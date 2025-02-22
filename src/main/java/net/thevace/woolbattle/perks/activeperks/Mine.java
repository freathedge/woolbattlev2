package net.thevace.woolbattle.perks.activeperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.WoolBattlePlayerManager;
import net.thevace.woolbattle.perks.ActivePerk;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Mine extends ActivePerk implements Listener {



    Location plateLocation;


    public Mine(WoolBattlePlayer p) {
        super(30, 16, p, ChatColor.GOLD + "Mine", Material.STONE_PRESSURE_PLATE, "Platziere eine Druckplatte die jedem Spieler in der Umgebung Rückstoß gibt");
        if(p != null) {
            Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("WoolBattle"));
        }
    }

    @Override
    protected void applyEffect() {
        player.getPlayer().sendMessage("Applied effect, placed pressure plate");
        plateLocation.getWorld().getBlockAt(plateLocation).setType(Material.STONE_PRESSURE_PLATE);
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.STONE_PRESSURE_PLATE) {

            WoolBattlePlayer woolbattlePlayer = WoolBattlePlayerManager.getWoolBattlePlayer(event.getPlayer());

            if(woolbattlePlayer.equals(player)) {
                plateLocation = event.getBlock().getLocation();
                event.setCancelled(true);
                activate();
            }
        }
    }

    @EventHandler
    public void onPlayerStepOnPressurePlate(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL) {
            Block block = event.getClickedBlock();

            if (block != null && block.getType() == Material.STONE_PRESSURE_PLATE) {

                for (Player player : plateLocation.getWorld().getPlayers()) {
                    Location playerLoc = player.getLocation();
                    double distance = playerLoc.distance(plateLocation);

                    if (distance <= 5) {
                        Vector knockback = playerLoc.toVector().subtract(plateLocation.toVector()).normalize();
                        knockback.multiply(1.5);
                        knockback.setY(0.5);
                        player.setVelocity(knockback);
                        player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);
                    }
                }

                for (Player player : plateLocation.getWorld().getPlayers()) {
                    player.spawnParticle(Particle.EXPLOSION_EMITTER, plateLocation, 1);
                    player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);
                }

                block.setType(Material.AIR);
            }
        }
    }

}
