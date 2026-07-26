package com.epicbowgun;

import org.bukkit.plugin.java.JavaPlugin;

public class EpicBowGun extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("EpicBowGun Plugin Enabled!");
        
        // Register command executor
        getCommand("machinegun").setExecutor(new MachineGunCommand(this));
        
        // Register event listeners
        getServer().getPluginManager().registerEvents(new BowShootListener(this), this);
        
        // Load configuration
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("EpicBowGun Plugin Disabled!");
    }
}