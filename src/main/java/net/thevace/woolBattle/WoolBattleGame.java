package net.thevace.woolBattle;

import net.thevace.woolBattle.listener.WoolBattleGameListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class WoolBattleGame {

    private List<WoolbattlePlayer> Team1;
    private List<WoolbattlePlayer> Team2;

    private int Team1Health;
    private int Team2Health;

    private WoolBattleGameListener listener;

    private WoolBattlePlayerManager playerManager;



    public WoolBattleGame(int teamHealth, List<WoolbattlePlayer> Team1, List<WoolbattlePlayer> Team2, WoolBattlePlayerManager playerManager) {
        this.listener = new WoolBattleGameListener(this);

        this.Team1Health = teamHealth;
        this.Team2Health = teamHealth;
        this.Team1 = Team1;
        this.Team2 = Team2;
        this.playerManager = playerManager;
    }

    public void startGame() {
        Bukkit.getPluginManager().registerEvents(listener, Bukkit.getPluginManager().getPlugin("WoolBattle"));

        for (WoolbattlePlayer wbp : Team1) {
            Player p = wbp.getPlayer();
            p.sendMessage("Woolbattle game started!");
            p.getInventory().clear();
        }
        for (WoolbattlePlayer wbp : Team2) {
            Player p = wbp.getPlayer();
            p.sendMessage("Woolbattle game started!");
            p.getInventory().clear();
        }
    }

    public void endGame() {
        PlayerMoveEvent.getHandlerList().unregister(listener);
        if(Team1Health > Team2Health) {
            for (WoolbattlePlayer wbp : Team1) {
                Player p = wbp.getPlayer();
                p.sendMessage("Woolbattle game ended! Team 1 has won!");
            }
            for (WoolbattlePlayer wbp : Team2) {
                Player p = wbp.getPlayer();
                p.sendMessage("Woolbattle game ended! Team 1 has won!");
            }
        } else {
            for (WoolbattlePlayer wbp : Team1) {
                Player p = wbp.getPlayer();
                p.sendMessage("Woolbattle game ended! Team 1 has won!");
            }
            for (WoolbattlePlayer wbp : Team2) {
                Player p = wbp.getPlayer();
                p.sendMessage("Woolbattle game ended! Team 1 has won!");
            }
        }
    }

    public void handlePlayerHeight(Player player) {
        if (Team1.stream().anyMatch(wbp -> wbp.getPlayer().equals(player))) {
            if (Team1Health > 0) {
                Team1Health--;
                Bukkit.broadcastMessage("Team 1 hat ein Leben verloren: " + Team1Health);
            }
        } else if (Team2.stream().anyMatch(wbp -> wbp.getPlayer().equals(player))) {
            if (Team2Health > 0) {
                Team2Health--;
                Bukkit.broadcastMessage("Team 2 hat ein Leben verloren: " + Team2Health);
            }
        }


        player.teleport(player.getWorld().getSpawnLocation());

        if (Team1Health == 0 || Team2Health == 0) {
            endGame();
        }
    }

    public void handleWoolBreak(Player p) {
        WoolbattlePlayer player = playerManager.getWoolBattlePlayer(p);

        if (Team1.contains(player)) {
            player.addWool(1, Material.RED_WOOL);
        } else if (Team2.contains(player)) {
            player.addWool(1, Material.BLUE_WOOL);
        }

    }

}
