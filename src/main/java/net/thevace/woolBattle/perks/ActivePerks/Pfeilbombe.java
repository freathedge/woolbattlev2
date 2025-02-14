package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Pfeilbombe extends ActivePerk {

    public Pfeilbombe(WoolbattlePlayer p) {
        super(15, 10, p, ChatColor.GOLD + "Pfeilbombe", Material.FIREWORK_STAR, "Schießt Pfeile in jede Richtung und kann Feind als auch Freund treffen");
    }

    @Override
    protected void applyEffect() {

    }
}
