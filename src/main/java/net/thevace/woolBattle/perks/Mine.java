package net.thevace.woolBattle.perks;

import net.thevace.woolBattle.WoolbattlePlayer;
import org.bukkit.Material;

public class Mine extends ActivePerk{


    public Mine(long cooldown, int preis, WoolbattlePlayer p, String itemName, Material material) {
        super(cooldown, preis, p, itemName, material);
    }

    @Override
    protected void applyEffect() {

    }
}
