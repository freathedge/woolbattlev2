package net.thevace.woolBattle;

import net.thevace.woolBattle.perks.ActivePerk;
import net.thevace.woolBattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WoolbattlePlayer {
    private Player player;
    private int Wool;

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
        return Wool;
    }

    public void setWool(int wool) {
        Wool = wool;
    }

    public void addWool(int wool, Material material) {
        Wool += wool;
        player.getInventory().addItem(new ItemStack(material));
    }

    public void removeWool(int wool) {
        Wool -= wool;
    }
}
