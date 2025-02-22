package net.thevace.woolbattle.commands;

import net.thevace.woolbattle.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class getQueue implements CommandExecutor {

    QueueManager queueManager;


    public getQueue(QueueManager queueManager) {
        this.queueManager = queueManager;
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
