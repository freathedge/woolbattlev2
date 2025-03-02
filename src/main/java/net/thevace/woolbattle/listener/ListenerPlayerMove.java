package net.thevace.woolbattle.listener;

import net.thevace.woolbattle.WoolBattleGame;
import net.thevace.woolbattle.WoolBattleGameManager;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.WoolBattlePlayerManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class ListenerPlayerMove implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        if(!WoolBattlePlayerManager.isRegistered(p)) return;
        WoolBattlePlayer player = WoolBattlePlayerManager.getWoolBattlePlayer(p);
        WoolBattleGame game = WoolBattleGameManager.getPlayerGame(player);
        if(game == null) return;

        if(!WoolBattlePlayerManager.isRegistered(p) || !game.isPlayerInGame(p)) return;

        Block block = p.getLocation().getBlock().getRelative(BlockFace.DOWN);

        if(block.getType() != Material.AIR) {
            player.setLastBlockLocation(block.getLocation());
        }

        if (event.getTo().getY() < 0) {
            game.handlePlayerDeath(player);
        }

        player.setInDoubleJump(false);
        if(player.getWool() < 5 ) {
            p.setAllowFlight(false);
            return;
        }

        if(((Entity) p).isOnGround()) {
            p.setAllowFlight(true);
        }

    }
}
