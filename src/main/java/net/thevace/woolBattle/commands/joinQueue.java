package net.thevace.woolBattle.commands;

import me.devnatan.inventoryframework.ViewFrame;
import net.thevace.woolBattle.WoolBattleQueue;
import net.thevace.woolBattle.inventorys.TeamSelect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class joinQueue implements CommandExecutor  {
    WoolBattleQueue queue;
    ViewFrame viewFrame;

    public joinQueue(WoolBattleQueue queue, ViewFrame viewFrame) {
        this.queue = queue;
        this.viewFrame = viewFrame;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        Player p = (Player) commandSender;
        queue.joinQueue(p);
        p.sendMessage("You have joined the queue.");
        viewFrame.open(TeamSelect.class, p);
        return false;
    }
}
