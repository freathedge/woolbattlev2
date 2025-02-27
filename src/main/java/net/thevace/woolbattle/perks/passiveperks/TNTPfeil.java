package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.GameManager;
import net.thevace.woolbattle.PerkListenerManager;
import net.thevace.woolbattle.WoolBattleGame;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class TNTPfeil extends PassivePerk implements Listener {
    public TNTPfeil(WoolBattlePlayer p) {
        super(4, p, ChatColor.GOLD + "TNT-Pfeil", Material.TNT, "Schießt alle 10 Pfeile einen explosiven Pfeil welcher allen Spielern in einem Radius von 2 Blöcken Rückstoß gibt");
    }

    @Override
    public void applyEffect() {
    }

    @EventHandler
    public void onBowShoot(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player p) {
            Projectile projectile = (Projectile) event.getProjectile();

            if(p.equals(player.getPlayer())) {
                if (projectile instanceof Arrow arrow) {
                    player.setArrowsShot(player.getArrowsShot() + 1);

                    if(player.getArrowsShot() >= 2) {
                        TNTPrimed tnt = (TNTPrimed) p.getWorld().spawnEntity(arrow.getLocation(), EntityType.TNT);
                        arrow.addPassenger(tnt);
                    }
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

                if (event.getHitEntity() instanceof Player) {
                    assert game != null;
                    if (!game.handlePlayerHit(p, target)) {
                        if(player.getArrowsShot() >= 10) {

                        }

                    }
                }
                if (event.getHitBlock() != null) {
                    if(player.getArrowsShot() >= 10) {

                    }
                }
            }
        }
    }
}
