package net.thevace.woolBattle.commands;

import net.thevace.woolBattle.QueueManager;
import net.thevace.woolBattle.WoolBattlePlayerManager;
import net.thevace.woolBattle.WoolBattleQueue;
import net.thevace.woolBattle.WoolbattlePlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class startGame implements CommandExecutor {

    WoolBattlePlayerManager playerManager;
    QueueManager queueManager;

    public startGame(WoolBattlePlayerManager playerManager, QueueManager queueManager) {
        this.playerManager = playerManager;
        this.queueManager = queueManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        Player p = (Player) commandSender;
        WoolbattlePlayer woolbattlePlayer = playerManager.getWoolBattlePlayer(p);
        WoolBattleQueue queue = queueManager.getQueue(woolbattlePlayer);
        queue.startQueue();

        p.sendMessage(ChatColor.GOLD + "Starting WoolBattle game...");
        return false;
    }
}
