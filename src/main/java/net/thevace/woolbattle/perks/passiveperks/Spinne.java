package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.ActivePerk;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Spinne extends PassivePerk {

    public Spinne(WoolBattlePlayer p) {
        super(1, p, ChatColor.GOLD + "Spinne", Material.SPIDER_SPAWN_EGG, "Sofern du mindestens drei Sekunden nicht getroffen wurdest, bist du in der Lage Blöcke hochzuklettern, wenn du sie anschaust");
    }
}
