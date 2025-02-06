package net.thevace.woolBattle;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewFrame;
import net.thevace.woolBattle.commands.joinQueue;
import net.thevace.woolBattle.commands.startGame;
import net.thevace.woolBattle.inventorys.TeamSelect;
import net.thevace.woolBattle.listener.PlayerInteraction;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class WoolBattle extends JavaPlugin {

    @Override
    public void onEnable() {



        WoolBattleQueue queue = new WoolBattleQueue(1, this);
        ViewFrame viewFrame = ViewFrame.create(this)
                .with(new TeamSelect(queue))
                .register();

        this.getCommand("joinQueue").setExecutor(new joinQueue(queue, viewFrame));
        this.getCommand("startGame").setExecutor(new startGame(queue));
        Bukkit.getPluginManager().registerEvents(new PlayerInteraction(viewFrame), this);


    }

    @Override
    public void onDisable() {

    }
}
