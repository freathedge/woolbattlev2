package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class RocketJump extends PassivePerk {
    public RocketJump(WoolBattlePlayer p) {
        super(p, ChatColor.GOLD + "Rocket Jump", Material.FIREWORK_ROCKET, "Ermöglicht es dir bei einem Doppelsprung mehr vertikale höhe zu erreichen");
    }

    @Override
    public void applyEffect() {
        player.setDoubleJumpVerticalPower(1.5);
    }
}
