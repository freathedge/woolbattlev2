package net.thevace.woolbattle.listener;

import net.thevace.woolbattle.WoolBattleGame;
import net.thevace.woolbattle.WoolBattlePlayerManager;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.activeperks.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class WoolBattleGameListener implements Listener {
    private WoolBattleGame game;


    public WoolBattleGameListener(WoolBattleGame game) {
        this.game = game;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();

        WoolBattlePlayer player = WoolBattlePlayerManager.getWoolBattlePlayer(p);
        Block block = p.getLocation().getBlock().getRelative(BlockFace.DOWN);

        if(block.getType() != Material.AIR) {
            player.setLastBlockLocation(block.getLocation());
        }


        if (player.isFreezed()) {
            if (event.getFrom().getX() != event.getTo().getX() ||
                    event.getFrom().getZ() != event.getTo().getZ() ||
                    event.getFrom().getY() < event.getTo().getY()) {
                event.setTo(event.getFrom());
            }
        }
        if (event.getTo().getY() < 0) {
            game.handlePlayerDeath(player);
        }

        if(((Entity) p).isOnGround()) {
            p.setAllowFlight(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player p = event.getPlayer();

        if (block.getType() == Material.RED_WOOL || block.getType() == Material.BLUE_WOOL) {
            game.handleWoolBreak(p, block);
            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1.0f, 1.0f);
        }

        event.setCancelled(true);
    }



    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow arrow) {
            if (event.getHitEntity() instanceof Player) {
                Player damager = (Player) arrow.getShooter();
                Player target = (Player) event.getHitEntity();
                if(target != null && damager != null) {
                    if(target == damager) {
                        event.setCancelled(true);
                        event.getEntity().remove();
                    }
                    if(!game.handlePlayerHit(damager, target)){
                        if (WoolBattlePlayerManager.getWoolBattlePlayer(target).isProtected()) {
                            event.setCancelled(true);
                            event.getEntity().remove();
                        }
                    }
                }

            }
            if (event.getHitBlock() != null) {
                Block block = event.getHitBlock();
                if (block.getType() == Material.RED_WOOL || block.getType() == Material.BLUE_WOOL) {
                    if(game.getPlayerBlocks().contains(block.getLocation())) {
                        block.setType(Material.AIR);
                    }
                }
            }

            event.getEntity().remove();
        }
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(event.getBlock().getType() == Material.RED_WOOL || event.getBlock().getType() == Material.BLUE_WOOL) {
            WoolBattlePlayer player = WoolBattlePlayerManager.getWoolBattlePlayer(event.getPlayer());
            game.handleWoolPlace(player, event.getBlock());
        }
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        Player damager = null;
        Player target = null;

        if (event.getDamager() instanceof Player) {
            damager = (Player) event.getDamager();
        }

        if (event.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) event.getDamager();
            if (projectile.getShooter() instanceof Player) {
                damager = (Player) projectile.getShooter();
            }
        }

        if (event.getEntity() instanceof Player) {
            target = (Player) event.getEntity();
        }

        if (damager == null || target == null) return;

        if(game.handlePlayerHit(damager, target)) {
            event.setCancelled(true);
            return;
        }

       WoolBattlePlayerManager.getWoolBattlePlayer(target).setLastHit(Timestamp.valueOf(LocalDateTime.now()));

        event.setDamage(0.000001);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setDamage(0.000001);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {

        System.out.println("Flight toggled");

        Player p = event.getPlayer();

        if (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR) {
            return;
        }

        if(!p.isFlying()) {
            event.setCancelled(true);

            p.setAllowFlight(false);
            p.setVelocity(p.getLocation().getDirection().setY(1));
        }

    }
}
