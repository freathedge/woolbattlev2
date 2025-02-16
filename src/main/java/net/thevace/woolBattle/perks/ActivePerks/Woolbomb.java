package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolBattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.util.Vector;

public class Woolbomb extends ActivePerk {

    public Woolbomb(WoolBattlePlayer p) {
        super(15, 16, p, ChatColor.GOLD + "Woolbomb", Material.WHITE_WOOL, "Wirft ein TNT in die richtung die du schaust und gibt Spielern Rückstoß, die in der Nähe sind");
    }

    @Override
    protected void applyEffect() {
        Vector direction = player.getPlayer().getLocation().getDirection();

        Location location = player.getPlayer().getLocation().add(0, 1.5, 0);

        Location tntLocation = location.add(direction.multiply(2)); // TNT wird 2 Blöcke vor dem Spieler geworfen

        TNTPrimed tnt = player.getPlayer().getWorld().spawn(location, TNTPrimed.class);
        tnt.setVelocity(direction.multiply(1.5));
    }
}
