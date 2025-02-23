package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Naehkasten extends PassivePerk {

    public Naehkasten(WoolBattlePlayer p) {
        super(p, ChatColor.GOLD + "Nähkasten", Material.SHEARS, "Gibt dir beim abbauen von Wolle doppielt so viel Wolle und verdoppelt des insgesamten Platz von Wolle von 3 auf 6 Stacks");
    }

    @Override
    public void applyEffect() {
        System.out.println("applied naehkasten");
        player.setMaxWool(384);
        player.setWoolBreakMultiplier(2);
    }
}
