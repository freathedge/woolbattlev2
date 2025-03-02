package net.thevace.woolbattle.listener;

import net.thevace.woolbattle.WoolBattlePlayerManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ListenerItemDrop implements Listener {

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if(event.getItemDrop().getItemStack().getType() == Material.RED_WOOL || event.getItemDrop().getItemStack().getType() == Material.BLUE_WOOL) {
            WoolBattlePlayerManager.getWoolBattlePlayer(event.getPlayer()).removeWool(event.getItemDrop().getItemStack().getAmount(), false);
            return;
        }
        event.setCancelled(true);
    }
}
