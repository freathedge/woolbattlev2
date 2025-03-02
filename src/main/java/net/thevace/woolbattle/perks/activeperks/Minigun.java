package net.thevace.woolbattle.perks.activeperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.ActivePerk;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Minigun extends ActivePerk implements Listener {

    public Minigun(WoolBattlePlayer p) {
        super(20, 1, p, ChatColor.GOLD + "Minigun", Material.BOW, "Schießt eine große Menge an Pfeilen in die Richtung die du schaust");
    }

    @Override
    protected void applyEffect() {
        Vector direction = player.getPlayer().getLocation().getDirection().normalize();

        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                if (ticks >= 10) {
                    cancel();
                    return;
                }

                for (int i = 0; i < 5; i++) {
                    if (player.getWool() <= 0) {
                        cancel();
                        return;
                    }

                    player.removeWool(1);

                    Arrow arrow = player.getPlayer().getWorld().spawnArrow(player.getPlayer().getEyeLocation(), direction, 2.0f, 10.0f);
                    arrow.setShooter(player.getPlayer());
                    player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_ARROW_SHOOT, 1.0f, 1.0f);

                    NamespacedKey key = new NamespacedKey("woolbattle", "perktype");
                    arrow.getPersistentDataContainer().set(key, PersistentDataType.STRING, "minigunarrow");
                }

                ticks++;
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("WoolBattle"), 0L, 1L);
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
        if(event.getEntity() instanceof Arrow arrow) {
            NamespacedKey key = new NamespacedKey("woolbattle", "perktype");
            String perkType = arrow.getPersistentDataContainer().get(key, PersistentDataType.STRING);
            if ("minigunarrow".equals(perkType)) {
                arrow.remove();
            }

        }


    }
}
