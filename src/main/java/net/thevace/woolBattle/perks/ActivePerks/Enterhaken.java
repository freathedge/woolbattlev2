package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;

public class Enterhaken extends ActivePerk {

    Block targetBlock;

    PlayerFishEvent event;

    public Enterhaken(WoolbattlePlayer p) {
        super(12, 10, p, ChatColor.GOLD + "Enterhaken", Material.FISHING_ROD);
    }

    public void setEvent(PlayerFishEvent event) {
        this.event = event;
    }

    @Override
    protected void applyEffect() {
        Player player = event.getPlayer();
        if(event.getState().equals(PlayerFishEvent.State.REEL_IN)) {
            Location location = player.getLocation();
            Location hookLocation = event.getHook().getLocation();
            Location change = hookLocation.subtract(location);
            player.setVelocity(change.toVector().multiply(0.3));
        }
    }
}
