package net.thevace.woolBattle.listener;

import net.thevace.woolBattle.WoolBattleGame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class WoolBattleGameListener implements Listener {
    private WoolBattleGame game;
    private Plugin plugin;

    public WoolBattleGameListener(WoolBattleGame game, Plugin plugin) {
        this.game = game;
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (event.getTo().getY() < 0) {
            game.handlePlayerHeight(player);
        }
    }

    public void unregister() {
        PlayerMoveEvent.getHandlerList().unregister(this);
    }
}
