package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolBattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;


public class Blink extends ActivePerk implements Listener {


    public Blink(WoolBattlePlayer p) {
        super(15, 30, p, ChatColor.GOLD + "Blink", Material.ENDER_EYE, "Teleportiert dich 15 Blöcke in die Richtung die du schaust");
        if(p != null) {
            Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("WoolBattle"));
        }
    }


    @Override
    protected void applyEffect() {
        Location currentLocation = player.getPlayer().getLocation();
        Vector direction = currentLocation.getDirection().normalize().multiply(15);
        Location newLocation = currentLocation.add(direction);

        player.getPlayer().teleport(newLocation);
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
