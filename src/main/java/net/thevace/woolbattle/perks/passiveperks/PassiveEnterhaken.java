package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class PassiveEnterhaken extends PassivePerk {

    public PassiveEnterhaken(WoolBattlePlayer p) {
        super(15, 4, p, ChatColor.GOLD + "Enterhaken", Material.FISHING_ROD, "Zieht den Spieler welcher geschossen hat zum getroffenen Spieler");
    }

    @Override
    public void applyEffect() {

    }
}
