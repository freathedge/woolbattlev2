package net.thevace.woolBattle;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WoolBattleQueue {

    private List<WoolbattlePlayer> Queue = new ArrayList<WoolbattlePlayer>();

    private List<WoolbattlePlayer> team1 = new ArrayList<WoolbattlePlayer>();
    private List<WoolbattlePlayer> team2 = new ArrayList<WoolbattlePlayer>();

    private int teamSize;
    private WoolBattlePlayerManager playerManager;
    private QueueManager queueManager;

    public WoolBattleQueue(int teamSize, WoolBattlePlayerManager playerManager, QueueManager queueManager) {
        this.teamSize = teamSize;
        this.playerManager = playerManager;
        this.queueManager = queueManager;
    }

    public void joinQueue(WoolbattlePlayer player) {
        Queue.add(player);
        player.getPlayer().sendMessage("Du bist einer Queue beigetreten" );

        if(team1.size() < team2.size()) {
            team1.add(player);
            player.getPlayer().sendMessage(ChatColor.RED + "Du bist dem Roten Team beigetreten");
        } else if(team2.size() < team1.size()) {
            team2.add(player);
            player.getPlayer().sendMessage(ChatColor.BLUE + "Du bist dem Blauen Team beigetreten");
        } else {
            team1.add(player);
            player.getPlayer().sendMessage(ChatColor.RED + "Du bist dem Roten Team beigetreten");
        }

        if(team1.size() == teamSize && team2.size() == teamSize) {
            WoolBattleGame game = new WoolBattleGame(10, team1, team2, playerManager);
            game.startGame();
        }

        addTeamSwitcher(player.getPlayer());
        addLeaveQueue(player.getPlayer());
        addStartGame(player.getPlayer());
    }

    public void leaveQueue(WoolbattlePlayer player) {
        Queue.remove(player);
        player.getPlayer().sendMessage("Du wurdest von der Queue entfernt");
        player.getPlayer().getInventory().clear();

        if(team1.contains(player)) {
            team1.remove(player);
        } else if(team2.contains(player)) {
            team2.remove(player);
        }
    }

    public void startGame() {
        WoolBattleGame game = new WoolBattleGame(10, team1, team2, playerManager);
        game.startGame();

        queueManager.removeQueue(this);
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

    public void addStartGame(Player player) {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.MAGIC + "Start game");
        item.setItemMeta(meta);

        player.getInventory().setItem(0, item);
    }
}
