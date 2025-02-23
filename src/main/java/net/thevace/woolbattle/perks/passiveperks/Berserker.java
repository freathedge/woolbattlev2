package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Berserker extends PassivePerk {
    public Berserker(WoolBattlePlayer p) {
        super(1, p, ChatColor.GOLD + "Berserker", Material.GOLDEN_AXE, "Alle 15 Sekunden wird der Rückstoß deines Bogens um ein Level erweitert. Wenn du schaden nimmst, setzt sich das Level auf den Standard zurück");
    }

    @Override
    public void applyEffect() {

    }
}
