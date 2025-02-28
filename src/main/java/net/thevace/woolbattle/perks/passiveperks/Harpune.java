package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Harpune extends PassivePerk {

    public Harpune(WoolBattlePlayer p) {
        super(15, 4, p, ChatColor.GOLD + "Harpune", Material.SPECTRAL_ARROW, "Zieht den getroffenen Spieler zum Spieler, welcher geschossen hat");
    }
}
