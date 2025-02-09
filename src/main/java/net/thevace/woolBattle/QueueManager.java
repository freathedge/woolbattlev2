package net.thevace.woolBattle;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueueManager {
    private static final Map<WoolbattlePlayer, WoolBattleQueue> playerQueues = new HashMap<>();
    private static final List<WoolBattleQueue> allQueues = new ArrayList<>();


    public void addToQueue(WoolbattlePlayer player, WoolBattleQueue queue) {
        playerQueues.put(player, queue);
        queue.joinQueue(player);
    }

    public void removeFromQueue(WoolbattlePlayer player) {
        WoolBattleQueue queue = getQueue(player);
        if (queue != null) {
            queue.leaveQueue(player);
        }
        playerQueues.remove(player);
    }

    public WoolBattleQueue getQueue(WoolbattlePlayer player) {
        return playerQueues.get(player);
    }

    public Map<WoolbattlePlayer, WoolBattleQueue> getAllQueues() {
        return playerQueues;
    }

    public void joinAvailableQueue(WoolbattlePlayer player, int teamSize) {
        for (WoolBattleQueue queue : allQueues) {
            if (queue.getTotalPlayers() < queue.getTeamSize()) {
                addToQueue(player, queue);
                return;
            }
        }

        WoolBattleQueue newQueue = new WoolBattleQueue(teamSize);
        allQueues.add(newQueue);
        addToQueue(player, newQueue);

    }
}