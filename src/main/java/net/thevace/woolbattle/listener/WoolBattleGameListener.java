package net.thevace.woolbattle.listener;

import net.kyori.adventure.text.Component;
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

        if(!WoolBattlePlayerManager.isRegistered(p) || !game.isPlayerInGame(p)) return;

        WoolBattlePlayer player = WoolBattlePlayerManager.getWoolBattlePlayer(p);
        Block block = p.getLocation().getBlock().getRelative(BlockFace.DOWN);

        if(block.getType() != Material.AIR) {
            player.setLastBlockLocation(block.getLocation());
        }

        if (event.getTo().getY() < 0) {
            game.handlePlayerDeath(player);
        }

        player.setInDoubleJump(false);
        if(player.getWool() < 5 ) {
            p.setAllowFlight(false);
            return;
        }

        if(((Entity) p).isOnGround()) {
            p.setAllowFlight(true);

        }

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player p = event.getPlayer();

        if(!WoolBattlePlayerManager.isRegistered(p) || !game.isPlayerInGame(p)) return;


        if (block.getType() == Material.RED_WOOL || block.getType() == Material.BLUE_WOOL || block.getType() == Material.GRAY_WOOL || block.getType() == Material.GREEN_WOOL) {
            game.handleWoolBreak(p, block);
            p.playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1.0f, 1.0f);
        }

        event.setCancelled(true);
    }



    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Player pl = (Player) event.getEntity().getShooter();

        if(!WoolBattlePlayerManager.isRegistered(pl) || !game.isPlayerInGame(pl)) return;

        WoolBattlePlayer player = WoolBattlePlayerManager.getWoolBattlePlayer(pl);
        if (event.getEntity() instanceof Arrow arrow) {
            arrow.remove();
            if (event.getHitEntity() instanceof Player p) {
                p.setArrowsInBody(0);
                Player damager = (Player) arrow.getShooter();

                if(damager != null) {
                    if(p == damager) {
                        event.setCancelled(true);
                    }
                    if(!game.handlePlayerHit(damager, p)){
                        if (WoolBattlePlayerManager.getWoolBattlePlayer(p).isProtected()) {
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
        }
        else if (event.getEntity() instanceof EnderPearl enderPearl) {
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
        Player p = event.getPlayer();
        if(!WoolBattlePlayerManager.isRegistered(p) || !game.isPlayerInGame(p)) return;


        if(event.getBlock().getType() == Material.RED_WOOL || event.getBlock().getType() == Material.BLUE_WOOL) {
            WoolBattlePlayer player = WoolBattlePlayerManager.getWoolBattlePlayer(event.getPlayer());
            game.handleWoolPlace(player, event.getBlock());
        }
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        Player p = (Player) event.getEntity();
        if(!WoolBattlePlayerManager.isRegistered(p) || !game.isPlayerInGame(p)) return;


        Player damager = null;
        Player target = null;

        if (event.getDamager() instanceof Player) {
            damager = (Player) event.getDamager();
        }else if (event.getDamager() instanceof Projectile) {
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

        if(!damager.equals(target)) {
            WoolBattlePlayerManager.getWoolBattlePlayer(target).setLastHitter(damager);
        }

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
        if (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR ||!WoolBattlePlayerManager.isRegistered(p) || !game.isPlayerInGame(p)) {
            return;
        }

        WoolBattlePlayer player = WoolBattlePlayerManager.getWoolBattlePlayer(p);


        if(player.getWool() < 5 ||!player.canDoubleJump()) {
            event.setCancelled(true);
            return;
        } else {
            player.removeWool(5);
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
            startDJCooldown(player);
        }
    }

    public void startDJCooldown(WoolBattlePlayer player) {
        player.setCanDoubleJump(false);
        player.getPlayer().setFoodLevel(6);

        new BukkitRunnable() {
            int currentFoodLevel = 6;

            @Override
            public void run() {
                if(currentFoodLevel < 20) {
                    currentFoodLevel += 1;
                    player.getPlayer().setFoodLevel(currentFoodLevel);
                } else {
                    cancel();
                    player.setCanDoubleJump(true);
                }
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("WoolBattle"), 1L, 5L);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack().getType() != Material.RED_WOOL || event.getItemDrop().getItemStack().getType() != Material.BLUE_WOOL) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBowShoot(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            Player shooter = (Player) event.getEntity();
            WoolBattlePlayer player = WoolBattlePlayerManager.getWoolBattlePlayer(shooter);

            if(player.getWool() >= 1) {
                player.removeWool(1);
            } else {
                event.setCancelled(true);
                player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            }

        }
    }
}
