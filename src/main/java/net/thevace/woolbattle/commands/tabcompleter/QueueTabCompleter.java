package net.thevace.woolbattle.commands.tabcompleter;

import net.thevace.woolbattle.QueueManager;
import net.thevace.woolbattle.WoolBattleQueue;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QueueTabCompleter implements TabCompleter {

    private QueueManager queueManager;

    public QueueTabCompleter(QueueManager queueManager) {
        this.queueManager = queueManager;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        List<String> ids = new ArrayList<>();

        if(args.length == 1) {
            for (WoolBattleQueue queue : queueManager.getAllQueues()) {
                ids.add(queue.getId());
            }
        }
        return ids;
    }
}
