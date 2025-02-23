package net.thevace.woolbattle.perks.activeperks;

import net.thevace.woolbattle.GameManager;
import net.thevace.woolbattle.PerkListenerManager;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.ActivePerk;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Brueckenbauer extends ActivePerk implements Listener {

    public Brueckenbauer(WoolBattlePlayer p) {
        super(10, 2, p, ChatColor.GOLD + "Brückenbauer", Material.PISTON, "Baut eine flache Linie in die Richtung die du schaust");
    }


    @Override
    protected void applyEffect() {
        Location playerLoc = player.getPlayer().getLocation();
        Vector direction = playerLoc.getDirection().setY(0).normalize();
        Location startLoc = playerLoc.clone().add(0, -1, 0);

        if (player.getWool() < preis) {
            return;
        }

        for (int i = 0; i < 20; i++) {
            startLoc.add(direction);
            Block block = startLoc.getBlock();

            if (block.getType() != Material.AIR) {
                break;
            }

            block.setType(player.getWoolMaterial());
            GameManager.getPlayerGame(player).addToPlayerBlocks(block.getLocation());

            if(player.getWool() < preis) {
                break;
            }

            player.removeWool(preis);
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
