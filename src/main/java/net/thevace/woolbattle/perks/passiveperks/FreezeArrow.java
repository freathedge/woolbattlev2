package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.*;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class FreezeArrow extends PassivePerk implements Listener {

    public FreezeArrow(WoolBattlePlayer p) {
        super(4, p, ChatColor.GOLD + "Freeze-Arrow", Material.BLUE_ICE, "Schießt alle 10 Pfeile einen Eis Pfeil, welcher den Spieler temporär einfrieren lässt");

    }

    @Override
    public void applyEffect() {
        if(player != null) {
            PerkListenerManager.registerPerkListener(GameManager.getPlayerGame(player), this);
        }
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
                            player.removeWool(preis);
                            player.setArrowsShot(0);

//                            target.sendMessage("turned on freeze");
//                            target.setWalkSpeed(0);
//                            target.registerAttribute(Attribute.JUMP_STRENGTH);
//                            Objects.requireNonNull(target.getAttribute(Attribute.JUMP_STRENGTH)).setBaseValue(0);
//                            target.setAllowFlight(false);
//                            Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("WoolBattle"), () -> {
//                                target.sendMessage("turned off freeze");
//                                target.setWalkSpeed(1);
//                                Objects.requireNonNull(target.getAttribute(Attribute.JUMP_STRENGTH)).setBaseValue(Objects.requireNonNull(target.getAttribute(Attribute.JUMP_STRENGTH)).getDefaultValue());
//                                target.setAllowFlight(true);
//                            }, 100L);
                        }

                    }
                }
            }
        }
    }
}
