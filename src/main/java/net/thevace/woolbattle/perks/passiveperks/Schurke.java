package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.GameManager;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.WoolBattlePlayerManager;
import net.thevace.woolbattle.perks.PassivePerk;
import net.thevace.woolbattle.perks.Perk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

public class Schurke extends PassivePerk implements Listener {

    Random random = new Random();

    public Schurke(WoolBattlePlayer p) {
        super(p, ChatColor.GOLD + "Schurke", Material.GOLD_INGOT, "Beim Treffen eines Gegners gibt es eine fünf Prozentige Chance, zwischen 4 und 16 Wolle aus dem Inventar des Gegners zu klauen");
    }

    @Override
    public void applyEffect() {

    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        Player damager = null;
        Player target = null;

        if (event.getDamager() instanceof Player) {
            damager = (Player) event.getDamager();
        }

        if (event.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) event.getDamager();
            if (projectile.getShooter() instanceof Player) {
                damager = (Player) projectile.getShooter();
            }
        }

        if (event.getEntity() instanceof Player) {
            target = (Player) event.getEntity();
        }

        if (damager != null && target != null) {
            if(GameManager.getPlayerGame(player).handlePlayerHit(damager, target)) {
                event.setCancelled(true);
            } else {
                if (random.nextInt(100) < 5) {
                    int woolAmount = 4 + random.nextInt(13);
                    WoolBattlePlayer targetPlayer = WoolBattlePlayerManager.getWoolBattlePlayer(target);
                    if(targetPlayer.getWool() > 0) {
                        targetPlayer.removeWool(woolAmount);
                        player.addWool(woolAmount);
                    }
                }
            }
        }
    }
}
