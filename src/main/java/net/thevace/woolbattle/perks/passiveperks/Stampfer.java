package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Stampfer extends PassivePerk {
    public Stampfer(WoolBattlePlayer p) {
        super(4, p, ChatColor.GOLD + "Stampfer", Material.DIAMOND_BOOTS, "Shifte nach einem Doppelsprung um eine Stampfattacke auszuführen undf Genern in einem Radius von einem Block Rückstoß zu geben");
    }

    @Override
    public void applyEffect() {

    }
}
