package net.thevace.woolbattle;

import net.thevace.woolbattle.perks.ActivePerk;
import net.thevace.woolbattle.perks.activeperks.Enderperle;
import net.thevace.woolbattle.perks.activeperks.Enterhaken;
import net.thevace.woolbattle.perks.activeperks.Pod;
import net.thevace.woolbattle.perks.PassivePerk;
import net.thevace.woolbattle.perks.passiveperks.RocketJump;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.Timestamp;

public class WoolBattlePlayer {
    private Player player;
    private int wool;
    private Material woolMaterial;

    ActivePerk enderperle = new Enderperle(this);
    ActivePerk activePerk1 = new Pod(this);
    ActivePerk activePerk2 = new Enterhaken(this);
    PassivePerk passivePerk = new RocketJump(this);

    private Timestamp enderpearlLastUsed;
    private Timestamp activePerk1LastUsed;
    private Timestamp activePerk2LastUsed;
    private Timestamp passivePerkLastUsed;

    private boolean isProtected = false;
    private boolean isFreezed = false;

    private Location lastBlockLocation;

    private Timestamp lastHit;



    public WoolBattlePlayer(Player player) {
        this.player = player;
    }



    public void addWool(int wool) {
        this.wool += wool;
        player.getInventory().addItem(new ItemStack(woolMaterial));
    }

    public void removeWool(int wool) {
       this.wool -= wool;
       player.getInventory().removeItem(new ItemStack(woolMaterial, wool));
    }

    public void updatePlayerWool() {
        player.getInventory().removeItem(new ItemStack(woolMaterial, 192));
        player.getInventory().addItem(new ItemStack(woolMaterial, wool));
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
    }

    public ActivePerk getActivePerk2() {
        return activePerk2;
    }

    public void setActivePerk2(ActivePerk activePerk2) {
        this.activePerk2 = activePerk2;
    }

    public PassivePerk getPassivePerk() {
        return passivePerk;
    }

    public void setPassivePerk(PassivePerk passivePerk) {
        this.passivePerk = passivePerk;
    }

    public Timestamp getActivePerk1LastUsed() {
        return activePerk1LastUsed;
    }

    public void setActivePerk1LastUsed(Timestamp activePerk1LastUsed) {
        this.activePerk1LastUsed = activePerk1LastUsed;
    }

    public Timestamp getActivePerk2LastUsed() {
        return activePerk2LastUsed;
    }

    public void setActivePerk2LastUsed(Timestamp activePerk2LastUsed) {
        this.activePerk2LastUsed = activePerk2LastUsed;
    }

    public Timestamp getPassivePerkLastUsed() {
        return passivePerkLastUsed;
    }

    public void setPassivePerkLastUsed(Timestamp passivePerkLastUsed) {
        this.passivePerkLastUsed = passivePerkLastUsed;
    }

    public void setProtected(boolean aProtected) {
        isProtected = aProtected;
    }

    public boolean isProtected() {
        return isProtected;
    }

    public boolean isFreezed() {
        return isFreezed;
    }

    public void setFreezed(boolean freezed) {
        isFreezed = freezed;
    }

    public Location getLastBlockLocation() {
        return lastBlockLocation;
    }

    public void setLastBlockLocation(Location lastBlockLocation) {
        this.lastBlockLocation = lastBlockLocation;
    }

    public ActivePerk getEnderperle() {
        return enderperle;
    }

    public Timestamp getEnderpearlLastUsed() {
        return enderpearlLastUsed;
    }

    public Timestamp getLastHit() {
        return lastHit;
    }

    public void setLastHit(Timestamp lastHit) {
        this.lastHit = lastHit;
    }
}
