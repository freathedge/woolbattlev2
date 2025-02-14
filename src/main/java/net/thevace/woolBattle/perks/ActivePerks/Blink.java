package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

public class Blink extends ActivePerk {


    public Blink(WoolbattlePlayer p) {
        super(15, 30, p, ChatColor.GOLD + "Blink", Material.ENDER_EYE);
    }

    @Override
    protected void applyEffect() {
        Location currentLocation = player.getPlayer().getLocation();
        Vector direction = currentLocation.getDirection().normalize().multiply(15);
        Location newLocation = currentLocation.add(direction);

        player.getPlayer().teleport(newLocation);
    }
}
