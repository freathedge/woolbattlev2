package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.WoolBattlePlayerManager;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class Stampfer extends PassivePerk implements Listener {
    public Stampfer(WoolBattlePlayer p) {
        super(4, p, ChatColor.GOLD + "Stampfer", Material.DIAMOND_BOOTS, "Shifte nach einem Doppelsprung um eine Stampfattacke auszuführen undf Genern in einem Radius von einem Block Rückstoß zu geben");
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        WoolBattlePlayer player = WoolBattlePlayerManager.getWoolBattlePlayer(event.getPlayer());
        if(player.isInDoubleJump()) {
        }
    }

}
