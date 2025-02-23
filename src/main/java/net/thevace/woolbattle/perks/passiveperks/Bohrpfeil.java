package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Bohrpfeil extends PassivePerk {

    public Bohrpfeil(WoolBattlePlayer p) {
        super(p, ChatColor.GOLD + "Bohrpfeil", Material.DIAMOND_PICKAXE, "Zerstöre leichter Wollblöcke mit Pfeilen");
    }

    @Override
    public void applyEffect() {

    }
}
