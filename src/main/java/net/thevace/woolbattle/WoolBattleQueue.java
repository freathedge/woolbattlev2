package net.thevace.woolbattle;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class WoolBattleQueue {

    private String id;

    private List<WoolBattlePlayer> queue = new ArrayList<WoolBattlePlayer>();

    private List<WoolBattlePlayer> team1 = new ArrayList<WoolBattlePlayer>();
    private List<WoolBattlePlayer> team2 = new ArrayList<WoolBattlePlayer>();

    private int teamSize;
    private QueueManager queueManager;

    private Map<WoolBattlePlayer, Integer> lifeVoting = new HashMap<>();

    public WoolBattleQueue(int teamSize, QueueManager queueManager) {
        this.teamSize = teamSize;
        this.queueManager = queueManager;
        this.id = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5);
    }

    public void joinQueue(WoolBattlePlayer player) {
        queue.add(player);
        player.getPlayer().sendMessage("Du bist der Queue " + ChatColor.GOLD + id + ChatColor.RESET + " beigetreten");

        if(team1.size() < team2.size()) {
            addPlayerToTeam(team1, player);

        } else if(team2.size() < team1.size()) {
            addPlayerToTeam(team2, player);

        } else {
            addPlayerToTeam(team1, player);
        }

        for(WoolBattlePlayer p : team1) {
            setQueueScoreboard(p);
        }
        for(WoolBattlePlayer p : team2) {
            setQueueScoreboard(p);
        }

//        if(team1.size() == teamSize && team2.size() == teamSize) {
//            startGame();
//        }

        player.getPlayer().getInventory().clear();

        addItems(player.getPlayer());

        setQueueScoreboard(player);
    }

    public void leaveQueue(WoolBattlePlayer player) {
        queue.remove(player);
        player.getPlayer().sendMessage("Du wurdest von der Queue" + ChatColor.GOLD + id + ChatColor.RESET + " entfernt");
        player.getPlayer().getInventory().clear();

        team1.remove(player);
        team2.remove(player);

        WoolBattlePlayerManager.removePlayer(player.getPlayer());
        player.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
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

        if(mostVotedLives == -1) {
            mostVotedLives = 10;
        }

        WoolBattleGame game = new WoolBattleGame(mostVotedLives, team1, team2);
        WoolBattleGameManager.addGame(game);
        game.startGame();

        queueManager.removeQueue(this);
    }


    public List<WoolBattlePlayer> getTeam1() {
        return team1;
    }

    public List<WoolBattlePlayer> getTeam2() {
        return team2;
    }

    public List<WoolBattlePlayer> getQueue() {
        return queue;
    }

    public void setQueue(List<WoolBattlePlayer> queue) {
        queue = queue;
    }

    public void setTeam1(List<WoolBattlePlayer> team1) {
        this.team1 = team1;
    }

    public void setTeam2(List<WoolBattlePlayer> team2) {
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


    public void setQueueScoreboard(WoolBattlePlayer player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = board.registerNewObjective("woolbattle", "dummy", ChatColor.GOLD + "WoolBattle");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score score1 = objective.getScore(ChatColor.AQUA + "Online: " + ChatColor.RESET + queue.size());
        score1.setScore(2);

        Score score2 = objective.getScore(ChatColor.AQUA + "Benötigt: " + ChatColor.RESET + teamSize * 2);
        score2.setScore(0);

        player.getPlayer().setScoreboard(board);
    }

    public void setLifeVoting(WoolBattlePlayer player, int lives) {
        lifeVoting.put(player, lives);
    }

    public Map<WoolBattlePlayer, Integer> getLifeVoting() {
        return lifeVoting;
    }

    public void addPlayerToTeam(List<WoolBattlePlayer> team, WoolBattlePlayer player) {
        if(!team.contains(player)) {
            if(team.equals(team1)) {
                team2.remove(player);
                team1.add(player);
                player.setWoolMaterial(Material.RED_WOOL);
            } else if(team.equals(team2)) {
                team1.remove(player);
                team2.add(player);
                player.setWoolMaterial(Material.BLUE_WOOL);
            }

        }
    }

    public String getId() {
        return id;
    }

    public boolean isInQueue(WoolBattlePlayer player) {
        return queue.contains(player);
    }
}
