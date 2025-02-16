package net.thevace.woolBattle.commands;

import net.thevace.woolBattle.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class getQueue implements CommandExecutor {

    QueueManager queueManager;
    WoolBattlePlayerManager playerManager;


    public getQueue(QueueManager queueManager, WoolBattlePlayerManager playerManager) {
        this.queueManager = queueManager;
        this.playerManager = playerManager;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player p = (Player) sender;

        if(args.length == 0) {
            Map<WoolbattlePlayer, WoolBattleQueue> playerQueues = queueManager.getAllQueues();
            System.out.println(queueManager.getAllQueues().size());
            for(WoolBattleQueue queue : playerQueues.values()) {
                p.sendMessage("Spieler in der Queue: ");
                p.sendMessage("Team 1:");
                for(WoolbattlePlayer player : queue.getTeam1()) {
                    p.sendMessage(player.getPlayer().getDisplayName());
                }
                p.sendMessage("Team 2:");
                for(WoolbattlePlayer player : queue.getTeam2()) {
                    p.sendMessage(player.getPlayer().getDisplayName());
                }
            }
            return true;
        } else if(args.length == 1) {
            p.sendMessage("Spieler in der Queue von " + args[0] + ": ");
            Player target = Bukkit.getPlayer(args[0]);
            WoolbattlePlayer player = playerManager.getWoolBattlePlayer(target);
            for(WoolbattlePlayer woolbattlePlayer : queueManager.getQueue(player).getQueue()) {
                p.sendMessage(woolbattlePlayer.getPlayer().getName());
            }
            p.sendMessage(String.valueOf(queueManager.getQueue(player).getQueue().size()));

            return true;
        }

        return false;
    }
}
