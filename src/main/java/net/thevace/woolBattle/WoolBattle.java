package net.thevace.woolBattle;

import me.devnatan.inventoryframework.ViewFrame;
import net.thevace.woolBattle.commands.*;
import net.thevace.woolBattle.inventorys.ActivePerk1Selector;
import net.thevace.woolBattle.inventorys.ActivePerk2Selector;
import net.thevace.woolBattle.inventorys.TeamSelect;
import net.thevace.woolBattle.listener.PlayerInteraction;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class WoolBattle extends JavaPlugin {

    @Override
    public void onEnable() {



        WoolBattlePlayerManager playerManager = new WoolBattlePlayerManager();
        PerkManager perkManager = new PerkManager();
        QueueManager queueManager = new QueueManager(playerManager, perkManager);

        ViewFrame viewFrame = ViewFrame.create(this)
                .with(new TeamSelect(playerManager, queueManager))
                .with(new ActivePerk1Selector(playerManager, perkManager))
                .with(new ActivePerk2Selector(playerManager, perkManager))
                .register();

        this.getCommand("joinQueue").setExecutor(new joinQueue(playerManager, queueManager, viewFrame));
        this.getCommand("startGame").setExecutor(new startGame(playerManager, queueManager));
        this.getCommand("getQueue").setExecutor(new getQueue(queueManager, playerManager));
        this.getCommand("getPlayerWool").setExecutor(new getPlayerWool(playerManager));
        Bukkit.getPluginManager().registerEvents(new PlayerInteraction(viewFrame, playerManager, queueManager), this);


    }

    @Override
    public void onDisable() {

    }
}
