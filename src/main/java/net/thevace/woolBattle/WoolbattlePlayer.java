package net.thevace.woolBattle;

import net.thevace.woolBattle.perks.ActivePerk;
import net.thevace.woolBattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WoolbattlePlayer {
    private Player player;
    private int wool;
    private Material woolMaterial;

    ActivePerk activePerk1;
    ActivePerk activePerk2;
    PassivePerk passivePerk;

    private long activePerk1LastUsed = 0;
    private long activePerk2LastUsed = 0;
    private long passivePerkLastUsed = 0;

    private boolean isProtected = false;


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

    public void addWool(int wool) {
        this.wool += wool;
        player.getInventory().addItem(new ItemStack(woolMaterial));
    }

    public void removeWool(int wool) {
       this.wool -= wool;
       player.getInventory().removeItem(new ItemStack(woolMaterial, wool));
    }

    public void handleBlockPlace() {
        this.wool --;
    }

    public Material getWoolMaterial() {
        return woolMaterial;
    }

    public void setWoolMaterial(Material woolMaterial) {
        this.woolMaterial = woolMaterial;
    }

    public ActivePerk getActivePerk1() {
        return activePerk1;
    }

    public void setActivePerk1(ActivePerk activePerk1) {
        this.activePerk1 = activePerk1;

        ItemStack item = activePerk1.getItem();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Select Perk 1");
        item.setItemMeta(meta);

        player.getInventory().setItem(4, item);
    }

    public ActivePerk getActivePerk2() {
        return activePerk2;
    }

    public void setActivePerk2(ActivePerk activePerk2) {
        this.activePerk2 = activePerk2;

        ItemStack item = activePerk2.getItem();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Select Perk 2");
        item.setItemMeta(meta);

        player.getInventory().setItem(5, item);
    }

    public PassivePerk getPassivePerk() {
        return passivePerk;
    }

    public void setPassivePerk(PassivePerk passivePerk) {
        this.passivePerk = passivePerk;
    }

    public long getActivePerk1LastUsed() {
        return activePerk1LastUsed;
    }

    public void setActivePerk1LastUsed(long activePerk1LastUsed) {
        this.activePerk1LastUsed = activePerk1LastUsed;
    }

    public long getActivePerk2LastUsed() {
        return activePerk2LastUsed;
    }

    public void setActivePerk2LastUsed(long activePerk2LastUsed) {
        this.activePerk2LastUsed = activePerk2LastUsed;
    }

    public long getPassivePerkLastUsed() {
        return passivePerkLastUsed;
    }

    public void setPassivePerkLastUsed(long passivePerkLastUsed) {
        this.passivePerkLastUsed = passivePerkLastUsed;
    }

    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }

    public boolean isProtected() {
        return isProtected;
    }
}
