package net.thevace.woolbattle.perks.activeperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.ActivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.Timestamp;

public class Enterhaken extends ActivePerk implements Listener {


    private PlayerFishEvent event;

    public Enterhaken(WoolBattlePlayer p) {
        super(12, 10, p, ChatColor.GOLD + "Enterhaken", Material.FISHING_ROD, "Ziehe dich dorthin wo die Angel trifft");
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

        this.event = event;


        Player p = event.getPlayer();
        if(p.equals(player.getPlayer())) {
            ItemStack fishingItem = p.getInventory().getItemInMainHand();
            if(fishingItem.getItemMeta().getDisplayName().equals(itemName)) {
                if(event.getState().equals(PlayerFishEvent.State.FISHING)) {
                    Timestamp lastUsed = null;
                    if(player.getActivePerk1().equals(this)) {
                        lastUsed = player.getActivePerk1LastUsed();
                    } else if(player.getActivePerk2().equals(this)) {
                        lastUsed = player.getActivePerk2LastUsed();
                    }
                    if(!canUsePerk(lastUsed) || !hasEnoughMoney()) {
                        event.setCancelled(true);
                    }
                } else if (event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH) || event.getState().equals(PlayerFishEvent.State.CAUGHT_ENTITY )) {
                    event.setCancelled(true);
                    event.getHook().remove();
                } else if (event.getState().equals(PlayerFishEvent.State.REEL_IN) || event.getState().equals(PlayerFishEvent.State.IN_GROUND)) {
                    activate();
                }
            }
        }
    }
}
