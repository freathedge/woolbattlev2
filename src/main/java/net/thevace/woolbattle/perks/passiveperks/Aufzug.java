package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Aufzug extends PassivePerk {

    public Aufzug(WoolBattlePlayer p) {
        super(15, 1, p, ChatColor.GOLD + "Aufzug", Material.PISTON, "Teleportiert dich auf den höchten Block über dir wenn du shiftest");
    }
}
