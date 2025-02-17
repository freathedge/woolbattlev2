package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolBattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.*;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Freeze extends ActivePerk implements Listener {

    WoolBattlePlayer target = null;

    public Freeze(WoolBattlePlayer p) {
        super(10, 16, p, ChatColor.GOLD + "Freeze", Material.ICE, "Friert den Spieler den du triffst ein");
        if(p != null) {
            Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("WoolBattle"));
        }
    }

    @Override
    protected void applyEffect() {
        target.setFreezed(true);

        Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("WoolBattle"), () -> {
            target.setFreezed(false);
        }, 200L);
    }

    public void throwSnowball() {
        Snowball snowball = player.getPlayer().launchProjectile(Snowball.class);
        Vector direction = player.getPlayer().getLocation().getDirection().multiply(1.5);
        snowball.setVelocity(direction);
        player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_SNOWBALL_THROW, 1.0f, 1.0f);
    }

    public void setTarget(WoolBattlePlayer target) {
        this.target = target;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().hasItemMeta() && event.getPlayer().equals(player.getPlayer())) {
            if (event.getItem().getItemMeta().getDisplayName().equals(itemName)) {
                throwSnowball();
                event.setCancelled(true);
            }
        }
    }
}
