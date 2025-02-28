package net.thevace.woolbattle.listener;

import net.thevace.woolbattle.WoolBattleGame;
import net.thevace.woolbattle.WoolBattlePlayerManager;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.activeperks.*;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
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

        if (event.getTo().getY() < 0) {
            game.handlePlayerDeath(player);
        }

        if(((Entity) p).isOnGround()) {
            p.setAllowFlight(true);
            player.setInDoubleJump(false);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player p = event.getPlayer();

        if (block.getType() == Material.RED_WOOL || block.getType() == Material.BLUE_WOOL || block.getType() == Material.GRAY_WOOL || block.getType() == Material.GREEN_WOOL) {
            game.handleWoolBreak(p, block);
            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1.0f, 1.0f);
        }

        event.setCancelled(true);
    }



    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        WoolBattlePlayer player = WoolBattlePlayerManager.getWoolBattlePlayer((Player) event.getEntity().getShooter());
        if (event.getEntity() instanceof Arrow arrow) {
            arrow.remove();
            if (event.getHitEntity() instanceof Player) {
                Player damager = (Player) arrow.getShooter();
                Player target = (Player) event.getHitEntity();
                if(target != null && damager != null) {
                    if(target == damager) {
                        event.setCancelled(true);
                    }
                    if(!game.handlePlayerHit(damager, target)){
                        if (WoolBattlePlayerManager.getWoolBattlePlayer(target).isProtected()) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
            if (event.getHitBlock() != null) {
                Block block = event.getHitBlock();
                if (block.getType() == Material.RED_WOOL || block.getType() == Material.BLUE_WOOL) {
                    if(game.getPlayerBlocks().contains(block.getLocation())) {
                        game.addBlockHit(block.getLocation(), player.getWoolDamage());
                        if(game.getBlockHit(block.getLocation()) >= 3)  {
                            block.setType(Material.AIR);
                            game.getPlayerBlocks().remove(block.getLocation());
                        }

                    }
                }
            }
        } else if (event.getEntity() instanceof EnderPearl enderPearl) {
            if (event.getHitEntity() instanceof Player) {
                Player damager = (Player) enderPearl.getShooter();
                Player target = (Player) event.getHitEntity();
                if(target != null && damager != null) {
                    damager.teleport(target.getLocation());
                    damager.playSound(damager.getLocation(), Sound.ENTITY_PLAYER_TELEPORT, 1.0f, 1.0f);
                }

            }
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
            projectile.remove();
        }

        if (event.getEntity() instanceof Player) {
            target = (Player) event.getEntity();
        }

        if (damager == null || target == null) return;

        if(game.handlePlayerHit(damager, target)) {
            event.setCancelled(true);
            return;
        }

        if(event.getDamager() instanceof Arrow arrow) {
            arrow.remove();
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
        Player p = event.getPlayer();
        if (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR) {
            return;
        }

        WoolBattlePlayer player = WoolBattlePlayerManager.getWoolBattlePlayer(p);

        if(!player.canDoubleJump()) {
            event.setCancelled(true);
            return;
        }

        if (!p.isFlying()) {
            event.setCancelled(true);
            p.setAllowFlight(false);

            Vector direction;

            if(player.getDoubleJumpHorizontalPower() != 0.0) {
                direction = p.getLocation().getDirection().normalize().multiply(player.getDoubleJumpHorizontalPower());
                direction.setY(player.getDoubleJumpVerticalPower());
            } else {
                direction = new Vector(0, player.getDoubleJumpVerticalPower(), 0);
            }

            p.setVelocity(direction);
            player.setInDoubleJump(true);
        }

    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
}
