package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.GameManager;
import net.thevace.woolbattle.PerkListenerManager;
import net.thevace.woolbattle.WoolBattleGame;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

public class TNTPfeil extends PassivePerk implements Listener {
    public TNTPfeil(WoolBattlePlayer p) {
        super(4, p, ChatColor.GOLD + "TNT-Pfeil", Material.TNT, "Schießt alle 10 Pfeile einen explosiven Pfeil welcher allen Spielern in einem Radius von 2 Blöcken Rückstoß gibt");
    }

    @EventHandler
    public void onBowShoot(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player p) {
            Projectile projectile = (Projectile) event.getProjectile();

            if(!p.equals(player.getPlayer())) return;
            if (projectile instanceof Arrow) {
                player.setArrowsShot(player.getArrowsShot() + 1);
            }

        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow arrow) {

            if (event.getHitBlock() != null) {
                Player p = (Player) arrow.getShooter();
                Block targetBlock = event.getHitBlock();

                if(!p.equals(player.getPlayer())) return;

                if(player.getArrowsShot() >= 10 && hasEnoughMoney()) {
                    Location location = targetBlock.getLocation();
                    TNTPrimed tnt = (TNTPrimed) location.getWorld().spawnEntity(location.add(0.5, 1, 0.5), EntityType.TNT);
                    tnt.setFuseTicks(10);

                    player.removeWool(preis);
                    player.setArrowsShot(0);
                }
            }
        }
    }

    @EventHandler
    public void onTNTExplosion(EntityExplodeEvent event) {
        if (event.getEntity() instanceof TNTPrimed tnt) {
            event.setCancelled(true);

            Location tntLocation = tnt.getLocation();

            double radius = 2.0;

            for (Entity entity : tntLocation.getWorld().getNearbyEntities(tntLocation, radius, radius, radius)) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    Vector knockback = player.getLocation().toVector().subtract(tntLocation.toVector()).normalize();
                    knockback.multiply(4);
                    knockback.setY(0.5);
                    player.setVelocity(knockback);
                }
            }

            for (Player player : tnt.getWorld().getPlayers()) {
                player.spawnParticle(Particle.EXPLOSION_EMITTER, tntLocation, 1);
                player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);
            }
        }
    }
}
