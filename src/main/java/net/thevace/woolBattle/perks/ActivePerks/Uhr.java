package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Uhr extends ActivePerk {
    public Uhr(WoolbattlePlayer p) {
        super(20, 30, p, ChatColor.GOLD + "Uhr", Material.CLOCK, "Teleportiere dich zu dem letzten Block auf dem du gestanden bist");
    }

    @Override
    protected void applyEffect() {

    }
}
