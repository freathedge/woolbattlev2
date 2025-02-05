package net.thevace.woolBattle;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class WoolBattleGame {

    private List<Player> Team1;
    private List<Player> Team2;

    private int Team1Health;
    private int Team2Health;


    public WoolBattleGame(int playerHealth, List<Player> Team1, List<Player> Team2) {
        this.Team1Health = playerHealth;
        this.Team2Health = playerHealth;
        this.Team1 = Team1;
        this.Team2 = Team2;
    }

    public void startGame() {
        checkPlayerHeigth();
        for (Player p : Team1) {

        }
    }

    public void endGame() {
        if(Team1Health > Team2Health) {
            System.out.println("Team 1 has won!");
        } else {
            System.out.println("Team 2 has won!");
        }
    }

    private void checkPlayerHeigth() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Team1) {
                    if (player.getLocation().getY() < 0) {
                        player.teleport(player.getWorld().getSpawnLocation());
                        if(Team1Health != 0) {
                            Team1Health --;
                        } else {
                            endGame();
                        }

                    }
                }
                for (Player player : Team2) {
                    if (player.getLocation().getY() < 0) {
                        player.teleport(player.getWorld().getSpawnLocation());
                        if(Team2Health != 0) {
                            Team2Health --;
                        } else {
                            endGame();
                        }
                    }
                }
            }
        }.runTaskTimer((Plugin) this, 0L, 20L);
    }


}
