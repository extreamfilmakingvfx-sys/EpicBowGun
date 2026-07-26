package com.epicbowgun;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MachineGunCommand implements CommandExecutor {
    private final EpicBowGun plugin;

    public MachineGunCommand(EpicBowGun plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }

        Player player = (Player) sender;
        String playerUUID = player.getUniqueId().toString();
        
        // Toggle machine gun mode
        if (MachineGunManager.isPlayerInMachineGunMode(playerUUID)) {
            MachineGunManager.removePlayer(playerUUID);
            player.sendMessage("§cMachine Gun Mode DISABLED");
        } else {
            MachineGunManager.addPlayer(playerUUID);
            player.sendMessage("§aMAchine Gun Mode ENABLED");
        }
        
        return true;
    }
}