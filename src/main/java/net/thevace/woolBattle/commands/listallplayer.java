package net.thevace.woolBattle.commands;

import net.thevace.woolBattle.WoolBattlePlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class listallplayer implements CommandExecutor {

    WoolBattlePlayerManager playerManager;

    public listallplayer(WoolBattlePlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        playerManager.listAllPlayers();
        return true;
    }
}
