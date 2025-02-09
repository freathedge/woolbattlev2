package net.thevace.woolBattle.commands;

import me.devnatan.inventoryframework.ViewFrame;
import net.thevace.woolBattle.QueueManager;
import net.thevace.woolBattle.WoolBattlePlayerManager;
import net.thevace.woolBattle.WoolBattleQueue;
import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.inventorys.TeamSelect;
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
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        Player p = (Player) commandSender;
        playerManager.registerPlayer(p);
        WoolbattlePlayer woolbattlePlayer = playerManager.getWoolBattlePlayer(p);
        queueManager.joinAvailableQueue(woolbattlePlayer, 1);
        return false;
    }
}
