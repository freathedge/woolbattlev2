package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolBattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Enderperle extends ActivePerk {
    public Enderperle(WoolBattlePlayer p) {
        super(5, 0, p, ChatColor.GREEN + "Enderperle", Material.ENDER_PEARL, "Enderperle halt");
    }

    @Override
    protected void applyEffect() {

    }
}
