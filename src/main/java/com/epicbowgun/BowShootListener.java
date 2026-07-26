package com.epicbowgun;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class BowShootListener implements Listener {
    private final EpicBowGun plugin;
    private static final int FIRE_RATE = 2; // Ticks between shots
    private static final int ARROW_COUNT = 5; // Arrows per burst

    public BowShootListener(EpicBowGun plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof Arrow)) return;
        
        Arrow arrow = (Arrow) event.getEntity();
        if (!(arrow.getShooter() instanceof Player)) return;
        
        Player player = (Player) arrow.getShooter();
        String playerUUID = player.getUniqueId().toString();
        
        // Check if player is in machine gun mode
        if (!MachineGunManager.isPlayerInMachineGunMode(playerUUID)) return;
        
        // Cancel the original arrow
        event.setCancelled(true);
        
        // Fire multiple arrows in rapid succession
        fireArrowBurst(player, arrow);
    }
    
    private void fireArrowBurst(Player player, Arrow originalArrow) {
        Vector direction = player.getLocation().getDirection();
        
        for (int i = 0; i < ARROW_COUNT; i++) {
            new BukkitRunnable() {
                int arrowIndex = i;
                
                @Override
                public void run() {
                    // Create and shoot arrow
                    Arrow newArrow = player.launchProjectile(Arrow.class);
                    
                    // Add slight spread for more realistic machine gun effect
                    Vector spreadDirection = direction.clone();
                    double spread = 0.05 * arrowIndex;
                    spreadDirection.add(new Vector(
                        (Math.random() - 0.5) * spread,
                        (Math.random() - 0.5) * spread,
                        (Math.random() - 0.5) * spread
                    ));
                    spreadDirection.normalize();
                    
                    newArrow.setVelocity(spreadDirection.multiply(3.0));
                    newArrow.setDamage(originalArrow.getDamage());
                }
            }.runTaskLater(plugin, FIRE_RATE * arrowIndex);
        }
        
        // Sound effect
        player.playSound(player.getLocation(), "entity.arrow.shoot", 1.0f, 1.5f);
    }
}