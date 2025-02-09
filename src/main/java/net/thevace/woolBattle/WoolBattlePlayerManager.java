package net.thevace.woolBattle;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WoolBattlePlayerManager {
    private static final Map<Player, WoolbattlePlayer> playerMap = new HashMap<>();

    public void registerPlayer(Player player) {
        playerMap.put(player, new WoolbattlePlayer(player));
    }

    public void removePlayer(Player player) {
        playerMap.remove(player);
    }

    public WoolbattlePlayer getWoolBattlePlayer(Player player) {
        return playerMap.get(player);
    }

    public boolean isRegistered(Player player) {
        return playerMap.containsKey(player);
    }
}
