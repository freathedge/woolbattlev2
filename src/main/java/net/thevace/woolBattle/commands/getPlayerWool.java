package net.thevace.woolBattle.commands;

import net.thevace.woolBattle.WoolBattlePlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class getPlayerWool implements CommandExecutor {

    WoolBattlePlayerManager playerManager;

    public getPlayerWool(WoolBattlePlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        p.sendMessage(ChatColor.GOLD + "Your current wool: " + playerManager.getWoolBattlePlayer(p).getWool());
        return true;
    }
}
