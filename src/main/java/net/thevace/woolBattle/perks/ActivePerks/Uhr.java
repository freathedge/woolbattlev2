package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolBattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

public class Uhr extends ActivePerk {
    public Uhr(WoolBattlePlayer p) {
        super(20, 30, p, ChatColor.GOLD + "Uhr", Material.CLOCK, "Teleportiere dich zu dem letzten Block auf dem du gestanden bist");
    }

    @Override
    protected void applyEffect() {

        Location lastBlockLocation = player.getLastBlockLocation();

        if (lastBlockLocation != null) {

            float pitch = player.getPlayer().getLocation().getPitch();
            float yaw = player.getPlayer().getLocation().getYaw();
            Location teleport = lastBlockLocation.add(0.5, 1, 0.5);
            teleport.setYaw(yaw);
            teleport.setPitch(pitch);



            player.getPlayer().teleport(teleport);
            player.getPlayer().setVelocity(new Vector(0, 0, 0));
            player.getPlayer().setFallDistance(0);
        }
    }
}
