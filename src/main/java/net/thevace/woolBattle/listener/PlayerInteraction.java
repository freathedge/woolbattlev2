package net.thevace.woolBattle.listener;

import me.devnatan.inventoryframework.ViewFrame;
import net.thevace.woolBattle.inventorys.TeamSelect;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteraction implements Listener {
    ViewFrame viewFrame;

    public PlayerInteraction(ViewFrame viewFrame) {
        this.viewFrame = viewFrame;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().hasItemMeta()){

            Action action = event.getAction();
            Player player = event.getPlayer();

            if (event.getItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Team select")) {
                viewFrame.open(TeamSelect.class, player);
                event.setCancelled(true);
            }
        }

    }
}
