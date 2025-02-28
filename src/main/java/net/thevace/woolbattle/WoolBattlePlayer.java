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
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.sql.Timestamp;

public class WoolBattlePlayer {

    private Player player;
    private boolean isProtected = false;
    private Location lastBlockLocation;
    private Timestamp lastHit;
    private Player lastHitter;

    ActivePerk enderperle = new Enderperle(this);
    ActivePerk activePerk1 = new Pod(this);
    ActivePerk activePerk2 = new Enterhaken(this);
    PassivePerk passivePerk = new RocketJump(this);

    private Timestamp enderpearlLastUsed;
    private Timestamp activePerk1LastUsed;
    private Timestamp activePerk2LastUsed;
    private Timestamp passivePerkLastUsed;

    private int wool;
    private Material woolMaterial;
    private int maxWool = 192;
    private int woolBreakMultiplier = 1;

    private double doubleJumpVerticalPower = 1.0;
    private double doubleJumpHorizontalPower = 0.0;

    private int arrowsShot = 0;
    private boolean canDoubleJump = true;
    private int woolDamage = 1;
    private boolean isInDoubleJump = false;

    public WoolBattlePlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getWool() {
        return wool;
    }

    public void setWool(int wool) {
        this.wool = wool;
    }

    public void addWool(int wool) {
        if (this.wool < maxWool) {
            this.wool += wool;
            player.getInventory().addItem(new ItemStack(woolMaterial, wool));
        }
    }

    public void removeWool(int wool) {
        this.wool -= wool;
        player.getInventory().removeItem(new ItemStack(woolMaterial, wool));
    }

    public void updatePlayerWool() {
        player.getInventory().removeItem(new ItemStack(woolMaterial, 192));
        player.getInventory().addItem(new ItemStack(woolMaterial, wool));
    }

    public Material getWoolMaterial() {
        return woolMaterial;
    }

    public void setWoolMaterial(Material woolMaterial) {
        this.woolMaterial = woolMaterial;
    }

    public int getMaxWool() {
        return maxWool;
    }

    public void setMaxWool(int maxWool) {
        this.maxWool = maxWool;
    }

    public int getWoolBreakMultiplier() {
        return woolBreakMultiplier;
    }

    public void setWoolBreakMultiplier(int woolBreakMultiplier) {
        this.woolBreakMultiplier = woolBreakMultiplier;
    }

    public int getWoolDamage() {
        return woolDamage;
    }

    public void setWoolDamage(int woolDamage) {
        this.woolDamage = woolDamage;
    }

    // Perk-Zugriff


    public void setEnderperle(ActivePerk enderperle) {
        this.enderperle = enderperle;
    }

    public void setActivePerk1(ActivePerk activePerk1) {
        this.activePerk1 = activePerk1;
    }

    public void setActivePerk2(ActivePerk activePerk2) {
        this.activePerk2 = activePerk2;
    }

    public void setPassivePerk(PassivePerk passivePerk) {
        this.passivePerk = passivePerk;
    }

    public ActivePerk getEnderperle() {
        return enderperle;
    }

    public Listener getEnderperleListener() {
        return (Listener) enderperle;
    }

    public ActivePerk getActivePerk1() {
        return activePerk1;
    }

    public Listener getActivePerk1Listener() {
        return (Listener) activePerk1;
    }

    public ActivePerk getActivePerk2() {
        return activePerk2;
    }

    public Listener getActivePerk2Listener() {
        return (Listener) activePerk2;
    }

    public PassivePerk getPassivePerk() {
        return passivePerk;
    }

    public Listener getPassivePerkListener() {
        if(passivePerk instanceof Listener) {
            return (Listener) passivePerk;
        } else {
            return null;
        }
    }

    // Perk-Zeitstempel
    public Timestamp getEnderpearlLastUsed() {
        return enderpearlLastUsed;
    }

    public void setEnderpearlLastUsed(Timestamp enderpearlLastUsed) {
        this.enderpearlLastUsed = enderpearlLastUsed;
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

    // Status-Flags
    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean isProtected) {
        this.isProtected = isProtected;
    }

    // Location-Management
    public Location getLastBlockLocation() {
        return lastBlockLocation;
    }

    public void setLastBlockLocation(Location lastBlockLocation) {
        this.lastBlockLocation = lastBlockLocation;
    }

    // Springen und Bewegung
    public double getDoubleJumpVerticalPower() {
        return doubleJumpVerticalPower;
    }

    public void setDoubleJumpVerticalPower(double doubleJumpVerticalPower) {
        this.doubleJumpVerticalPower = doubleJumpVerticalPower;
    }

    public double getDoubleJumpHorizontalPower() {
        return doubleJumpHorizontalPower;
    }

    public void setDoubleJumpHorizontalPower(double doubleJumpHorizontalPower) {
        this.doubleJumpHorizontalPower = doubleJumpHorizontalPower;
    }

    public boolean canDoubleJump() {
        return canDoubleJump;
    }

    public void setCanDoubleJump(boolean canDoubleJump) {
        this.canDoubleJump = canDoubleJump;
    }

    public boolean isInDoubleJump() {
        return isInDoubleJump;
    }

    public void setInDoubleJump(boolean inDoubleJump) {
        isInDoubleJump = inDoubleJump;
    }

    // Pfeil-Statistik
    public int getArrowsShot() {
        return arrowsShot;
    }

    public void setArrowsShot(int arrowsShot) {
        this.arrowsShot = arrowsShot;
    }

    // Blockplatzierung
    public void handleBlockPlace() {
        this.wool--;
    }

    // Hit-Management
    public Timestamp getLastHit() {
        return lastHit;
    }

    public void setLastHit(Timestamp lastHit) {
        this.lastHit = lastHit;
    }

    public Player getLastHitter() {
        return lastHitter;
    }

    public void setLastHitter(Player lastHitter) {
        this.lastHitter = lastHitter;
    }
}
