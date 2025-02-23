package net.thevace.woolbattle;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PerkListenerManager {
    private static Plugin plugin = null;
    public static final HashMap<WoolBattleGame, List<Listener>> gameListeners = new HashMap<>();

    public PerkListenerManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public static void registerPerkListener(WoolBattleGame game, Listener listener) {
        gameListeners.computeIfAbsent(game, k -> new ArrayList<>()).add(listener);
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }

    public static void unregisterListeners(WoolBattleGame game) {
        List<Listener> listeners = gameListeners.get(game);
        if (listeners != null) {
            for (Listener listener : listeners) {
                HandlerList.unregisterAll(listener);
            }
            gameListeners.remove(game);
        }
    }
}
