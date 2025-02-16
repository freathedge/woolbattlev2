package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.GameManager;
import net.thevace.woolBattle.WoolBattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Pod extends ActivePerk {


    public Pod(WoolBattlePlayer player) {
        super(15, 10, player, ChatColor.GOLD + "Pod", Material.FLOWER_POT, "Erschaffe ein kleines Pod um dich herum");
    }

    @Override
    public void applyEffect() {
        Player p = player.getPlayer();
        Location loc = p.getLocation().getBlock().getLocation();
        Location playerloc = p.getLocation();
        Material material = player.getWoolMaterial();




        int[][] positions = {
                {0, -1, 0}, //Block unter dem Spieler
                {1, 0, 0}, //Block rechts vom Spieler
                {-1, 0, 0}, //Block links vom Spieler
                {0, 0, 1}, //Block vor dem Spieler
                {0, 0, -1}, //BLock hinter dem Spieler
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
        p.setFallDistance(0);


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
                Location location = loc.clone().add(x, y, z);

                if (location.getBlock().getType() == Material.AIR) {
                    location.getBlock().setType(material);
                    GameManager.getPlayerGame(player).addToPlayerBlocks(location);
                    p.playSound(p.getLocation(), Sound.ENTITY_SNOWBALL_THROW, 1.0f, 1.0f);
                }

                index++;
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("WoolBattle"), 0L, 2L);


        p.setVelocity(new Vector(0, 0, 0));
    }
}
