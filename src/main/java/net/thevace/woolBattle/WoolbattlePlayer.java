package net.thevace.woolBattle;

import net.thevace.woolBattle.perks.ActivePerk;
import net.thevace.woolBattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WoolbattlePlayer {
    private Player player;
    private int wool;
    private Material woolMaterial;

    ActivePerk activePerk1;
    ActivePerk activePerk2;
    PassivePerk passivePerk;

    public WoolbattlePlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getWool() {
        return wool;
    }

    public void setWool(int wool) {
        wool = wool;
    }

    public void addWool(int wool, Material material) {
        this.wool += wool;
        player.getInventory().addItem(new ItemStack(material));
    }

    public void removeWool(int wool) {
       this.wool -= wool;
    }

    public Material getWoolMaterial() {
        return woolMaterial;
    }

    public void setWoolMaterial(Material woolMaterial) {
        this.woolMaterial = woolMaterial;
    }
}
