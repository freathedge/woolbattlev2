package net.thevace.woolBattle;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WoolBattlePlayerManager {
    private static final Map<UUID, WoolbattlePlayer> playerMap = new HashMap<>();

    public void registerPlayer(Player player) {
        playerMap.put(player.getUniqueId(), new WoolbattlePlayer(player));
    }

    public WoolbattlePlayer getWoolBattlePlayer(Player player) {
        return playerMap.get(player.getUniqueId());
    }

    public void removePlayer(Player player) {
        playerMap.remove(player.getUniqueId());
    }


    public boolean isRegistered(Player player) {
        return playerMap.containsKey(player);
    }

    public void listAllPlayers() {
        for (UUID uuid : playerMap.keySet()) {
            WoolbattlePlayer player = playerMap.get(uuid);
            System.out.println(player.getPlayer().getUniqueId());
        }
    }
}
