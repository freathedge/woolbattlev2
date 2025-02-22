package net.thevace.woolbattle.perks.activeperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.ActivePerk;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class Enterhaken extends ActivePerk implements Listener {

    Block targetBlock;

    PlayerFishEvent event;

    public Enterhaken(WoolBattlePlayer p) {
        super(12, 10, p, ChatColor.GOLD + "Enterhaken", Material.FISHING_ROD, "Ziehe dich dorthin wo die Angel trifft");
        if(p != null) {
            Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("WoolBattle"));
        }
    }

    public void setEvent(PlayerFishEvent event) {
        this.event = event;
    }

    @Override
    protected void applyEffect() {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        Location hookLocation = event.getHook().getLocation();
        Location change = hookLocation.subtract(location);
        player.setVelocity(change.toVector().multiply(0.3));
        player.setFallDistance(0);
    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        setEvent(event);

        Player player = event.getPlayer();
        ItemStack fishingItem = player.getInventory().getItemInMainHand();
        if(fishingItem.getItemMeta().getDisplayName().equals(itemName)) {
            if(event.getState().equals(PlayerFishEvent.State.REEL_IN) || event.getState().equals(PlayerFishEvent.State.IN_GROUND )) {
                activate();
            }
        }
    }
}
