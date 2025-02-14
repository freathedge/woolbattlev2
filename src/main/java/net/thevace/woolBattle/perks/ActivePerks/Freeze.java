package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import net.thevace.woolBattle.perks.Perk;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Freeze extends ActivePerk {
    public Freeze(WoolbattlePlayer p) {
        super(10, 16, p, ChatColor.GOLD + "Freeze", Material.ICE, "Friert den Spieler den du triffst ein");
    }

    @Override
    protected void applyEffect() {

    }
}
