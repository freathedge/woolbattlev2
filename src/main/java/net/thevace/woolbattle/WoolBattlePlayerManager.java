package net.thevace.woolbattle;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WoolBattlePlayerManager {
    private static final Map<UUID, WoolBattlePlayer> playerMap = new HashMap<>();

    public void registerPlayer(Player player) {
        playerMap.put(player.getUniqueId(), new WoolBattlePlayer(player));
    }

    public static WoolBattlePlayer getWoolBattlePlayer(Player player) {
        return playerMap.get(player.getUniqueId());
    }

    public static void removePlayer(Player player) {
        playerMap.remove(player.getUniqueId());

        player.getInventory().clear();
        player.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }


    public static boolean isRegistered(Player player) {
        return playerMap.containsKey(player.getUniqueId());
    }

    public static void listAllPlayers() {
        for (UUID uuid : playerMap.keySet()) {
            WoolBattlePlayer player = playerMap.get(uuid);
            System.out.println(player.getPlayer().getUniqueId());
        }
    }
}
