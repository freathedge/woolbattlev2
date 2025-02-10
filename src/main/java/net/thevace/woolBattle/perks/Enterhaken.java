package net.thevace.woolBattle.perks;

import net.thevace.woolBattle.WoolbattlePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class Enterhaken extends ActivePerk {

    Block targetBlock;

    public Enterhaken(WoolbattlePlayer p) {
        super(12, 10, p, ChatColor.GOLD + "Enterhaken", Material.FISHING_ROD);
    }

    public void activate(PlayerFishEvent event) {
        Player player = event.getPlayer();
        if(event.getState().equals(PlayerFishEvent.State.REEL_IN)) {
            Location location = player.getLocation();
            Location hookLocation = event.getHook().getLocation();
            Location change = hookLocation.subtract(location);
            player.setVelocity(change.toVector().multiply(0.3));
        }

    }

    public void setTargetBlock(Block targetBlock) {
        this.targetBlock = targetBlock;
    }

}
