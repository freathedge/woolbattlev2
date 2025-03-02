package net.thevace.woolbattle.listener;

import net.thevace.woolbattle.WoolBattleGame;
import net.thevace.woolbattle.WoolBattleGameManager;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.WoolBattlePlayerManager;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ListenerEntityDamageByEntity implements Listener {

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof EnderPearl) {
            event.setDamage(0);
            event.setCancelled(true);
            return;
        }

        if (!(event.getEntity() instanceof Player target)) return;
        if (!WoolBattlePlayerManager.isRegistered(target)) return;

        WoolBattlePlayer targetPlayer = WoolBattlePlayerManager.getWoolBattlePlayer(target);
        WoolBattleGame game = WoolBattleGameManager.getPlayerGame(targetPlayer);
        if (game == null || !game.isPlayerInGame(target)) return;

        Player damager = getDamager(event);
        if (damager == null || damager.equals(target)) return;

        if (targetPlayer.isProtected()) {
            event.setCancelled(true);
            return;
        }

        targetPlayer.setLastHitter(damager);

        if (game.checkPlayerHit(damager, target)) {
            event.setCancelled(true);
            return;
        }

        targetPlayer.setLastHit(Timestamp.valueOf(LocalDateTime.now()));
        event.setDamage(0.000001);
    }

    private Player getDamager(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            return player;
        } else if (event.getDamager() instanceof Projectile projectile && projectile.getShooter() instanceof Player player) {
            return player;
        }
        return null;
    }
}
