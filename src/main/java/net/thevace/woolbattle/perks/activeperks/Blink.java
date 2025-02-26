package net.thevace.woolbattle.perks.activeperks;

import net.thevace.woolbattle.GameManager;
import net.thevace.woolbattle.PerkListenerManager;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.WoolBattlePlayerManager;
import net.thevace.woolbattle.perks.ActivePerk;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;


public class Blink extends ActivePerk implements Listener {


    public Blink(WoolBattlePlayer p) {
        super(15, 30, p, ChatColor.GOLD + "Blink", Material.ENDER_EYE, "Teleportiert dich 15 Blöcke in die Richtung die du schaust");
    }


    @Override
    protected void applyEffect() {
        Location currentLocation = player.getPlayer().getLocation();
        Vector direction = currentLocation.getDirection().normalize().multiply(15);
        Location newLocation = currentLocation.add(direction);

        while (newLocation.getBlock().getType() != Material.AIR || newLocation.clone().add(0, 1, 0).getBlock().getType() != Material.AIR || newLocation.clone().add(0, 2, 0).getBlock().getType() != Material.AIR || newLocation.clone().add(0, 3, 0).getBlock().getType() != Material.AIR) {
            newLocation.add(0, 1, 0);
        }

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
