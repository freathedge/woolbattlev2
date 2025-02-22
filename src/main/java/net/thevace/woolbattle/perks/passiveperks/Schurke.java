package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Schurke extends PassivePerk {

    public Schurke(WoolBattlePlayer p) {
        super(p, ChatColor.GOLD + "SChurke", Material.GOLD_INGOT, "Beim Treffen eines Gegners gibt es eine fünf Prozentige Chance, zwischen 4 und 16 Wolle aus dem Inventar des Gegners zu klauen");
    }
}
