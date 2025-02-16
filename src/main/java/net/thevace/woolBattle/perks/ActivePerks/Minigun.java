package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Minigun extends ActivePerk {

    public Minigun(WoolbattlePlayer p) {
        super(20, 1, p, ChatColor.GOLD + "Minigun", Material.BOW, "Schießt eine große Menge an Pfeilen in die Richtung die du schaust");
    }

    @Override
    protected void applyEffect() {
        Vector direction = player.getPlayer().getLocation().getDirection().normalize();

        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                if (ticks >= 20) {
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
                }

                ticks++;
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("WoolBattle"), 0L, 1L);
    }
}
