package net.thevace.woolBattle.perks;

import net.thevace.woolBattle.WoolbattlePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ActivePerk {
    int Cooldown;
    int Preis;
    WoolbattlePlayer player;

    String itemName;
    Material material;


    public ActivePerk(int cooldown, int preis, WoolbattlePlayer p, String itemName, Material material) {
        Cooldown = cooldown;
        Preis = preis;
        this.player  = p;
        this.itemName = itemName;
        this.material = material;
    }

    public void addItem() {
        Player p = player.getPlayer();

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(itemName);

        item.setItemMeta(meta);

        p.getInventory().addItem(item);
    }

}
