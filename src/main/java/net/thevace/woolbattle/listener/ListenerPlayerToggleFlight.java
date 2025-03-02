package net.thevace.woolbattle.listener;

import net.thevace.woolbattle.WoolBattleGame;
import net.thevace.woolbattle.WoolBattleGameManager;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.WoolBattlePlayerManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class ListenerPlayerToggleFlight implements Listener {

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Player p = event.getPlayer();
        if(!WoolBattlePlayerManager.isRegistered(p)) return;
        WoolBattlePlayer player = WoolBattlePlayerManager.getWoolBattlePlayer(p);
        WoolBattleGame game = WoolBattleGameManager.getPlayerGame(player);
        if(game == null) return;

        if (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR  || !game.isPlayerInGame(p)) {
            return;
        }

        if(player.getWool() < 5 ||!player.canDoubleJump()) {
            event.setCancelled(true);
            return;
        } else {
            player.removeWool(5);
        }


        if (!p.isFlying()) {
            event.setCancelled(true);
            p.setAllowFlight(false);

            Vector direction;

            if(player.getDoubleJumpHorizontalPower() != 0.0) {
                direction = p.getLocation().getDirection().normalize().multiply(player.getDoubleJumpHorizontalPower());
                direction.setY(player.getDoubleJumpVerticalPower());
            } else {
                direction = new Vector(0, player.getDoubleJumpVerticalPower(), 0);
            }

            p.setVelocity(direction);
            player.setInDoubleJump(true);
            game.startDJCooldown(player);
        }
    }
}
