package net.thevace.woolbattle.commands;

import me.devnatan.inventoryframework.ViewFrame;
import net.thevace.woolbattle.QueueManager;
import net.thevace.woolbattle.WoolBattlePlayerManager;
import net.thevace.woolbattle.WoolBattlePlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class joinQueue implements CommandExecutor  {
    WoolBattlePlayerManager playerManager;
    QueueManager queueManager;
    ViewFrame viewFrame;

    public joinQueue(WoolBattlePlayerManager playerManager, QueueManager queueManager, ViewFrame viewFrame) {
        this.playerManager = playerManager;
        this.queueManager = queueManager;
        this.viewFrame = viewFrame;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        Player p = (Player) commandSender;

        WoolBattlePlayer player;

        if(WoolBattlePlayerManager.isRegistered(p)) {
            player = WoolBattlePlayerManager.getWoolBattlePlayer(p);
        } else {
            WoolBattlePlayerManager.registerPlayer(p);
            player = WoolBattlePlayerManager.getWoolBattlePlayer(p);
        }

        if (args.length == 0) {
            p.sendMessage(ChatColor.RED + "Usage: /joinqueue <ID>");
        } else if (args.length == 1) {
            if(queueManager.getQueue(player) != null) {
                if(queueManager.getQueue(player).getId().equals(args[0])) {
                    p.sendMessage(ChatColor.RED + "Du bist schon in dieser Queue");
                    return false;
                } else {
                    queueManager.getQueue(player).leaveQueue(player);
                }
            }
            queueManager.joinQueue(player, args[0]);
            return true;
        }

        return false;
    }

}
