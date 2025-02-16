package net.thevace.woolBattle.commands;

import net.thevace.woolBattle.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
            for(WoolBattleQueue queue : queueManager.getAllQueues()) {
                p.sendMessage("Queue - " + ChatColor.GOLD + queue.getId());
            }
            return true;
        } else if(args.length == 1) {
            p.sendMessage("Spieler in der Queue " + ChatColor.GOLD + args[0] + ChatColor.RESET + ": ");

            for(WoolBattlePlayer woolbattlePlayer : queueManager.getQueue(args[0]).getQueue()) {
                p.sendMessage(woolbattlePlayer.getPlayer().getName());
            }

            return true;
        }

        return false;
    }
}
