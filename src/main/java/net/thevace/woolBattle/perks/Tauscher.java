package net.thevace.woolBattle.perks;

import net.thevace.woolBattle.WoolbattlePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Tauscher extends ActivePerk {

    public Tauscher(WoolbattlePlayer p) {
        super(10, 16, p, ChatColor.GOLD + "Tauscher", Material.SNOWBALL);
    }

    public void activate(Player target) {
        Location playerLoc = player.getPlayer().getLocation();
        Location targetLoc = target.getPlayer().getLocation();

        player.getPlayer().teleport(targetLoc);
        target.teleport(playerLoc);
    }

}
