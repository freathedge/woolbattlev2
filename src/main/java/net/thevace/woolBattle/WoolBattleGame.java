package net.thevace.woolBattle;

import io.papermc.paper.datacomponent.item.Unbreakable;
import net.thevace.woolBattle.listener.WoolBattleGameListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WoolBattleGame {

    private List<WoolBattlePlayer> team1;
    private List<WoolBattlePlayer> team2;

    private List<WoolBattlePlayer> allPlayers;

    private int team1Health;
    private int team2Health;

    private WoolBattleGameListener listener;

    private WoolBattlePlayerManager playerManager;

    private List<Location> playerBlocks = new ArrayList<>();

    private Location Team1Spawn = new Location(Bukkit.getWorlds().get(0), 10.5, 21, -20.5, 0, 0);
    private Location Team2Spawn = new Location(Bukkit.getWorlds().get(0), 10.5, 21, 7.5, -180, 0);



    public WoolBattleGame(int teamHealth, List<WoolBattlePlayer> Team1, List<WoolBattlePlayer> Team2, WoolBattlePlayerManager playerManager) {
        this.listener = new WoolBattleGameListener(this, playerManager);

        this.team1Health = teamHealth;
        this.team2Health = teamHealth;
        this.team1 = Team1;
        this.team2 = Team2;
        this.playerManager = playerManager;
    }

    public void startGame() {
        Bukkit.getPluginManager().registerEvents(listener, Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("WoolBattle")));


        for (WoolBattlePlayer wbp : team1) {
            Player p = wbp.getPlayer();
            p.sendMessage("Woolbattle game started!");
            setPlayerInventory(wbp);
            setGameScoreboard(wbp);
            p.teleport(Team1Spawn);
        }
        for (WoolBattlePlayer wbp : team2) {
            Player p = wbp.getPlayer();
            p.sendMessage("Woolbattle game started!");
            setPlayerInventory(wbp);
            setGameScoreboard(wbp);
            p.teleport(Team2Spawn);
        }
    }

    public void endGame() {
        HandlerList.unregisterAll(listener);

        String message;

        if(team1Health > team2Health) {
            message = "Woolbattle game ended! Team 1 has won!";
        } else {
            message = "Woolbattle game ended! Team 2 has won!";
        }

        for (WoolBattlePlayer wbp : team1) {
            wbp.getPlayer().sendMessage(message);
            playerManager.removePlayer(wbp.getPlayer());
        }
        for (WoolBattlePlayer wbp : team2) {
            wbp.getPlayer().sendMessage(message);
            playerManager.removePlayer(wbp.getPlayer());
        }

        for(Location loc : playerBlocks) {
            loc.getBlock().setType(Material.AIR);
        }


        GameManager.removeGame(this);
    }

    public void handlePlayerDeath(WoolBattlePlayer player) {

        player.getPlayer().setNoDamageTicks(Integer.MAX_VALUE);

        Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("WoolBattle"), () -> {
            player.getPlayer().setNoDamageTicks(0);
        }, 100L);


        if(team1.contains(player)) {
            team1Health--;
            Bukkit.broadcastMessage("Team 1 hat ein Leben verloren: " + team1Health);
            player.getPlayer().teleport(Team1Spawn);
        } else if(team2.contains(player)) {
            team2Health--;
            Bukkit.broadcastMessage("Team 2 hat ein Leben verloren: " + team2Health);
            player.getPlayer().teleport(Team2Spawn);
        }

        player.setFreezed(false);

        if (team1Health == 0 || team2Health == 0) {
            endGame();
        }

        for (WoolBattlePlayer wbp : team1) {
            setGameScoreboard(wbp);
        }
        for (WoolBattlePlayer wbp : team2) {
            setGameScoreboard(wbp);
        }

        player.setActivePerk1LastUsed(0);
        player.setActivePerk2LastUsed(0);

    }

    public void handleWoolPlace(WoolBattlePlayer player, Block block) {
        player.handleBlockPlace();
        playerBlocks.add(block.getLocation());
    }

    public void handleWoolBreak(Player p, Block block) {
        WoolBattlePlayer player = playerManager.getWoolBattlePlayer(p);

        if(player.getWool() < 192) {
            player.addWool(1);
        }

        if(playerBlocks.contains(block.getLocation())) {
            block.getLocation().getBlock().setType(Material.AIR);
            playerBlocks.remove(block.getLocation());
        }
    }

    public boolean handlePlayerHit(Player damager, Player target) {
        WoolBattlePlayer wbpDamager = playerManager.getWoolBattlePlayer(damager);
        WoolBattlePlayer wbpTarget = playerManager.getWoolBattlePlayer(target);

        if(team1.contains(wbpDamager) && team1.contains(wbpTarget) || team2.contains(wbpDamager) && team2.contains(wbpTarget)) {
            return true;
        } else {
            return false;
        }
    }

    public void setPlayerInventory(WoolBattlePlayer player) {

        Inventory playerInv = player.getPlayer().getInventory();
        playerInv.clear();

        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.PUNCH, 2);
        bow.addEnchantment(Enchantment.INFINITY, 1);
        bow.addEnchantment(Enchantment.UNBREAKING, 1);

        ItemMeta bowMeta = bow.getItemMeta();
        bowMeta.setDisplayName(ChatColor.GOLD + "Bow");
        bowMeta.setUnbreakable(true);
        bow.setItemMeta(bowMeta);

        ItemStack shears = new ItemStack(Material.SHEARS);
        shears.addEnchantment(Enchantment.EFFICIENCY, 5);
        shears.addEnchantment(Enchantment.UNBREAKING, 1);

        ItemMeta shearsMeta = shears.getItemMeta();
        shearsMeta.setDisplayName(ChatColor.GOLD + "Shears");
        shearsMeta.setUnbreakable(true);
        shears.setItemMeta(shearsMeta);

        playerInv.setItem(0, bow);
        playerInv.setItem(1, shears);
        playerInv.setItem(2, player.getActivePerk1().getItem());
        playerInv.setItem(3, player.getActivePerk2().getItem());
        playerInv.setItem(4, player.getEnderperle().getItem());



        playerInv.setItem(17, new ItemStack(Material.ARROW, 1));


//        if(player.getActivePerk1() != null) {
//            player.getActivePerk1().addItem();
//        } else {
//            player.setActivePerk1(PerkManager.createPerkInstance(Pod.class, player));
//            player.getActivePerk1().addItem();
//        }
//
//        if(player.getActivePerk2() != null) {
//            player.getActivePerk2().addItem();
//        } else {
//            player.setActivePerk2(PerkManager.createPerkInstance(Enterhaken.class, player));
//            player.getActivePerk2().addItem();
//        }


    }

    public void setGameScoreboard(WoolBattlePlayer player) {
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective titel = board.registerNewObjective("woolbattle", "dummy", ChatColor.GOLD + "WoolBattle");
        titel.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score team1score = titel.getScore(ChatColor.RED + "Rotes Team leben: " + ChatColor.RESET + team1Health);
        team1score.setScore(2);

        Score team2score = titel.getScore(ChatColor.BLUE + "Blaues Team leben: " + ChatColor.RESET + team2Health);
        team2score.setScore(0);

        player.getPlayer().setScoreboard(board);
    }

    public boolean isPlayerInGame(WoolBattlePlayer player) {
        if(team1.contains(player) || team2.contains(player)) {
            return true;
        } else {
            return false;
        }
    }

    public void addToPlayerBlocks(Location loc) {
        playerBlocks.add(loc);
    }

    public List<Location> getPlayerBlocks() {
        return playerBlocks;
    }
}
