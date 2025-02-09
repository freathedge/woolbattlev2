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

    public WoolBattleQueue(int teamSize) {
        this.teamSize = teamSize;
    }

    public void joinQueue(WoolbattlePlayer player) {
        Queue.add(player);
        player.getPlayer().sendMessage("Du bist einer Queue beigetreten" );
        addTeamSwitcher(player.getPlayer());
        addLeaveQueue(player.getPlayer());
    }

    public void leaveQueue(WoolbattlePlayer player) {
        Queue.remove(player);
        player.getPlayer().sendMessage("Du wurdest von der Queue entfernt");
        player.getPlayer().getInventory().clear();
    }

    public void startQueue() {
        WoolBattleGame game = new WoolBattleGame(10, team1, team2);
        game.startGame();
    }

    public List<WoolbattlePlayer> getTeam1() {
        return team1;
    }

    public List<WoolbattlePlayer> getTeam2() {
        return team2;
    }

    public List<WoolbattlePlayer> getQueue() {
        return Queue;
    }

    public void removeFromTeam(WoolbattlePlayer player) {
        team1.remove(player);
        team2.remove(player);
        player.getPlayer().sendMessage("Removed from teams");

        player.getPlayer().sendMessage("Team 1: ");
        for(WoolbattlePlayer wbp : team1) {
            player.getPlayer().sendMessage(wbp.getPlayer().getName());
        }
        player.getPlayer().sendMessage("Team 2: ");
        for(WoolbattlePlayer wbp : team2) {
            player.getPlayer().sendMessage(wbp.getPlayer().getName());
        }
    }

    public void setQueue(List<WoolbattlePlayer> queue) {
        Queue = queue;
    }

    public void setTeam1(List<WoolbattlePlayer> team1) {
        this.team1 = team1;
    }

    public void setTeam2(List<WoolbattlePlayer> team2) {
        this.team2 = team2;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public int getTotalPlayers() {
        return team1.size() + team2.size();
    }

    public void addTeamSwitcher(Player player) {
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
            WoolBattleGame game = new WoolBattleGame(10, team1, team2);
            game.startGame();
        }
    }

    public void addLeaveQueue(Player player) {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Leave queue");
        item.setItemMeta(meta);

        player.getInventory().setItem(7, item);
    }

    public void addLifeVoting(Player player) {

    }
}
