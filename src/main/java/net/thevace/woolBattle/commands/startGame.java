package net.thevace.woolBattle.commands;

import net.thevace.woolBattle.WoolBattleQueue;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class startGame implements CommandExecutor {

    WoolBattleQueue queue;

    public startGame(WoolBattleQueue queue) {
        this.queue = queue;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        queue.startQueue();
        Player p = (Player) commandSender;
        p.sendMessage(ChatColor.GOLD + "Starting WoolBattle game...");
        return false;
    }
}
