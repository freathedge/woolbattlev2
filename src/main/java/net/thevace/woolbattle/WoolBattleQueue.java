package net.thevace.woolbattle;

import net.thevace.woolbattle.maps.Blocks_2x1;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
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

    private List<WoolBattlePlayer> queue = new ArrayList<>();

    private List<WoolBattleTeam> teams = new ArrayList<>();

    private int teamSize;
    private QueueManager queueManager;

    private Map<WoolBattlePlayer, Integer> lifeVoting = new HashMap<>();

    private static final List<ChatColor> teamColors = Arrays.asList(
            ChatColor.RED, ChatColor.BLUE, ChatColor.GREEN, ChatColor.YELLOW,
            ChatColor.GOLD, ChatColor.LIGHT_PURPLE, ChatColor.DARK_PURPLE, ChatColor.AQUA
    );

    private static final List<Material> teamMaterials = Arrays.asList(
            Material.RED_WOOL, Material.BLUE_WOOL, Material.GREEN_WOOL, Material.YELLOW_WOOL,
            Material.ORANGE_WOOL, Material.PINK_WOOL, Material.PURPLE_WOOL, Material.LIGHT_BLUE_WOOL
    );

    private static final List<String> teamColorNames = Arrays.asList(
            "Red", "Blue", "Green", "Yellow",
            "Gold", "Pink", "Purple", "Aqua"
    );

    private static final List<Color> teamRGBColors = Arrays.asList(
            Color.fromRGB(252, 84, 84),
            Color.fromRGB(84, 134, 252),
            Color.fromRGB(84, 252, 84),
            Color.fromRGB(252, 252, 84),
            Color.fromRGB(252, 168, 0),
            Color.fromRGB(252, 84, 252),
            Color.fromRGB(126, 34, 206),
            Color.fromRGB(84, 218, 252)
    );

    public WoolBattleQueue(int teams, QueueManager queueManager) {
        this.queueManager = queueManager;
        this.id = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5);

        for (int i = 0; i < teams; i++) {
            this.teams.add(new WoolBattleTeam(teamColorNames.get(i), teamColors.get(i), teamMaterials.get(i), teamRGBColors.get(i)));
        }
    }

    public void joinQueue(WoolBattlePlayer player) {
        queue.add(player);
        player.getPlayer().sendMessage("Du bist der Queue " + ChatColor.GOLD + id + ChatColor.RESET + " beigetreten");

        for (WoolBattleTeam team : teams) {
            List<WoolBattlePlayer> teamList = team.getPlayers();
            if (teamList.size() < teamSize) {
                teamList.add(player);
            }
        }

        player.getPlayer().getInventory().clear();

        addItems(player.getPlayer());

        setQueueScoreboard(player);
    }

    public void leaveQueue(WoolBattlePlayer player) {
        queue.remove(player);
        player.getPlayer().sendMessage("Du wurdest von der Queue " + ChatColor.GOLD + id + ChatColor.RESET + " entfernt");
        player.getPlayer().getInventory().clear();

        for (WoolBattleTeam team : teams) {
            List<WoolBattlePlayer> teamList = team.getPlayers();
            teamList.remove(player);
        }

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

        if (mostVotedLives == -1) {
            mostVotedLives = 10;
        }

        WoolBattleGame game = new WoolBattleGame(new Blocks_2x1(), teams, mostVotedLives);
        WoolBattleGameManager.addGame(game);
        game.startGame();

        queueManager.removeQueue(this);
    }


    public List<WoolBattlePlayer> getQueue() {
        return queue;
    }

    public void setQueue(List<WoolBattlePlayer> queue) {
        queue = queue;
    }

    public int getTeamSize() {
        return teamSize;
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

    public String getId() {
        return id;
    }

    public boolean isInQueue(WoolBattlePlayer player) {
        return queue.contains(player);
    }

    public List<WoolBattleTeam> getTeams() {
        return teams;
    }
}
