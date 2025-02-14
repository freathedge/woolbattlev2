package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Tuermchenbauer extends ActivePerk {
    public Tuermchenbauer(WoolbattlePlayer p) {
        super(15, 20, p, ChatColor.GOLD + "Türmchenbauer", Material.LADDER, "Erschafft einen Vertikalen Turm unter dir");
    }

    @Override
    protected void applyEffect() {

    }
}
