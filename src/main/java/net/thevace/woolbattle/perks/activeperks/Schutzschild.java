package net.thevace.woolbattle.perks.activeperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.ActivePerk;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Schutzschild extends ActivePerk implements Listener {
    public Schutzschild(WoolBattlePlayer p) {
        super(15, 32, p, ChatColor.GOLD + "Schutzschild", Material.BEACON, "Macht dich für kurze Zeit immun gegen Pfeiltreffer");
        if(p != null) {
            Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("WoolBattle"));
        }
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

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().hasItemMeta() && event.getPlayer().equals(player.getPlayer())) {
            if (event.getItem().getItemMeta().getDisplayName().equals(itemName)) {
                activate();
                event.setCancelled(true);
            }
        }
    }
}
