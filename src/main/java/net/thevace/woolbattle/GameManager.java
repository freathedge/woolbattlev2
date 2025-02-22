package net.thevace.woolbattle;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class GameManager {
    private static final Set<WoolBattleGame> activeGames = new HashSet<>();

    public static void addGame(WoolBattleGame game) {
        activeGames.add(game);
    }

    public static void removeGame(WoolBattleGame game) {
        activeGames.remove(game);
    }

    public static Set<WoolBattleGame> getActiveGames() {
        return activeGames;
    }

    public static WoolBattleGame getPlayerGame(WoolBattlePlayer player) {
        for (WoolBattleGame game : activeGames) {
            if (game.isPlayerInGame(player)) {
                return game;
            }
        }
        return null;
    }

    public static WoolBattleGame getPlayerGame(Player player) {
        for (WoolBattleGame game : activeGames) {
            if (game.isPlayerInGame(player)) {
                return game;
            }
        }
        return null;
    }

}
