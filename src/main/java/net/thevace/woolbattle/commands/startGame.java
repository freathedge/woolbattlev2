package net.thevace.woolbattle.commands;

import net.thevace.woolbattle.QueueManager;
import net.thevace.woolbattle.WoolBattlePlayerManager;
import net.thevace.woolbattle.WoolBattleQueue;
import net.thevace.woolbattle.WoolBattlePlayer;
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
        WoolBattlePlayer woolbattlePlayer = playerManager.getWoolBattlePlayer(p);
        WoolBattleQueue queue = queueManager.getQueue(woolbattlePlayer);
        queue.startGame();

        p.sendMessage(ChatColor.GOLD + "Starting WoolBattle game...");
        return false;
    }
}
