package net.thevace.woolbattle.perks.activeperks;

import net.thevace.woolbattle.GameManager;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.ActivePerk;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Pod extends ActivePerk implements Listener {


    public Pod(WoolBattlePlayer p) {
        super(15, 10, p, ChatColor.GOLD + "Pod", Material.FLOWER_POT, "Erschaffe ein kleines Pod um dich herum");
        if(p != null) {
            Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("WoolBattle"));
        }
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

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().hasItemMeta() && event.getPlayer().equals(player.getPlayer())) {
            if (event.getItem().getItemMeta().getDisplayName().equals(itemName)) {
                activate();
                event.setCancelled(true);
            }
        }
    }
}
