package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.*;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class RueckstossPfeil extends PassivePerk implements Listener {

    public RueckstossPfeil(WoolBattlePlayer p) {
        super(3, p, ChatColor.GOLD + "Rückstoß-Pfeil", Material.ARROW, "Alle 10 Pfeile wird ein Pfeil geschossen, welcher 15% mehr Rückstoß erteilt");
    }

    @EventHandler
    public void onBowShoot(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            Player shooter = (Player) event.getEntity();
            Projectile projectile = (Projectile) event.getProjectile();

            if(shooter.equals(player.getPlayer())) {
                if (projectile instanceof Arrow) {
                    player.setArrowsShot(player.getArrowsShot() + 1);

                }
            }

        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow arrow) {
            if (arrow.getShooter() instanceof Player p) {
                Player target = (Player) event.getHitEntity();
                WoolBattleGame game = GameManager.getPlayerGame(player);

                if(!p.equals(player.getPlayer())) return;

                if (target != null) {
                    assert game != null;
                    if (!game.handlePlayerHit(p, target)) {
                        if(player.getArrowsShot() >= 10) {
                            arrow.setKnockbackStrength(3);
                            player.removeWool(preis);
                            player.setArrowsShot(0);
                        }
                    }
                }
            }
        }
    }
}
