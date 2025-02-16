package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolBattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

public class Blink extends ActivePerk {


    public Blink(WoolBattlePlayer p) {
        super(15, 30, p, ChatColor.GOLD + "Blink", Material.ENDER_EYE, "Teleportiert dich 15 Blöcke in die Richtung die du schaust");
    }

    @Override
    protected void applyEffect() {
        Location currentLocation = player.getPlayer().getLocation();
        Vector direction = currentLocation.getDirection().normalize().multiply(15);
        Location newLocation = currentLocation.add(direction);

        player.getPlayer().teleport(newLocation);
    }
}
