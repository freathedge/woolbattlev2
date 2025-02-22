package net.thevace.woolbattle.commands;

import net.thevace.woolbattle.WoolBattlePlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class getPlayerWool implements CommandExecutor {


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        p.sendMessage(ChatColor.GOLD + "Your current wool: " + WoolBattlePlayerManager.getWoolBattlePlayer(p).getWool());
        return true;
    }
}
