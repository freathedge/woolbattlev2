package net.thevace.woolBattle;

import net.thevace.woolBattle.listener.WoolBattleGameListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class WoolBattleGame implements Listener {

    private List<WoolbattlePlayer> Team1;
    private List<WoolbattlePlayer> Team2;

    private int Team1Health;
    private int Team2Health;

    private WoolBattleGameListener listener;
    private Plugin plugin;


    public WoolBattleGame(int teamHealth, List<WoolbattlePlayer> Team1, List<WoolbattlePlayer> Team2, Plugin plugin) {
        this.listener = new WoolBattleGameListener(this, plugin);


        this.Team1Health = teamHealth;
        this.Team2Health = teamHealth;
        this.Team1 = Team1;
        this.Team2 = Team2;
        this.plugin = plugin;
    }

    public void startGame() {
        Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("WoolBattle"));

        for (WoolbattlePlayer wbp : Team1) {
            Player p = wbp.getPlayer();
            p.sendMessage("Woolbattle game started!");
        }
    }

    public void endGame() {
        PlayerMoveEvent.getHandlerList().unregister(this); // Listener deregistrieren
        if(Team1Health > Team2Health) {
            System.out.println("Team 1 has won!");
        } else {
            System.out.println("Team 2 has won!");
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

}
