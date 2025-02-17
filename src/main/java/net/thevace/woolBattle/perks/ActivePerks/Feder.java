package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolBattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Feder extends ActivePerk implements Listener {
    public Feder(WoolBattlePlayer p) {
        super(15, 20, p, ChatColor.GOLD + "Feder", Material.FEATHER, "Wirft dich in die Richtung die du schaust");
        if(p != null) {
            Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("WoolBattle"));
        }
    }

    @Override
    protected void applyEffect() {
        Vector direction = player.getPlayer().getLocation().getDirection().normalize();
        Vector velocity = direction.multiply(3);
        player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_BREEZE_WIND_BURST, 1.0f, 1.0f);

        player.getPlayer().setVelocity(velocity);
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
