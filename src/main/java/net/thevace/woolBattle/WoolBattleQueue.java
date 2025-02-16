package net.thevace.woolBattle;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class WoolBattleQueue {

    private List<WoolbattlePlayer> queue = new ArrayList<WoolbattlePlayer>();

    private List<WoolbattlePlayer> team1 = new ArrayList<WoolbattlePlayer>();
    private List<WoolbattlePlayer> team2 = new ArrayList<WoolbattlePlayer>();

    private int teamSize;
    private WoolBattlePlayerManager playerManager;
    private QueueManager queueManager;

    private Map<WoolbattlePlayer, Integer> lifeVoting = new HashMap<>();

    public WoolBattleQueue(int teamSize, WoolBattlePlayerManager playerManager, QueueManager queueManager) {
        this.teamSize = teamSize;
        this.playerManager = playerManager;
        this.queueManager = queueManager;
    }

    public void joinQueue(WoolbattlePlayer player) {
        queue.add(player);
        player.getPlayer().sendMessage("Du bist einer Queue beigetreten" );

        if(team1.size() < team2.size()) {
            team1.add(player);
            player.setWoolMaterial(Material.RED_WOOL);
            player.getPlayer().sendMessage(ChatColor.RED + "Du bist dem Roten Team beigetreten");
        } else if(team2.size() < team1.size()) {
            team2.add(player);
            player.setWoolMaterial(Material.BLUE_WOOL);
            player.getPlayer().sendMessage(ChatColor.BLUE + "Du bist dem Blauen Team beigetreten");
        } else {
            team1.add(player);
            player.setWoolMaterial(Material.RED_WOOL);
            player.getPlayer().sendMessage(ChatColor.RED + "Du bist dem Roten Team beigetreten");
        }

//        if(team1.size() == teamSize && team2.size() == teamSize) {
//            startGame();
//        }

        player.getPlayer().getInventory().clear();

        addItems(player.getPlayer());

        setQueueScoreboard(player);
    }

    public void leaveQueue(WoolbattlePlayer player) {
        queue.remove(player);
        player.getPlayer().sendMessage("Du wurdest von der Queue entfernt");
        player.getPlayer().getInventory().clear();

        if(team1.contains(player)) {
            team1.remove(player);
        } else if(team2.contains(player)) {
            team2.remove(player);
        }
    }

    public void startGame() {

        Map<Integer, Integer> voteCount = new HashMap<>();

        for (int lifes : lifeVoting.values()) {
            voteCount.put(lifes, voteCount.getOrDefault(lifes, 0) + 1);
        }

        int mostVotedLives = -1;
        int highestVotes = 0;

        for (Map.Entry<Integer, Integer> entry : voteCount.entrySet()) {
            if (entry.getValue() > highestVotes) {
                mostVotedLives = entry.getKey();
                highestVotes = entry.getValue();
            }
        }

        WoolBattleGame game = new WoolBattleGame(mostVotedLives, team1, team2, playerManager);
        GameManager.addGame(game);
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
        return queue;
    }

    public void setQueue(List<WoolbattlePlayer> queue) {
        queue = queue;
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


    public void addItems(Player player) {

        ItemStack startGame = new ItemStack(Material.NETHER_STAR);
        ItemMeta startGameMeta = startGame.getItemMeta();
        startGameMeta.setDisplayName(ChatColor.MAGIC + "Start game");
        startGame.setItemMeta(startGameMeta);

        //player.getInventory().setItem(0, startgame);

        ItemStack teamSelect = new ItemStack(Material.RED_BED);
        ItemMeta teamSelectMeta = teamSelect.getItemMeta();
        teamSelectMeta.setDisplayName(ChatColor.RED + "Team select");
        teamSelect.setItemMeta(teamSelectMeta);

        player.getInventory().setItem(1, teamSelect);

        ItemStack perkSelector = new ItemStack(Material.CHEST);
        ItemMeta perkSelectorMeta = perkSelector.getItemMeta();
        perkSelectorMeta.setDisplayName(ChatColor.GOLD + "Perk selector");
        perkSelector.setItemMeta(perkSelectorMeta);

        player.getPlayer().getInventory().setItem(4, perkSelector);


        ItemStack voting = new ItemStack(Material.MAP);
        ItemMeta votingMeta = voting.getItemMeta();
        votingMeta.setDisplayName(ChatColor.GOLD + "Voting");
        voting.setItemMeta(votingMeta);

        player.getPlayer().getInventory().setItem(7, voting);

        ItemStack leaveQueue = new ItemStack(Material.BARRIER);
        ItemMeta leaveQueueMeta = leaveQueue.getItemMeta();
        leaveQueueMeta.setDisplayName(ChatColor.RED + "Leave queue");
        leaveQueue.setItemMeta(leaveQueueMeta);

        player.getInventory().setItem(8, leaveQueue);
    }


    public void setQueueScoreboard(WoolbattlePlayer player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = board.registerNewObjective("woolbattle", "dummy", ChatColor.GOLD + "WoolBattle");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score score1 = objective.getScore(ChatColor.AQUA + "Online: " + ChatColor.RESET + queue.size());
        score1.setScore(2);

        Score score2 = objective.getScore(ChatColor.AQUA + "Benötigt: " + ChatColor.RESET + teamSize * 2);
        score2.setScore(0);

        player.getPlayer().setScoreboard(board);
    }

    public void setLifeVoting(WoolbattlePlayer player, int lives) {
        lifeVoting.put(player, lives);
    }

    public Map<WoolbattlePlayer, Integer> getLifeVoting() {
        return lifeVoting;
    }
}
