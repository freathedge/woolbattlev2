package net.thevace.woolbattle.commands;

import net.thevace.woolbattle.WoolBattlePlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class listallplayer implements CommandExecutor {



    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        WoolBattlePlayerManager.listAllPlayers();
        return true;
    }
}
