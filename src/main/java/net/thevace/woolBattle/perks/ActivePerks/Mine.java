package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Mine extends ActivePerk {



    Location plateLocation;


    public Mine(WoolbattlePlayer p) {
        super(30, 16, p, ChatColor.GOLD + "Mine", Material.STONE_PRESSURE_PLATE);
    }

    @Override
    protected void applyEffect() {
        for (Player player : Bukkit.getOnlinePlayers()) {

            Location playerLoc = player.getLocation();
            double distance = playerLoc.distance(plateLocation);

            if (distance <= 5) {
                Vector knockback = playerLoc.toVector().subtract(plateLocation.toVector()).normalize();
                knockback.multiply(1.5);
                knockback.setY(0.5);
                player.setVelocity(knockback);
                player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);            }
        }
    }

    public void setPlateLocation(Location plateLocation) {
        this.plateLocation = plateLocation;
    }
}
