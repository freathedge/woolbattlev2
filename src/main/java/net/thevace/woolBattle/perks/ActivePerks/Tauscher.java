package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;

public class Tauscher extends ActivePerk {

    Player target;

    public Tauscher(WoolbattlePlayer p) {
        super(10, 16, p, ChatColor.GOLD + "Tauscher", Material.SNOWBALL, "Tausche den Platz mit dem Spieler den du triffst");
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

}
