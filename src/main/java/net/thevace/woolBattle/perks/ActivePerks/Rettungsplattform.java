package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Rettungsplattform extends ActivePerk {

    public Rettungsplattform(WoolbattlePlayer p) {
        super(20, 32, p, ChatColor.GOLD + "Rettungsplattform", Material.BLAZE_ROD, "Erschafft eine kleine Kreisförmige Plattform unter dir");
    }

    @Override
    public void applyEffect() {
        Player p = player.getPlayer();
        Location loc = p.getLocation().getBlock().getLocation();
        Location playerloc = p.getLocation();
        Material material = player.getWoolMaterial();




        int[][] positions = {
                {0, -1, 0},  // Block unter dem Spieler

                {1, -1, 0},  // Rechts
                {2, -1, 0},  // Rechts
                {-1, -1, 0}, // Links
                {-2, -1, 0}, // Links

                {0, -1, 1},  // Vorne
                {0, -1, 2},  // Vorne
                {0, -1, -1}, // Hinten
                {0, -1, -2}, // Hinten

                {1, -1, 1},  // Ecke vorne rechts
                {1, -1, -1}, // Ecke hinten rechts
                {-1, -1, 1}, // Ecke vorne links
                {-1, -1, -1} // Ecke hinten links
        };


        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                if (ticks >= 20L) {
                    cancel();
                    return;
                }

                p.setVelocity(new Vector(0, 0, 0));
                ticks++;
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("WoolBattle"), 0L, 1L);


        p.teleport(new Location(p.getWorld(), loc.getX() + 0.5, loc.getY(), loc.getZ() + 0.5, playerloc.getYaw(), playerloc.getPitch()));

        new BukkitRunnable() {
            int index = 0;

            @Override
            public void run() {
                if (index >= positions.length) {
                    cancel();
                    return;
                }
                int x = positions[index][0];
                int y = positions[index][1];
                int z = positions[index][2];
                loc.clone().add(x, y, z).getBlock().setType(material);
                p.playSound(p.getLocation(), Sound.ENTITY_SNOWBALL_THROW, 1.0f, 1.0f);
                index++;
            }


        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("WoolBattle"), 0L, 1L);

        p.setVelocity(new Vector(0, 0, 0));
    }
}
