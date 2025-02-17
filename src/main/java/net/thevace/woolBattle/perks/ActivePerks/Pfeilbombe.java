package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolBattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import net.thevace.woolBattle.perks.Perk;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Pfeilbombe extends ActivePerk implements Listener {

    public Pfeilbombe(WoolBattlePlayer p) {
        super(15, 10, p, ChatColor.GOLD + "Pfeilbombe", Material.FIREWORK_STAR, "Schießt Pfeile in jede Richtung und kann Feind als auch Freund treffen");
        if(p != null) {
            Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("WoolBattle"));
        }
    }

    @Override
    protected void applyEffect() {
        Snowball snowball = player.getPlayer().launchProjectile(Snowball.class);
        Vector direction = player.getPlayer().getLocation().getDirection().multiply(1.5);
        snowball.setVelocity(direction);
        player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_SNOWBALL_THROW, 1.0f, 1.0f);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().hasItemMeta() && event.getPlayer().equals(player.getPlayer())) {
            if (event.getItem().getItemMeta().getDisplayName().equals(itemName)) {
                activate();
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball) {

            if (event.getHitBlock() != null) {
                Block targetBlock = event.getHitBlock();
                Location location = targetBlock.getLocation().clone().add(0, 2, 0);

                for (int i = 0; i < 100; i++) {
                    double phi = Math.random() * Math.PI;
                    double theta = Math.random() * 2 * Math.PI;

                    double xDirection = Math.sin(phi) * Math.cos(theta);
                    double yDirection = Math.cos(phi);
                    double zDirection = Math.sin(phi) * Math.sin(theta);

                    Arrow arrow = targetBlock.getWorld().spawn(location, Arrow.class);
                    arrow.setShooter(player.getPlayer());
                    Vector direction = new Vector(xDirection, yDirection, zDirection).normalize().multiply(1);
                    arrow.setVelocity(direction);
                }
            }
        }
    }

}
