package net.thevace.woolbattle.perks.activeperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.ActivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EnderPearl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Enderperle extends ActivePerk implements Listener {
    public Enderperle(WoolBattlePlayer p) {
        super(5, 0, p, ChatColor.GREEN + "Enderperle", Material.ENDER_PEARL, "Enderperle halt");

    }

    @Override
    protected void applyEffect() {
        EnderPearl enderpearl = player.getPlayer().launchProjectile(EnderPearl.class);
        Vector direction = player.getPlayer().getLocation().getDirection().multiply(1.5);
        enderpearl.setVelocity(direction);
        player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_ENDER_PEARL_THROW, 1.0f, 1.0f);
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
