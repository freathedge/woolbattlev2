package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class LongJump extends PassivePerk {

    public LongJump(WoolBattlePlayer p) {
        super(p, ChatColor.GOLD + "Long Jump", Material.GOLDEN_BOOTS, "Ermöglicht es dir bei einem Doppelsprung mehr horizontale Distanz zu überbrücken");
    }

    @Override
    protected void applyEffect() {
        player.setDoubleJumpHorizontalPower(2.0);
    }
}
