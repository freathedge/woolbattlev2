package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolBattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.util.Vector;

public class Feder extends ActivePerk {
    public Feder(WoolBattlePlayer p) {
        super(15, 20, p, ChatColor.GOLD + "Feder", Material.FEATHER, "Wirft dich in die Richtung die du schaust");
    }

    @Override
    protected void applyEffect() {
        Vector direction = player.getPlayer().getLocation().getDirection().normalize();
        Vector velocity = direction.multiply(3);
        player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_BREEZE_WIND_BURST, 1.0f, 1.0f);

        player.getPlayer().setVelocity(velocity);
    }
}
