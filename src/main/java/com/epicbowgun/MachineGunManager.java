package com.epicbowgun;

import java.util.HashSet;
import java.util.Set;

public class MachineGunManager {
    private static final Set<String> activePlayers = new HashSet<>();
    
    public static void addPlayer(String playerUUID) {
        activePlayers.add(playerUUID);
    }
    
    public static void removePlayer(String playerUUID) {
        activePlayers.remove(playerUUID);
    }
    
    public static boolean isPlayerInMachineGunMode(String playerUUID) {
        return activePlayers.contains(playerUUID);
    }
    
    public static void clearAllPlayers() {
        activePlayers.clear();
    }
}