package net.thevace.woolbattle.perks;

import net.thevace.woolbattle.WoolBattlePlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public abstract class PassivePerk extends Perk {

    public PassivePerk(int cooldown, int preis, WoolBattlePlayer p, String itemName, Material material, String description) {
        super(cooldown, preis, p, itemName, material, description);
    }

    public PassivePerk(int preis, WoolBattlePlayer p, String itemName, Material material, String description) {
        super(0, preis, p, itemName, material, description);
    }

    public PassivePerk(WoolBattlePlayer p, String itemName, Material material, String description) {
        super(0, 0, p, itemName, material, description);
    }

    public PassivePerk(int cooldown, int preis, WoolBattlePlayer p, String itemName, ItemStack item, String description) {
        super(cooldown, preis, p, itemName, item, description);
    }

    public PassivePerk(int preis, WoolBattlePlayer p, String itemName, ItemStack item, String description) {
        super(0, preis, p, itemName, item, description);
    }

    public PassivePerk(WoolBattlePlayer p, String itemName, ItemStack item, String description) {
        super(0, 0, p, itemName, item, description);
    }

}
