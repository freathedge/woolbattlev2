package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Pfeilregen extends PassivePerk {
    public Pfeilregen(WoolBattlePlayer p) {
        super(5, p, ChatColor.GOLD + "Pfeilregen", Material.ARROW, "Schieße alle 10 Pfeile 5 Pfeile auf einmal");
    }
}
