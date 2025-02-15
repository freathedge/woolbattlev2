package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import net.thevace.woolBattle.perks.Perk;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Freeze extends ActivePerk {

    WoolbattlePlayer target = null;

    public Freeze(WoolbattlePlayer p) {
        super(10, 16, p, ChatColor.GOLD + "Freeze", Material.ICE, "Friert den Spieler den du triffst ein");
    }

    @Override
    protected void applyEffect() {
        if(target == null) {
            Snowball snowball = player.getPlayer().launchProjectile(Snowball.class);
            Vector direction = player.getPlayer().getLocation().getDirection().multiply(1.5);
            snowball.setVelocity(direction);
            player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_SNOWBALL_THROW, 1.0f, 1.0f);
        } else {
            target.setFreezed(true);

            new BukkitRunnable() {

                @Override
                public void run() {
                    target.setFreezed(false);
                }
            }.runTaskTimer(Bukkit.getPluginManager().getPlugin("WoolBattle"), 0L, 200L);
        }
    }

    public void setTarget(WoolbattlePlayer target) {
        this.target = target;
    }
}
