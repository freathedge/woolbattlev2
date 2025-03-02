package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class Harpune extends PassivePerk {

    public Harpune(WoolBattlePlayer p) {
        super(15, 4, p, ChatColor.GOLD + "Harpune", Material.SPECTRAL_ARROW, "Zieht den getroffenen Spieler zum Spieler, welcher geschossen hat");
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

                if(player.getArrowsShot() >= 4 && hasEnoughMoney()) {
                    Location location = targetBlock.getLocation();
                    TNTPrimed tnt = (TNTPrimed) location.getWorld().spawnEntity(location.add(0.5, 1, 0.5), EntityType.TNT);
                    tnt.setFuseTicks(10);

                    player.removeWool(preis);
                    player.setArrowsShot(0);
                }
            }
        }
    }
}
