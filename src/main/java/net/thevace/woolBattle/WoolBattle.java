package net.thevace.woolBattle;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewFrame;
import net.thevace.woolBattle.commands.getQueue;
import net.thevace.woolBattle.commands.joinQueue;
import net.thevace.woolBattle.commands.startGame;
import net.thevace.woolBattle.inventorys.TeamSelect;
import net.thevace.woolBattle.listener.PlayerInteraction;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class WoolBattle extends JavaPlugin {

    @Override
    public void onEnable() {



        WoolBattlePlayerManager playerManager = new WoolBattlePlayerManager();
        QueueManager queueManager = new QueueManager(playerManager, this);
        ViewFrame viewFrame = ViewFrame.create(this)
                .with(new TeamSelect(playerManager, queueManager))
                .register();

        this.getCommand("joinQueue").setExecutor(new joinQueue(playerManager, queueManager, viewFrame));
        this.getCommand("startGame").setExecutor(new startGame(playerManager, queueManager));
        this.getCommand("getQueue").setExecutor(new getQueue(queueManager, playerManager));
        Bukkit.getPluginManager().registerEvents(new PlayerInteraction(viewFrame, playerManager, queueManager), this);


    }

    @Override
    public void onDisable() {

    }
}
