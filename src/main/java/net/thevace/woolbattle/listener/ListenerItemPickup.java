package net.thevace.woolbattle.listener;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.WoolBattlePlayerManager;

public class ListenerItemPickup implements Listener {

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!WoolBattlePlayerManager.isRegistered(player)) return;

        WoolBattlePlayer woolBattlePlayer = WoolBattlePlayerManager.getWoolBattlePlayer(player);
        int maxWool = woolBattlePlayer.getMaxWool();
        int currentWool = woolBattlePlayer.getWool();
        int availableSpace = maxWool - currentWool;

        if (availableSpace <= 0) {
            event.setCancelled(true);
            return;
        }

        ItemStack item = event.getItem().getItemStack();
        if (item.getType() == Material.RED_WOOL || item.getType() == Material.BLUE_WOOL) {
            int pickupAmount = item.getAmount();

            if (pickupAmount > availableSpace) {
                item.setAmount(pickupAmount - availableSpace);
                woolBattlePlayer.addWool(availableSpace, false);
            } else {
                woolBattlePlayer.addWool(pickupAmount, false);
            }

        }
    }
}
