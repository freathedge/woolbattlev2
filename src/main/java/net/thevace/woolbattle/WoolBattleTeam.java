package net.thevace.woolbattle;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class WoolBattleTeam {
    private  List<WoolBattlePlayer> players = new ArrayList<WoolBattlePlayer>();
    private ChatColor chatColor;
    private Material woolColorMaterial;
    private Color armorColor;
    private String colorName;
    private int lives;

    public WoolBattleTeam(String colorName, ChatColor chatColor, Material colorMaterial, Color armorColor) {
        this.colorName = colorName;
        this.chatColor = chatColor;
        this.woolColorMaterial = colorMaterial;
        this.armorColor = armorColor;
    }

    public List<WoolBattlePlayer> getPlayers() {
        return players;
    }

    public ChatColor getChatColor() {
        return chatColor;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void removeLive() {
        this.lives--;
    }

    public Material getWoolColorMaterial() {
        return woolColorMaterial;
    }

    public String getColorName() {
        return colorName;
    }

    public Color getArmorColor() {
        return armorColor;
    }
}
