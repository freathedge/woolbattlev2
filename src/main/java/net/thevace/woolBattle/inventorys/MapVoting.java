package net.thevace.woolBattle.inventorys;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

public class MapVoting extends View {

    @Override
    public void onInit(ViewConfigBuilder config) {
        config.title(ChatColor.GOLD + "Maps");
        config.size(3);
        config.cancelOnClick();
    }

}
