package net.thevace.woolbattle.perks.activeperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.ActivePerk;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Uhr extends ActivePerk implements Listener {
    public Uhr(WoolBattlePlayer p) {
        super(20, 30, p, ChatColor.GOLD + "Uhr", Material.CLOCK, "Teleportiere dich zu dem letzten Block auf dem du gestanden bist");
        if(p != null) {
            Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("WoolBattle"));
        }
    }

    @Override
    protected void applyEffect() {

        Location lastBlockLocation = player.getLastBlockLocation();

        if (lastBlockLocation != null) {

            float pitch = player.getPlayer().getLocation().getPitch();
            float yaw = player.getPlayer().getLocation().getYaw();
            Location teleport = lastBlockLocation.add(0.5, 1, 0.5);
            teleport.setYaw(yaw);
            teleport.setPitch(pitch);



            player.getPlayer().teleport(teleport);
            player.getPlayer().setVelocity(new Vector(0, 0, 0));
            player.getPlayer().setFallDistance(0);
        }
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
