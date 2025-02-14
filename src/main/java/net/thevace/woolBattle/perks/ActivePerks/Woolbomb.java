package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Woolbomb extends ActivePerk {

    public Woolbomb(WoolbattlePlayer p) {
        super(15, 16, p, ChatColor.GOLD + "Woolbomb", Material.WHITE_WOOL, "Wirft ein TNT in die richtung die du schaust und gibt Spielern Rückstoß, die in der Nähe sind");
    }

    @Override
    protected void applyEffect() {

    }
}
