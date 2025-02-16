package net.thevace.woolBattle.commands;

import net.thevace.woolBattle.QueueManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class createQueue implements CommandExecutor {

    QueueManager queueManager;

    public createQueue(QueueManager queueManager) {
        this.queueManager = queueManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        Player p = (Player) commandSender;

        if(args.length == 0) {
            queueManager.createQueue(p, 1);
            return true;
        } else if(args.length == 1) {
            queueManager.createQueue(p, Integer.parseInt(args[0]));
            return true;
        }

        p.sendMessage(ChatColor.RED + "Usage: /createQueue <team size>");
        return false;
    }
}
