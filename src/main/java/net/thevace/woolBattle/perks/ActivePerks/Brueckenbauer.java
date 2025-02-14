package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Brueckenbauer extends ActivePerk {
    public Brueckenbauer(WoolbattlePlayer p) {
        super(0, 2, p, ChatColor.GOLD + "Brückenbauer", Material.PISTON, "Baut eine flache Linie in die Richtung die du schaust");
    }

    @Override
    protected void applyEffect() {
        Location playerLoc = player.getPlayer().getLocation();
        Vector direction = playerLoc.getDirection().setY(0).normalize(); // Nur horizontale Richtung
        Location startLoc = playerLoc.clone().add(0, -1, 0); // Startpunkt unter dem Spieler

        if (player.getWool() < preis) {
            return;
        }

        for (int i = 0; i < 20; i++) {
            startLoc.add(direction);
            Block block = startLoc.getBlock();

            if (block.getType() != Material.AIR) {
                break; // Stoppt, wenn ein Block im Weg ist
            }

            block.setType(player.getWoolMaterial()); // Block setzen

            if(player.getWool() < preis) {
                break;
            }

            player.removeWool(preis);
        }

    }
}
