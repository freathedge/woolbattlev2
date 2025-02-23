package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.GameManager;
import net.thevace.woolbattle.PerkListenerManager;
import net.thevace.woolbattle.WoolBattleGame;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

public class Pfeilregen extends PassivePerk implements Listener {
    public Pfeilregen(WoolBattlePlayer p) {
        super(5, p, ChatColor.GOLD + "Pfeilregen", Material.ARROW, "Schieße alle 10 Pfeile 5 Pfeile auf einmal");
    }

    @Override
    public void applyEffect() {
    }

    @EventHandler
    public void onBowShoot(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            Player shooter = (Player) event.getEntity();
            Projectile projectile = (Projectile) event.getProjectile();

            if(shooter.equals(player.getPlayer())) {
                if (projectile instanceof Arrow) {
                    player.setArrowsShot(player.getArrowsShot() + 1);
                    if(player.getArrowsShot() >= 10) {
                        for (int i = 0; i < 4; i++) {
                            Arrow newArrow = shooter.launchProjectile(Arrow.class);
                            Vector direction = projectile.getVelocity().clone();
                            double spread = 0.3;
                            direction.setX(direction.getX() + (Math.random() - 0.5) * spread);
                            direction.setY(direction.getY() + (Math.random() - 0.5) * spread);
                            direction.setZ(direction.getZ() + (Math.random() - 0.5) * spread);

                            newArrow.setVelocity(direction);
                            newArrow.setShooter(shooter);
                            newArrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                        }
                        player.setArrowsShot(0);
                    }
                }
            }

        }
    }


}
