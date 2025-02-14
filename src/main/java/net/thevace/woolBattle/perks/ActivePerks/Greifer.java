package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import net.thevace.woolBattle.perks.Perk;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Greifer extends ActivePerk {
    public Greifer(WoolbattlePlayer p) {
        super(15, 20, p, ChatColor.GOLD + "Greifer", Material.STICK, "Zieht den anvisierten Gegner zu dir");
    }

    @Override
    protected void applyEffect() {

    }
}
