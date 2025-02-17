package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolBattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Tauscher extends ActivePerk implements Listener {

    Player target;

    public Tauscher(WoolBattlePlayer p) {
        super(10, 16, p, ChatColor.GOLD + "Tauscher", Material.SNOWBALL, "Tausche den Platz mit dem Spieler den du triffst");
        if(p != null) {
            Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("WoolBattle"));
        }
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    @Override
    public void applyEffect() {
        Location playerLoc = player.getPlayer().getLocation();
        Location targetLoc = target.getLocation();

        player.getPlayer().teleport(targetLoc);
        target.teleport(playerLoc);
    }

    public void throwSnowball() {
        Snowball snowball = player.getPlayer().launchProjectile(Snowball.class);
        Vector direction = player.getPlayer().getLocation().getDirection().multiply(1.5);
        snowball.setVelocity(direction);
        player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_SNOWBALL_THROW, 1.0f, 1.0f);
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
