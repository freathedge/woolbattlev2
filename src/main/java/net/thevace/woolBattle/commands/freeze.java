package net.thevace.woolBattle.commands;

import net.thevace.woolBattle.WoolBattlePlayerManager;
import net.thevace.woolBattle.WoolbattlePlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class freeze implements CommandExecutor {

    WoolBattlePlayerManager playerManager;

    public freeze(WoolBattlePlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        WoolbattlePlayer player = playerManager.getWoolBattlePlayer(p);
        player.setFreezed(true);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("WoolBattle"), new Runnable() {
            public void run() {
                if(player.isFreezed()) {
                    player.setFreezed(false);
                }
            }
        }, 200L);
        return true;

    }
}
