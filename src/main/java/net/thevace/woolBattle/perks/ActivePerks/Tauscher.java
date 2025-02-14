package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

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
        Location targetLoc = target.getPlayer().getLocation();

        player.getPlayer().teleport(targetLoc);
        target.teleport(playerLoc);
    }

}
