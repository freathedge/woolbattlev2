package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.WoolBattlePlayerManager;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

public class Stampfer extends PassivePerk implements Listener {

    private boolean stamp = false;

    public Stampfer(WoolBattlePlayer p) {
        super(4, p, ChatColor.GOLD + "Stampfer", Material.DIAMOND_BOOTS, "Shifte nach einem Doppelsprung um eine Stampfattacke auszuführen undf Genern in einem Radius von einem Block Rückstoß zu geben");
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {

        if(!event.getPlayer().equals(player.getPlayer())) return;

        if(player.isInDoubleJump()) {
            stamp = true;
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        if(!event.getPlayer().equals(player.getPlayer())) return;

        if(((Entity) player.getPlayer()).isOnGround() && stamp) {
            double radius = 1.0;
            for (Entity entity : event.getPlayer().getNearbyEntities(radius, radius, radius)) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    Vector knockback = player.getLocation().toVector().subtract(event.getPlayer().getLocation().toVector()).normalize();
                    knockback.multiply(4);
                    knockback.setY(0.5);
                    player.setVelocity(knockback);

                }
            }

            for (Player p : event.getPlayer().getWorld().getPlayers()) {
                p.spawnParticle(Particle.GUST_EMITTER_SMALL, event.getPlayer().getLocation(), 1);
                p.playSound(event.getPlayer().getLocation(), Sound.ENTITY_BREEZE_WIND_BURST, 1.0f, 1.0f);
            }
            stamp = false;
        }
    }

}
