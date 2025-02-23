package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class TNTPfeil extends PassivePerk {
    public TNTPfeil(WoolBattlePlayer p) {
        super(4, p, ChatColor.GOLD + "TNT-Pfeil", Material.TNT, "Schießt alle 10 Pfeile einen explosiven Pfeil welcher allen Spielern in einem Radius von 2 Blöcken Rückstoß gibt");
    }

    @Override
    public void applyEffect() {

    }
}
