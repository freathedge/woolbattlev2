package net.thevace.woolBattle.perks;

import net.thevace.woolBattle.WoolbattlePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class Enterhaken extends ActivePerk {
    WoolbattlePlayer player;

    Block targetBlock;

    public Enterhaken(WoolbattlePlayer p) {
        super(12, 10, p, ChatColor.GOLD + "Enterhaken", Material.FISHING_ROD);
        this.player = p;
    }

    public void activate() {
        Vector direction = targetBlock.getLocation().toVector().subtract(player.getPlayer().getLocation().toVector()).normalize();
        player.getPlayer().setVelocity(direction.multiply(0.5));

    }

    public void setTargetBlock(Block targetBlock) {
        this.targetBlock = targetBlock;
    }

}
