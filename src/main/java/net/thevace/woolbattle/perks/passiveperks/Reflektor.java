package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Reflektor extends PassivePerk {
    public Reflektor(WoolBattlePlayer p) {
        super(8, p, ChatColor.GOLD + "Reflektor", Material.SLIME_BALL, "Wenn du getroffen wird, gibt es eine 5 Prozentige Chance, dass statt dir der Gegner Rückstoß erhält");
    }
}
