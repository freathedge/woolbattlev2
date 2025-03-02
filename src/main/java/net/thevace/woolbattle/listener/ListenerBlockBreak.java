package net.thevace.woolbattle.listener;

import net.thevace.woolbattle.WoolBattleGame;
import net.thevace.woolbattle.WoolBattleGameManager;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.WoolBattlePlayerManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ListenerBlockBreak implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player p = event.getPlayer();
        if(!WoolBattlePlayerManager.isRegistered(p)) return;
        WoolBattlePlayer player = WoolBattlePlayerManager.getWoolBattlePlayer(p);
        WoolBattleGame game = WoolBattleGameManager.getPlayerGame(player);
        if(game == null) return;

        if(!WoolBattlePlayerManager.isRegistered(p) || !game.isPlayerInGame(p)) return;


        if (block.getType() == Material.RED_WOOL || block.getType() == Material.BLUE_WOOL || block.getType() == Material.GRAY_WOOL || block.getType() == Material.GREEN_WOOL) {
            game.handleWoolBreak(p, block);
            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1.0f, 1.0f);
        }

        event.setCancelled(true);
    }
}
