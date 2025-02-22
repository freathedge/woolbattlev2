package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class FreezeArrow extends PassivePerk {

    public FreezeArrow(WoolBattlePlayer p) {
        super(4, p, ChatColor.GOLD + "FreezeArrow", Material.BLUE_ICE, "Schießt alle 10 Pfeile einen Eis Pfeil, welcher den Spieler temporär einfrieren lässt");
    }
}
