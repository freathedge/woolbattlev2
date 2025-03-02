package net.thevace.woolbattle;

import net.thevace.woolbattle.listener.*;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class WoolBattleGame {

    private WoolBattleMap map;

    private Map<Integer, WoolBattleTeam> teams = new HashMap<>();

    private List<WoolBattlePlayer> allPlayers = new ArrayList<>();

    private Map<Integer, Integer> teamLives = new HashMap<>();

    private List<Location> playerBlocks = new ArrayList<>();

    private HashMap<Location, Integer> hitBlocks = new HashMap<>();

    public WoolBattleGame(WoolBattleMap map, List<WoolBattleTeam> teams, int teamHealth) {
        this.map = map;

        for (int i = 0; i < teams.size(); i++) {
            this.teams.put(i, teams.get(i));
        }

        for (WoolBattleTeam team : teams) {
            team.setLives(teamHealth);
        }
    }

    public void startGame() {
        registerListener();

        for(Integer team : teams.keySet()) {
            List<WoolBattlePlayer> players = teams.get(team).getPlayers();
            Map<Integer, Location> teamSpawns = map.getTeamSpawns();
            for(WoolBattlePlayer wbp : players) {
                allPlayers.add(wbp);
                Player p = wbp.getPlayer();
                p.sendMessage("Woolbattle game started!");
                p.teleport(teamSpawns.get(team));
            }
        }

        for (WoolBattlePlayer wbp : allPlayers) {
            setPlayerInventory(wbp);
            setPlayerArmor(wbp);
            setGameScoreboard(wbp);
            startSpawnProtection(wbp);

            PerkListenerManager.registerPerkListener(this, wbp.getActivePerk1Listener());
            PerkListenerManager.registerPerkListener(this, wbp.getActivePerk2Listener());
            if (wbp.getPassivePerkListener() != null) {
                PerkListenerManager.registerPerkListener(this, wbp.getPassivePerkListener());
            }
            wbp.getPassivePerk().applyEffect();
            PerkListenerManager.registerPerkListener(this, wbp.getEnderperleListener());
        }

        showActionBar();
    }

    public void registerListener() {
        Bukkit.getPluginManager().registerEvents(new ListenerBlockBreak(), Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("WoolBattle")));
        Bukkit.getPluginManager().registerEvents(new ListenerBlockPlace(), Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("WoolBattle")));
        Bukkit.getPluginManager().registerEvents(new ListenerFallDamageEvent(), Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("WoolBattle")));
        Bukkit.getPluginManager().registerEvents(new ListenerEntityDamageByEntity(), Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("WoolBattle")));
        Bukkit.getPluginManager().registerEvents(new ListenerEntityShootBowEvent(), Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("WoolBattle")));
        Bukkit.getPluginManager().registerEvents(new ListenerItemDrop(), Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("WoolBattle")));
        Bukkit.getPluginManager().registerEvents(new ListenerPlayerMove(), Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("WoolBattle")));
        Bukkit.getPluginManager().registerEvents(new ListenerPlayerToggleFlight(), Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("WoolBattle")));
        Bukkit.getPluginManager().registerEvents(new ListenerProjectileHit(), Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("WoolBattle")));
        Bukkit.getPluginManager().registerEvents(new ListenerItemPickup(), Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("WoolBattle")));
    }

    public void endGame(WoolBattleTeam winningTeam) {
        String message;

//        if (team1.size() > team2.size()) {
//            message = "Woolbattle game ended! Team 1 has won!";
//        } else {
//            message = "Woolbattle game ended! Team 2 has won!";
//        }
//
//        for (WoolBattlePlayer wbp : allPlayers) {
//            wbp.getPlayer().sendMessage(message);
//            WoolBattlePlayerManager.removePlayer(wbp.getPlayer());
//        }

        for (Location loc : playerBlocks) {
            loc.getBlock().setType(Material.AIR);
        }

        HandlerList.unregisterAll();
        PerkListenerManager.unregisterListeners(this);

        WoolBattleGameManager.removeGame(this);
    }

    public void checkGameEnd() {
        WoolBattleTeam winningTeam = null;

        for (WoolBattleTeam team : teams.values()) {
            if (!team.getPlayers().isEmpty()) {
                if (winningTeam != null) {
                    return;
                }
                winningTeam = team;
                endGame(winningTeam);
            }
        }
    }

    public void handlePlayerDeath(WoolBattlePlayer player) {
        for(Integer team : teams.keySet()) {
            List<WoolBattlePlayer> players = teams.get(team).getPlayers();
            if(players.contains(player)) {
                player.getPlayer().teleport(map.getTeamSpawns().get(team));
                if(teamLives.get(team) <= 0) {
                    players.remove(player);
                    setSpectator(player);
                    checkGameEnd();
                    return;
                }

            }
        }
        player.setProtected(true);

        Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("WoolBattle"), () -> {
            player.setProtected(false);
        }, 60L);

        if (player.getLastHit() != null && player.getLastHitter() != null) {
            if (Duration.between(player.getLastHit().toInstant(), Instant.now()).getSeconds() < 10) {
                String message = "Der Spieler ";
                for(Integer team : teams.keySet()) {
                    List<WoolBattlePlayer> players = teams.get(team).getPlayers();
                    if(players.contains(player)) {
                        teams.get(team).removeLive();
                        message += teams.get(team).getChatColor() + player.getPlayer().getName();
                    }
                }
                message += ChatColor.RESET + " wurde von ";

                for(Integer team : teams.keySet()) {
                    List<WoolBattlePlayer> players = teams.get(team).getPlayers();
                    if(players.contains(player)) {
                        teams.get(team).removeLive();
                        message += teams.get(team).getChatColor() + player.getLastHitter().getName();
                    }
                }
                message += ChatColor.RESET + " getötet.";
                Bukkit.broadcastMessage(message);
            }
        }

        player.getPlayer().getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0.1);
        player.getPlayer().getAttribute(Attribute.JUMP_STRENGTH).setBaseValue(0.42);
        player.getPlayer().setAllowFlight(true);


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

    public boolean checkPlayerHit(Player damager, Player target) {
        WoolBattlePlayer wbpDamager = WoolBattlePlayerManager.getWoolBattlePlayer(damager);
        WoolBattlePlayer wbpTarget = WoolBattlePlayerManager.getWoolBattlePlayer(target);
        for(Integer team : teams.keySet()) {
            List<WoolBattlePlayer> players = teams.get(team).getPlayers();
            if(players.contains(wbpDamager) && players.contains(wbpTarget)) {
                return true;
            }
        }
        return false;
    }

    public void setPlayerArmor(WoolBattlePlayer player) {
        Player p = player.getPlayer();
        for(WoolBattleTeam team : teams.values()) {
            if(team.getPlayers().contains(player)) {
                p.getInventory().setHelmet(createColoredArmor(Material.LEATHER_HELMET, team.getArmorColor()));
                p.getInventory().setChestplate(createColoredArmor(Material.LEATHER_CHESTPLATE, team.getArmorColor()));
                p.getInventory().setLeggings(createColoredArmor(Material.LEATHER_LEGGINGS, team.getArmorColor()));
                p.getInventory().setBoots(createColoredArmor(Material.LEATHER_BOOTS, team.getArmorColor()));
            }
        }
    }

    private static ItemStack createColoredArmor(Material material, Color color) {
        ItemStack item = new ItemStack(material);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        if (meta != null) {
            meta.setColor(color);
            meta.setUnbreakable(true);
            item.setItemMeta(meta);
        }
        return item;
    }

    public void setPlayerInventory(WoolBattlePlayer player) {
        Inventory playerInv = player.getPlayer().getInventory();
        playerInv.clear();

        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.PUNCH, 2);
        bow.addEnchantment(Enchantment.INFINITY, 1);
        bow.addEnchantment(Enchantment.UNBREAKING, 1);
        bow.addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);

        ItemMeta bowMeta = bow.getItemMeta();
        bowMeta.setDisplayName(ChatColor.GOLD + "Bow");
        bowMeta.setUnbreakable(true);
        bow.setItemMeta(bowMeta);


        ItemStack shears = new ItemStack(Material.SHEARS);
        shears.addEnchantment(Enchantment.EFFICIENCY, 5);
        shears.addEnchantment(Enchantment.UNBREAKING, 1);
        shears.addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);

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

        int score = 0;
        for (Map.Entry<Integer, WoolBattleTeam> entry : teams.entrySet()) {
            WoolBattleTeam team = entry.getValue();
            String teamColor = team.getChatColor().toString();
            int teamLives = team.getLives();

            String teamLabel = ChatColor.GOLD + "» " + teamColor + "❤" + teamLives + "❤" + ChatColor.GOLD + "« " + teamColor + " " + team.getChatColor().name();
            Score teamScore = titel.getScore(teamLabel);
            teamScore.setScore(score);

            score++;
        }

        player.getPlayer().setScoreboard(board);
    }


    public boolean isPlayerInGame(WoolBattlePlayer player) {
        for(WoolBattleTeam team : teams.values()) {
            if(team.getPlayers().contains(player)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPlayerInGame(Player player) {
        for(WoolBattleTeam team : teams.values()) {
            if(team.getPlayers().contains(WoolBattlePlayerManager.getWoolBattlePlayer(player))) {
                return true;
            }
        }
        return false;
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

    public void addBlockHit(Location loc, int count) {
        hitBlocks.put(loc, hitBlocks.getOrDefault(loc, 0) + count);
    }

    public int getBlockHit(Location loc) {
        return hitBlocks.get(loc);
    }

    public void showActionBar() {

        new BukkitRunnable() {
            @Override
            public void run() {
                for (WoolBattlePlayer wbp : allPlayers) {
                    Player p = wbp.getPlayer();
                    p.sendActionBar(ChatColor.DARK_GRAY + "» " + ChatColor.GOLD + (int) p.getLocation().getY() + ChatColor.DARK_GRAY + " «");
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("WoolBattle"), 0L, 1L);
    }

    public void startSpawnProtection(WoolBattlePlayer player) {
        int totalTicks = 10 * 20;
        player.setProtected(true);

        new BukkitRunnable() {
            int ticksLeft = totalTicks;

            @Override
            public void run() {
                if (ticksLeft <= 0) {
                    player.getPlayer().setExp(0f);
                    player.setProtected(false);
                    this.cancel();
                    return;
                }

                float progress = (float) ticksLeft / totalTicks;
                player.getPlayer().setExp(progress);

                ticksLeft--;
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("WoolBattle"), 0L, 1L);
    }

    public void startDJCooldown(WoolBattlePlayer player) {
        player.setCanDoubleJump(false);
        player.getPlayer().setFoodLevel(6);

        new BukkitRunnable() {
            int currentFoodLevel = 6;

            @Override
            public void run() {
                if(currentFoodLevel < 20) {
                    currentFoodLevel += 1;
                    player.getPlayer().setFoodLevel(currentFoodLevel);
                } else {
                    cancel();
                    player.setCanDoubleJump(true);
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("WoolBattle"), 1L, 5L);
    }
}
