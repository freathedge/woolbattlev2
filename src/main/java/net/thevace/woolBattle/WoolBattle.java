package net.thevace.woolBattle;

import org.bukkit.plugin.java.JavaPlugin;

public final class WoolBattle extends JavaPlugin {

    @Override
    public void onEnable() {
        WoolBattleQueue queue = new WoolBattleQueue();
    }

    @Override
    public void onDisable() {
    }
}
