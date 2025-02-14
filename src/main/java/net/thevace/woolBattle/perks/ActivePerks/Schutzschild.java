package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

public class Schutzschild extends ActivePerk {
    public Schutzschild(WoolbattlePlayer p) {
        super(15, 32, p, ChatColor.GOLD + "Schutzschild", Material.BEACON, "Macht dich für kurze Zeit immun gegen Pfeiltreffer");
    }

    @Override
    protected void applyEffect() {
        player.setProtected(true);
        new BukkitRunnable() {
            @Override
            public void run() {
                player.setProtected(false);
            }
        }.runTaskLater(Bukkit.getPluginManager().getPlugin("WoolBattle"), 5 * 20L);
    }
}
