package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Minigun extends ActivePerk {

    public Minigun(WoolbattlePlayer p) {
        super(20, 1, p, ChatColor.GOLD + "Minigun", Material.BOW, "Schießt eine große Menge an Pfeilen in die Richtung die du schaust");
    }

    @Override
    protected void applyEffect() {

    }
}
