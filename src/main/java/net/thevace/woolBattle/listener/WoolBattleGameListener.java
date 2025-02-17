package net.thevace.woolBattle.listener;

import net.thevace.woolBattle.WoolBattleGame;
import net.thevace.woolBattle.WoolBattlePlayerManager;
import net.thevace.woolBattle.WoolBattlePlayer;
import net.thevace.woolBattle.perks.ActivePerks.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class WoolBattleGameListener implements Listener {
    private WoolBattleGame game;
    private WoolBattlePlayerManager playerManager;


    public WoolBattleGameListener(WoolBattleGame game, WoolBattlePlayerManager playerManager) {
        this.game = game;
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();

        WoolBattlePlayer player = playerManager.getWoolBattlePlayer(p);
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

//    @EventHandler
//    public void onPlayerInteract(PlayerInteractEvent event) {
//        if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().hasItemMeta()){
//
//            Action action = event.getAction();
//            Player player = event.getPlayer();
//            WoolBattlePlayer woolbattlePlayer = playerManager.getWoolBattlePlayer(player);
//
//            String displayName = event.getItem().getItemMeta().getDisplayName();
//            boolean cancelEvent = false;
//
//
//
//
//            if(cancelEvent){
//                event.setCancelled(true);
//            }
//        }
//    }



    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball) {
            Snowball snowball = (Snowball) event.getEntity();

            if (snowball.getShooter() instanceof Player) {
                Player player = (Player) snowball.getShooter();
                Player target = (Player) event.getHitEntity();

                if(player != null && target != null) {
                    if(!game.handlePlayerHit(player, target)) {
                        WoolBattlePlayer woolbattlePlayer = playerManager.getWoolBattlePlayer(player);
                        if (player.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Tauscher")) {
                            if (woolbattlePlayer.getActivePerk1() instanceof Tauscher tauscher) {
                                tauscher.setTarget(target);
                                tauscher.activate();
                            } else if (woolbattlePlayer.getActivePerk2() instanceof Tauscher tauscher) {
                                tauscher.setTarget(target);
                                tauscher.activate();
                            }
                        } else if (player.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Freeze")) {
                            if(woolbattlePlayer.getActivePerk1() instanceof Freeze freeze) {
                                freeze.setTarget(playerManager.getWoolBattlePlayer(target));
                                freeze.activate();
                            } else if (woolbattlePlayer.getActivePerk2() instanceof Freeze freeze) {
                                freeze.setTarget(playerManager.getWoolBattlePlayer(target));
                                freeze.activate();
                            }
                        }
                    }
                }
            }
        } else if (event.getEntity() instanceof Arrow arrow) {
            if (event.getHitEntity() instanceof Player) {
                Player damager = (Player) arrow.getShooter();
                Player target = (Player) event.getHitEntity();
                if(target != null && damager != null) {
                    System.out.println("damager: " + damager);
                    if(target == damager) {
                        event.setCancelled(true);
                        event.getEntity().remove();
                    }
                    if(!game.handlePlayerHit(damager, target)){
                        if (playerManager.getWoolBattlePlayer(target).isProtected()) {
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
            WoolBattlePlayer player = playerManager.getWoolBattlePlayer(event.getPlayer());
            game.handleWoolPlace(player, event.getBlock());
            event.setCancelled(false);
            return;
        } else if (event.getBlock().getType() == Material.STONE_PRESSURE_PLATE) {
            Location plateLocation = event.getBlock().getLocation();

            WoolBattlePlayer woolbattlePlayer = playerManager.getWoolBattlePlayer(event.getPlayer());

            if (woolbattlePlayer.getActivePerk1() instanceof Mine mine) {
                mine.setPlateLocation(plateLocation);
                mine.activate();
            } else if (woolbattlePlayer.getActivePerk2() instanceof Mine mine) {
                mine.setPlateLocation(plateLocation);
                mine.activate();
            }
            event.setCancelled(true);
        }
        event.setCancelled(true);
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
        }

        event.setDamage(0.000001);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setDamage(0.000001);
            event.setCancelled(true);
        }
    }
}
