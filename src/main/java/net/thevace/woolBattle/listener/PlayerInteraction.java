package net.thevace.woolBattle.listener;

import me.devnatan.inventoryframework.ViewFrame;
import net.thevace.woolBattle.*;
import net.thevace.woolBattle.inventorys.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerInteraction implements Listener {
    ViewFrame viewFrame;
    WoolBattlePlayerManager playerManager;
    QueueManager queueManager;


    public PlayerInteraction(ViewFrame viewFrame, WoolBattlePlayerManager playerManager, QueueManager queueManager) {
        this.viewFrame = viewFrame;
        this.playerManager = playerManager;
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
                queueManager.removeFromQueue(playerManager.getWoolBattlePlayer(player));
                event.setCancelled(true);
            } else if(event.getItem().getItemMeta().getDisplayName().equals(ChatColor.MAGIC + "Start game")) {
                queueManager.getQueue(playerManager.getWoolBattlePlayer(player)).startGame();
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
