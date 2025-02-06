package net.thevace.woolBattle;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class WoolBattleQueue {

    private List<WoolbattlePlayer> Queue = new ArrayList<WoolbattlePlayer>();

    private List<WoolbattlePlayer> team1 = new ArrayList<WoolbattlePlayer>();
    private List<WoolbattlePlayer> team2 = new ArrayList<WoolbattlePlayer>();

    private int teamSize;
    private Plugin plugin;

    public WoolBattleQueue(int teamSize, Plugin plugin) {
        this.teamSize = teamSize;
        this.plugin = plugin;
    }

    public void joinQueue(Player player) {

        ItemStack item = new ItemStack(Material.RED_BED);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Team select");
        item.setItemMeta(meta);

        player.getInventory().setItem(1, item);

        Queue.add(new WoolbattlePlayer(player));

        if(team1.size() < team2.size()) {
            team1.add(new WoolbattlePlayer(player));
            player.sendMessage("You have joined team 1");
        } else if(team2.size() < team1.size()) {
            team2.add(new WoolbattlePlayer(player));
            player.sendMessage("You have joined team 2");
        } else {
            team1.add(new WoolbattlePlayer(player));
            player.sendMessage("You have joined team 1");
        }

        if(team1.size() == teamSize && team2.size() == teamSize) {
            WoolBattleGame game = new WoolBattleGame(10, team1, team2, plugin);
            game.startGame();
        }
    }

    public void leaveQueue(WoolbattlePlayer player) {
        Queue.remove(player);
    }

    public void startQueue() {
        WoolBattleGame game = new WoolBattleGame(10, team1, team2, plugin);
        game.startGame();
    }

    public List<WoolbattlePlayer> getTeam1() {
        return team1;
    }

    public List<WoolbattlePlayer> getTeam2() {
        return team2;
    }
}
