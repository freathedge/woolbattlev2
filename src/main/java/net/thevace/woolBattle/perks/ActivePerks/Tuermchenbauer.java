package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.GameManager;
import net.thevace.woolBattle.WoolBattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Tuermchenbauer extends ActivePerk {
    public Tuermchenbauer(WoolBattlePlayer p) {
        super(15, 20, p, ChatColor.GOLD + "Türmchenbauer", Material.LADDER, "Erschafft einen Vertikalen Turm unter dir");
    }

    @Override
    protected void applyEffect() {
        Location location = player.getPlayer().getLocation().clone().add(0, -1, 0);

        while (location.getY() > 0) {
            Block block = location.getBlock();

            if (!block.getType().isAir()) {
                location.add(0, -1, 0);
                continue;
            }

            block.setType(player.getWoolMaterial());
            GameManager.getPlayerGame(player).addToPlayerBlocks(block.getLocation());

            location.add(0, -1, 0);
        }
    }



}
