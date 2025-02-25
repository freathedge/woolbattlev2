package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.GameManager;
import net.thevace.woolbattle.PerkListenerManager;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class FastArrow extends PassivePerk implements Listener {

    public FastArrow(WoolBattlePlayer p) {
        super(3, p, ChatColor.GOLD + "Fast-Arrow", Material.ARROW, "Geschossene Pfeile fliegen um 20% schneller");
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
                    Arrow arrow = (Arrow) projectile;
                    arrow.setVelocity(arrow.getVelocity().multiply(1.2));
                }
            }

        }
    }
}
