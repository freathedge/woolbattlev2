package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import net.thevace.woolBattle.perks.Perk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.util.Vector;

public class Feder extends ActivePerk {
    public Feder(WoolbattlePlayer p) {
        super(15, 20, p, ChatColor.GOLD + "Feder", Material.FEATHER);
    }

    @Override
    protected void applyEffect() {
        Vector direction = player.getPlayer().getLocation().getDirection().normalize();
        Vector velocity = direction.multiply(3);
        player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_BREEZE_WIND_BURST, 1.0f, 1.0f);

        player.getPlayer().setVelocity(velocity);
    }
}
