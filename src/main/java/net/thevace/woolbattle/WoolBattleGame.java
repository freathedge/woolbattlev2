package net.thevace.woolbattle;

import net.thevace.woolbattle.listener.WoolBattleGameListener;
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

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WoolBattleGame {

    private List<WoolBattlePlayer> team1;
    private List<WoolBattlePlayer> team2;

    private List<WoolBattlePlayer> allPlayers = new ArrayList<>();

    private int team1Health;
    private int team2Health;

    private WoolBattleGameListener listener;

    private List<Location> playerBlocks = new ArrayList<>();

    private final Location team1Spawn = new Location(Bukkit.getWorlds().getFirst(), 10.5, 21, -20.5, 0, 0);
    private final Location team2Spawn = new Location(Bukkit.getWorlds().getFirst(), 10.5, 21, 7.5, -180, 0);


    public WoolBattleGame(int teamHealth, List<WoolBattlePlayer> Team1, List<WoolBattlePlayer> Team2) {
        this.listener = new WoolBattleGameListener(this);

        this.team1Health = teamHealth;
        this.team2Health = teamHealth;
        this.team1 = Team1;
        this.team2 = Team2;
    }

    public void startGame() {
        Bukkit.getPluginManager().registerEvents(listener, Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("WoolBattle")));

        for (WoolBattlePlayer wbp : team1) {
            Player p = wbp.getPlayer();
            p.sendMessage("Woolbattle game started!");
            p.teleport(team1Spawn);
        }
        for (WoolBattlePlayer wbp : team2) {
            Player p = wbp.getPlayer();
            p.sendMessage("Woolbattle game started!");
            p.teleport(team2Spawn);
        }

        allPlayers.addAll(team1);
        allPlayers.addAll(team2);

        for (WoolBattlePlayer wbp : allPlayers) {
            Player p = wbp.getPlayer();
            p.setAllowFlight(true);
            setPlayerInventory(wbp);
            setGameScoreboard(wbp);

            PerkListenerManager.registerPerkListener(this, wbp.getActivePerk1Listener());
            PerkListenerManager.registerPerkListener(this, wbp.getActivePerk2Listener());
            if (wbp.getPassivePerkListener() != null) {
                PerkListenerManager.registerPerkListener(this, wbp.getPassivePerkListener());
            }
            wbp.getPassivePerk().applyEffect();
            PerkListenerManager.registerPerkListener(this, wbp.getEnderperleListener());
        }
    }

    public void endGame() {
        String message;

        if (team1.size() > team2.size()) {
            message = "Woolbattle game ended! Team 1 has won!";
        } else {
            message = "Woolbattle game ended! Team 2 has won!";
        }

        for (WoolBattlePlayer wbp : allPlayers) {
            wbp.getPlayer().sendMessage(message);
            WoolBattlePlayerManager.removePlayer(wbp.getPlayer());
        }

        for (Location loc : playerBlocks) {
            loc.getBlock().setType(Material.AIR);
        }

        HandlerList.unregisterAll(listener);
        PerkListenerManager.unregisterListeners(this);

        GameManager.removeGame(this);
    }

    public void checkGameEnd() {
        if (team1.isEmpty() || team2.isEmpty()) {
            endGame();
        }
    }

    public void handlePlayerDeath(WoolBattlePlayer player) {
        if (team1.contains(player)) {
            player.getPlayer().teleport(team1Spawn);
            if (team1Health <= 0) {
                team1.remove(player);
                setSpectator(player);
                checkGameEnd();
                return;
            }

        } else if (team2.contains(player)) {
            player.getPlayer().teleport(team2Spawn);
            if (team2Health <= 0) {
                team2.remove(player);
                setSpectator(player);
                checkGameEnd();
                return;
            }
        }


        player.getPlayer().setNoDamageTicks(Integer.MAX_VALUE);

        Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("WoolBattle"), () -> {
            player.getPlayer().setNoDamageTicks(0);
        }, 100L);

        if (player.getLastHit() != null) {
            if (Duration.between(player.getLastHit().toInstant(), Instant.now()).getSeconds() < 10) {
                if (team1.contains(player)) {
                    team1Health--;
                } else if (team2.contains(player)) {
                    team2Health--;
                }
            }
        }

        player.setFreezed(false);


        for (WoolBattlePlayer wbp : allPlayers) {
            setGameScoreboard(wbp);
        }
    }

    public void handleWoolPlace(WoolBattlePlayer player, Block block) {
        player.handleBlockPlace();
        playerBlocks.add(block.getLocation());
    }

    public void handleWoolBreak(Player p, Block block) {
        WoolBattlePlayer player = WoolBattlePlayerManager.getWoolBattlePlayer(p);


        player.addWool(player.getWoolBreakMultiplier());

        if (playerBlocks.contains(block.getLocation())) {
            block.getLocation().getBlock().setType(Material.AIR);
            playerBlocks.remove(block.getLocation());
        }
    }

    public boolean handlePlayerHit(Player damager, Player target) {
        WoolBattlePlayer wbpDamager = WoolBattlePlayerManager.getWoolBattlePlayer(damager);
        WoolBattlePlayer wbpTarget = WoolBattlePlayerManager.getWoolBattlePlayer(target);

        if (team1.contains(wbpDamager) && team1.contains(wbpTarget) || team2.contains(wbpDamager) && team2.contains(wbpTarget)) {
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
        bow.addUnsafeEnchantment(Enchantment.KNOCKBACK, 3);

        ItemMeta bowMeta = bow.getItemMeta();
        bowMeta.setDisplayName(ChatColor.GOLD + "Bow");
        bowMeta.setUnbreakable(true);
        bow.setItemMeta(bowMeta);


        ItemStack shears = new ItemStack(Material.SHEARS);
        shears.addEnchantment(Enchantment.EFFICIENCY, 5);
        shears.addEnchantment(Enchantment.UNBREAKING, 1);
        shears.addUnsafeEnchantment(Enchantment.KNOCKBACK, 3);

        ItemMeta shearsMeta = shears.getItemMeta();
        shearsMeta.setDisplayName(ChatColor.GOLD + "Shears");
        shearsMeta.setUnbreakable(true);
        shears.setItemMeta(shearsMeta);

        playerInv.setItem(0, bow);
        playerInv.setItem(1, shears);
        playerInv.setItem(2, player.getActivePerk1().addEnchantment(player.getActivePerk1().getItem()));
        playerInv.setItem(3, player.getActivePerk2().addEnchantment(player.getActivePerk2().getItem()));
        playerInv.setItem(4, player.getEnderperle().addEnchantment(player.getEnderperle().getItem()));

        playerInv.setItem(8, player.getPassivePerk().getItem());
        player.getPassivePerk().applyEffect();


        playerInv.setItem(17, new ItemStack(Material.ARROW, 1));
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
        if (team1.contains(player) || team2.contains(player)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isPlayerInGame(Player p) {
        WoolBattlePlayer player = WoolBattlePlayerManager.getWoolBattlePlayer(p);
        if (team1.contains(player) || team2.contains(player)) {
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

    public void setSpectator(WoolBattlePlayer player) {
        System.out.println("set " + player.getPlayer().getName() + " spectator");
    }
}
