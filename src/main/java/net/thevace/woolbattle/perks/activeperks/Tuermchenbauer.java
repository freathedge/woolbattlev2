package net.thevace.woolbattle.perks.activeperks;

import net.thevace.woolbattle.GameManager;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.ActivePerk;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Tuermchenbauer extends ActivePerk implements Listener {

    public Tuermchenbauer(WoolBattlePlayer p) {
        super(15, 20, p, ChatColor.GOLD + "Türmchenbauer", Material.LADDER, "Erschafft einen Vertikalen Turm unter dir");
        if(p != null) {
            Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("WoolBattle"));
        }
    }

    @Override
    protected void applyEffect() {
        Location location = player.getPlayer().getLocation().clone().add(0, -1, 0);

        while (location.getY() > 0) {
            Block block = location.getBlock();

            if (!block.getType().isAir()) {
                location.add(0, -1, 0);
                continue;
            }

            block.setType(player.getWoolMaterial());
            GameManager.getPlayerGame(player).addToPlayerBlocks(block.getLocation());

            location.add(0, -1, 0);
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
