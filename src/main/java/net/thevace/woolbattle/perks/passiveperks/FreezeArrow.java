package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.GameManager;
import net.thevace.woolbattle.WoolBattleGame;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.WoolBattlePlayerManager;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.persistence.PersistentDataType;

public class FreezeArrow extends PassivePerk implements Listener {

    public FreezeArrow(WoolBattlePlayer p) {
        super(4, p, ChatColor.GOLD + "FreezeArrow", Material.BLUE_ICE, "Schießt alle 10 Pfeile einen Eis Pfeil, welcher den Spieler temporär einfrieren lässt");

    }

    @Override
    protected void applyEffect() {
        if(player != null) {
            Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("WoolBattle"));
        }
    }

    @EventHandler
    public void onBowShoot(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            Player shooter = (Player) event.getEntity();
            Projectile projectile = (Projectile) event.getProjectile();

            if(shooter.getUniqueId().equals(player.getPlayer().getUniqueId())) {


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

                if(!p.getUniqueId().equals(player.getPlayer().getUniqueId())) return;

                if (target != null) {
                    assert game != null;
                    if (!game.handlePlayerHit(p, target)) {

                        WoolBattlePlayer wbpTarget = WoolBattlePlayerManager.getWoolBattlePlayer(target);
                        wbpTarget.setFreezed(true);

                        Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("WoolBattle"), () -> {
                            wbpTarget.setFreezed(false);
                        }, 60L);
                    }
                }
            }
        }
        }
}
