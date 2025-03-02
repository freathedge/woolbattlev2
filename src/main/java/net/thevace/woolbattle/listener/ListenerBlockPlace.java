package net.thevace.woolbattle.listener;

import net.thevace.woolbattle.WoolBattleGame;
import net.thevace.woolbattle.WoolBattleGameManager;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.WoolBattlePlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class ListenerBlockPlace implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player p = event.getPlayer();
        if(!WoolBattlePlayerManager.isRegistered(p)) return;
        WoolBattlePlayer player = WoolBattlePlayerManager.getWoolBattlePlayer(p);
        WoolBattleGame game = WoolBattleGameManager.getPlayerGame(player);
        if(game == null) return;

        if(!WoolBattlePlayerManager.isRegistered(p) || !game.isPlayerInGame(p)) return;


        if(event.getBlock().getType() == Material.RED_WOOL || event.getBlock().getType() == Material.BLUE_WOOL) {
            game.handleWoolPlace(player, event.getBlock());
        }
    }
}
