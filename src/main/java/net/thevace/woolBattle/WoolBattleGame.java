package net.thevace.woolBattle;

import net.thevace.woolBattle.listener.WoolBattleGameListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Objects;

public class WoolBattleGame {

    private List<WoolbattlePlayer> team1;
    private List<WoolbattlePlayer> team2;

    private List<WoolbattlePlayer> allPlayers;

    private int Team1Health;
    private int Team2Health;

    private WoolBattleGameListener listener;

    private WoolBattlePlayerManager playerManager;



    public WoolBattleGame(int teamHealth, List<WoolbattlePlayer> Team1, List<WoolbattlePlayer> Team2, WoolBattlePlayerManager playerManager) {
        this.listener = new WoolBattleGameListener(this, playerManager);

        this.Team1Health = teamHealth;
        this.Team2Health = teamHealth;
        this.team1 = Team1;
        this.team2 = Team2;
        this.playerManager = playerManager;
    }

    public void startGame() {
        Bukkit.getPluginManager().registerEvents(listener, Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("WoolBattle")));


        for (WoolbattlePlayer wbp : team1) {
            Player p = wbp.getPlayer();
            p.sendMessage("Woolbattle game started!");
            setPlayerInventory(wbp);
        }
        for (WoolbattlePlayer wbp : team2) {
            Player p = wbp.getPlayer();
            p.sendMessage("Woolbattle game started!");
            setPlayerInventory(wbp);
        }
    }

    public void endGame() {
        PlayerMoveEvent.getHandlerList().unregister(listener);
        if(Team1Health > Team2Health) {
            for (WoolbattlePlayer wbp : team1) {
                Player p = wbp.getPlayer();
                p.sendMessage("Woolbattle game ended! Team 1 has won!");
            }
            for (WoolbattlePlayer wbp : team2) {
                Player p = wbp.getPlayer();
                p.sendMessage("Woolbattle game ended! Team 1 has won!");
            }
        } else {
            for (WoolbattlePlayer wbp : team1) {
                Player p = wbp.getPlayer();
                p.sendMessage("Woolbattle game ended! Team 1 has won!");
            }
            for (WoolbattlePlayer wbp : team2) {
                Player p = wbp.getPlayer();
                p.sendMessage("Woolbattle game ended! Team 1 has won!");
            }
        }
    }

    public void handlePlayerDeath(WoolbattlePlayer player) {
        if(team1.contains(player)) {
            Team1Health--;
            Bukkit.broadcastMessage("Team 1 hat ein Leben verloren: " + Team1Health);
        } else if(team2.contains(player)) {
            Team2Health--;
            Bukkit.broadcastMessage("Team 2 hat ein Leben verloren: " + Team2Health);
        }


        player.getPlayer().teleport(player.getPlayer().getWorld().getSpawnLocation());

        if (Team1Health == 0 || Team2Health == 0) {
            endGame();
        }
    }

    public void handleWoolBreak(Player p) {
        WoolbattlePlayer player = playerManager.getWoolBattlePlayer(p);
        if(player.getWool() < 192) {
            player.addWool(1);
        }
    }

    public boolean handlePlayerHit(Player damager, Player target) {
        WoolbattlePlayer wbpDamager = playerManager.getWoolBattlePlayer(damager);
        WoolbattlePlayer wbpTarget = playerManager.getWoolBattlePlayer(target);

        if(team1.contains(wbpDamager) && team2.contains(wbpTarget) || team2.contains(wbpDamager) && team2.contains(wbpTarget)) {
            return true;
        } else {
            return false;
        }
    }

    public void setPlayerInventory(WoolbattlePlayer player) {

        Inventory playerInv = player.getPlayer().getInventory();
        playerInv.clear();

        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.PUNCH, 2);

        ItemStack shears = new ItemStack(Material.SHEARS);
        shears.addEnchantment(Enchantment.EFFICIENCY, 5);

        playerInv.addItem(bow);
        playerInv.addItem(shears);


        player.getActivePerk1().addItem();
        player.getActivePerk2().addItem();
    }
}
