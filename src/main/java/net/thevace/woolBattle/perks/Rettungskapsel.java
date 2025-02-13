package net.thevace.woolBattle.perks;

import net.thevace.woolBattle.WoolbattlePlayer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Rettungskapsel extends ActivePerk {


    public Rettungskapsel(WoolbattlePlayer player) {
        super(30, 16, player, ChatColor.GOLD + "Rettungskapsel", Material.WHITE_WOOL);
    }

    @Override
    public void applyEffect() {
        Player p = player.getPlayer();
        Location loc = p.getLocation().getBlock().getLocation();
        Location playerloc = p.getLocation();
        Material material = player.getWoolMaterial();




        int[][] positions = {
                {0, 2, 0}, // Block über dem Spieler
                {0, -1, 0}, // Block unter dem Spieler

                {1, 0, 0}, {1, 1, 0}, // Wand rechts (2 Blöcke hoch)
                {-1, 0, 0}, {-1, 1, 0}, // Wand links (2 Blöcke hoch)
                {0, 0, 1}, {0, 1, 1}, // Wand vorne (2 Blöcke hoch)
                {0, 0, -1}, {0, 1, -1} // Wand hinten (2 Blöcke hoch)
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


        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("WoolBattle"), 0L, 2L);

        p.setVelocity(new Vector(0, 0, 0));
    }
}
