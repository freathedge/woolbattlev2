package net.thevace.woolBattle;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class WoolBattleQueue extends JavaPlugin {

    private List<Player> Queue;

    private List<Player> team1 = new ArrayList<Player>();
    private List<Player> team2 = new ArrayList<Player>();

    public WoolBattleQueue() {
        WoolBattleGame game = new WoolBattleGame(10, team1, team2);
    }

    public void joinQueue(Player player) {
        Queue.add(player);

    }

    public void leaveQueue(Player player) {
        Queue.remove(player);
    }
}
