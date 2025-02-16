package net.thevace.woolBattle;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class QueueManager {
    private static final Map<WoolBattlePlayer, WoolBattleQueue> playerQueues = new HashMap<>();
    private static final List<WoolBattleQueue> allQueues = new ArrayList<>();

    private WoolBattlePlayerManager playerManager;


    public QueueManager(WoolBattlePlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public void addToQueue(WoolBattlePlayer player, WoolBattleQueue queue) {
        playerQueues.put(player, queue);
        queue.joinQueue(player);
    }

    public void removeFromQueue(WoolBattlePlayer player) {
        WoolBattleQueue queue = getQueue(player);
        if (queue != null) {
            queue.leaveQueue(player);
        }
        playerQueues.remove(player);
    }

    public WoolBattleQueue getQueue(WoolBattlePlayer player) {
        return playerQueues.get(player);
    }

    public WoolBattleQueue getQueue(String id) {
        for(WoolBattleQueue queue : allQueues) {
            if(queue.getId().equals(id)) {
                return queue;
            }
        }
        return null;
    }

    public List<WoolBattleQueue> getAllQueues() {
        return allQueues;
    }

    public void listAllQueues() {
        for (WoolBattleQueue queue : allQueues) {
            for (WoolBattlePlayer player : queue.getQueue()) {
                System.out.println("Queue: " + queue.getQueue() + " Player: " + player);
            }
        }
    }

//    public void joinAvailableQueue(WoolbattlePlayer player, int teamSize) {
//        if (playerQueues.containsKey(player)) {
//            player.getPlayer().sendMessage("Du bist bereits in einer Warteschlange.");
//            return;
//        }
//
//        // Nach existierender Warteschlange mit demselben TeamSize suchen
//        for (WoolBattleQueue queue : allQueues) {
//            if (queue.getTeamSize() == teamSize && queue.getTotalPlayers() < queue.getTeamSize() * 2) {
//                addToQueue(player, queue);
//                return;
//            }
//        }
//
//        // Falls keine passende Warteschlange existiert, erstelle eine neue
//        WoolBattleQueue newQueue = new WoolBattleQueue(teamSize, playerManager, this);
//        allQueues.add(newQueue);
//        addToQueue(player, newQueue);
//    }

    public void joinQueue(WoolBattlePlayer player, String ID) {
//        for (WoolBattleQueue queue : allQueues) {
//            if (queue.getId().equals(ID)) {
//                addToQueue(player, queue);
//                return;
//            }
//        }
        WoolBattleQueue queue = getQueue(ID);
        if (queue != null) {
            addToQueue(player, queue);
        } else {
            player.getPlayer().sendMessage(ChatColor.RED + "Es wurde kein Queue gefunden");
        }
    }

    public void createQueue(Player p, int teamSize) {
        WoolBattleQueue newQueue = new WoolBattleQueue(teamSize, playerManager, this);
        allQueues.add(newQueue);
        p.sendMessage(ChatColor.GREEN + "Neue Queue erstellt: " + newQueue.getId());

    }



    public void removeQueue(WoolBattleQueue queue) {
        allQueues.remove(queue);
        playerQueues.entrySet().removeIf(entry -> entry.getValue() == queue);
        for(WoolBattlePlayer player : playerQueues.keySet()) {
            System.out.println("Queue: " + queue.getQueue() + " Player: " + player.getPlayer().getName());
        }
    }
}