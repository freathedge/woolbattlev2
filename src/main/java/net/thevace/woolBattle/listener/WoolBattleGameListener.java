package net.thevace.woolBattle.listener;

import net.thevace.woolBattle.WoolBattleGame;
import net.thevace.woolBattle.WoolBattlePlayerManager;
import net.thevace.woolBattle.WoolbattlePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class WoolBattleGameListener implements Listener {
    private WoolBattleGame game;

    public WoolBattleGameListener(WoolBattleGame game) {
        this.game = game;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (event.getTo().getY() < 0) {
            game.handlePlayerHeight(player);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if(block.getType() == Material.RED_WOOL || block.getType() == Material.BLUE_WOOL) {
            Player player = event.getPlayer();
            game.handleWoolBreak(player);
        }
        event.setCancelled(true);
    }

    public void unregister() {
        PlayerMoveEvent.getHandlerList().unregister(this);
    }
}
