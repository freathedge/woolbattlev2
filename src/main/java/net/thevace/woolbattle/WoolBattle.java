package net.thevace.woolbattle;

import me.devnatan.inventoryframework.ViewFrame;
import net.thevace.woolbattle.commands.*;
import net.thevace.woolbattle.commands.tabcompleter.QueueTabCompleter;
import net.thevace.woolbattle.inventorys.*;
import net.thevace.woolbattle.listener.PlayerInteraction;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public final class WoolBattle extends JavaPlugin {

    @Override
    public void onEnable() {

        WoolBattlePlayerManager playerManager = new WoolBattlePlayerManager();
        PerkManager perkManager = new PerkManager();
        QueueManager queueManager = new QueueManager();
        new PerkListenerManager(this);

        ViewFrame viewFrame = ViewFrame.create(this)
                .with(new TeamSelect(playerManager, queueManager))
                .with(new ActivePerk1Selector(perkManager))
                .with(new ActivePerk2Selector(perkManager))
                .with(new PassivePerkSelector(perkManager))
                .with(new PerkSelector())
                .with(new Voting())
                .with(new LebenVoting(queueManager))
                .with(new MapVoting())
                .register();

        this.getCommand("joinQueue").setExecutor(new joinQueue(playerManager, queueManager, viewFrame));
        this.getCommand("startGame").setExecutor(new startGame(playerManager, queueManager));
        this.getCommand("getQueue").setExecutor(new getQueue(queueManager));
        this.getCommand("getPlayerWool").setExecutor(new getPlayerWool());
        this.getCommand("createQueue").setExecutor(new createQueue(queueManager));
        this.getCommand("listallplayers").setExecutor(new listallplayer());
        this.getCommand("getAllListeners").setExecutor(new getAllListeners());

        this.getCommand("getQueue").setTabCompleter(new QueueTabCompleter(queueManager));
        this.getCommand("joinQueue").setTabCompleter(new QueueTabCompleter(queueManager));

        Bukkit.getPluginManager().registerEvents(new PlayerInteraction(viewFrame, queueManager), this);

        for (World world : Bukkit.getWorlds()) {
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            world.setGameRule(GameRule.DISABLE_RAIDS, true);
            world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            world.setDifficulty(Difficulty.PEACEFUL);
        }


    }

    @Override
    public void onDisable() {

    }
}
