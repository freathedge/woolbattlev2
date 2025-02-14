package net.thevace.woolBattle.perks;

import net.thevace.woolBattle.WoolbattlePlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Perk {
    protected final int preis;
    protected final long cooldown;
    protected final WoolbattlePlayer player;

    String itemName;
    Material material;


    ItemStack item;

    public Perk(long cooldown, int preis, WoolbattlePlayer player, String itemName, Material material) {
        this.material = material;
        this.itemName = itemName;
        this.player = player;
        this.cooldown = cooldown;
        this.preis = preis;

        item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(itemName);
        item.setItemMeta(meta);
    }
}
