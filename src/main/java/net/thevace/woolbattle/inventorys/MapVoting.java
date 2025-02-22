package net.thevace.woolbattle.inventorys;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import org.bukkit.ChatColor;

public class MapVoting extends View {

    @Override
    public void onInit(ViewConfigBuilder config) {
        config.title(ChatColor.GOLD + "Maps");
        config.size(3);
        config.cancelOnClick();
    }

}
