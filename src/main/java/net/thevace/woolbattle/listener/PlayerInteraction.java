package net.thevace.woolbattle.listener;

import me.devnatan.inventoryframework.ViewFrame;
import net.thevace.woolbattle.*;
import net.thevace.woolbattle.inventorys.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteraction implements Listener {
    ViewFrame viewFrame;
    QueueManager queueManager;


    public PlayerInteraction(ViewFrame viewFrame, QueueManager queueManager) {
        this.viewFrame = viewFrame;
        this.queueManager = queueManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().hasItemMeta()){

            Action action = event.getAction();
            Player player = event.getPlayer();

            if (event.getItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Team select")) {
                viewFrame.open(TeamSelect.class, player);
                event.setCancelled(true);
            } else if(event.getItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "Leave queue")) {
                queueManager.removeFromQueue(WoolBattlePlayerManager.getWoolBattlePlayer(player));
                event.setCancelled(true);
            } else if(event.getItem().getItemMeta().getDisplayName().equals(ChatColor.MAGIC + "Start game")) {
                queueManager.getQueue(WoolBattlePlayerManager.getWoolBattlePlayer(player)).startGame();
                event.setCancelled(true);
            } else if(event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Perk selector")) {
                viewFrame.open(PerkSelector.class, player);
                event.setCancelled(true);
            } else if(event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Voting")) {
                viewFrame.open(Voting.class, player);
                event.setCancelled(true);
            }



        }



    }

}
