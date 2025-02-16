package net.thevace.woolBattle.listener;

import net.thevace.woolBattle.PerkManager;
import net.thevace.woolBattle.WoolBattleGame;
import net.thevace.woolBattle.WoolBattlePlayerManager;
import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import net.thevace.woolBattle.perks.ActivePerks.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

        WoolbattlePlayer player = playerManager.getWoolBattlePlayer(p);
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
        }

        event.setCancelled(true);

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().hasItemMeta()){

            Action action = event.getAction();
            Player player = event.getPlayer();
            WoolbattlePlayer woolbattlePlayer = playerManager.getWoolBattlePlayer(player);

            String displayName = event.getItem().getItemMeta().getDisplayName();
            boolean cancelEvent = false;


            if (displayName.equals(ChatColor.GOLD + "Pod")) {
                if (woolbattlePlayer.getActivePerk1() instanceof Pod pod) {
                    pod.activate();
                } else if (woolbattlePlayer.getActivePerk2() instanceof Pod pod) {
                    pod.activate();
                }
                cancelEvent = true;
            } else if (displayName.equals(ChatColor.GOLD + "Rettungskapsel")) {
                if (woolbattlePlayer.getActivePerk1() instanceof Rettungskapsel rettungskapsel) {
                    rettungskapsel.activate();
                } else if (woolbattlePlayer.getActivePerk2() instanceof Rettungskapsel rettungskapsel) {
                    rettungskapsel.activate();
                }
                cancelEvent = true;
            } else if (displayName.equals(ChatColor.GOLD + "Rettungsplattform")) {
                if (woolbattlePlayer.getActivePerk1() instanceof Rettungsplattform rettungsplattform) {
                    rettungsplattform.activate();
                } else if (woolbattlePlayer.getActivePerk2() instanceof Rettungsplattform rettungsplattform) {
                    rettungsplattform.activate();
                }
                cancelEvent = true;
            } else if (displayName.equals(ChatColor.GOLD + "Blink")) {
                if (woolbattlePlayer.getActivePerk1() instanceof Blink blink) {
                    blink.activate();
                } else if (woolbattlePlayer.getActivePerk2() instanceof Blink blink) {
                    blink.activate();
                }
                cancelEvent = true;
            } else if (displayName.equals(ChatColor.GOLD + "Feder")) {
                if (woolbattlePlayer.getActivePerk1() instanceof Feder feder) {
                    feder.activate();
                } else if (woolbattlePlayer.getActivePerk2() instanceof Feder feder) {
                    feder.activate();
                }
                cancelEvent = true;
            } else if (displayName.equals(ChatColor.GOLD + "Brückenbauer")) {
                if (woolbattlePlayer.getActivePerk1() instanceof Brueckenbauer brueckenbauer) {
                    brueckenbauer.activate();
                } else if (woolbattlePlayer.getActivePerk2() instanceof Brueckenbauer brueckenbauer) {
                    brueckenbauer.activate();
                }
                cancelEvent = true;
            } else if (displayName.equals(ChatColor.GOLD + "Schutzschild")) {
                if (woolbattlePlayer.getActivePerk1() instanceof Schutzschild schutzschild) {
                    schutzschild.activate();
                } else if (woolbattlePlayer.getActivePerk2() instanceof Schutzschild schutzschild) {
                    schutzschild.activate();
                }
                cancelEvent = true;
            } else if (displayName.equals(ChatColor.GOLD + "Minigun")) {
                if (woolbattlePlayer.getActivePerk1() instanceof Minigun minigun) {
                    minigun.activate();
                } else if (woolbattlePlayer.getActivePerk2() instanceof Minigun minigun) {
                    minigun.activate();
                }
                cancelEvent = true;
            } else if (displayName.equals(ChatColor.GOLD + "Tauscher")) {
                if (woolbattlePlayer.getActivePerk1() instanceof Tauscher tauscher) {
                    tauscher.throwSnowball();
                } else if (woolbattlePlayer.getActivePerk2() instanceof Tauscher tauscher) {
                    tauscher.throwSnowball();
                }
                cancelEvent = true;
            } else if (displayName.equals(ChatColor.GOLD + "Freeze")) {
                if (woolbattlePlayer.getActivePerk1() instanceof Freeze freeze) {
                    freeze.throwSnowball();
                } else if (woolbattlePlayer.getActivePerk2() instanceof Freeze freeze) {
                    freeze.throwSnowball();
                }
            } else if (displayName.equals(ChatColor.GOLD + "Uhr")) {
                if (woolbattlePlayer.getActivePerk1() instanceof Uhr uhr) {
                    uhr.activate();
                } else if (woolbattlePlayer.getActivePerk2() instanceof Uhr uhr) {
                    uhr.activate();
                }
            }

            if(cancelEvent){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        WoolbattlePlayer player = playerManager.getWoolBattlePlayer(event.getPlayer());
        if (event.getState().equals(PlayerFishEvent.State.IN_GROUND) || event.getState().equals(PlayerFishEvent.State.FAILED_ATTEMPT) || event.getState().equals(PlayerFishEvent.State.REEL_IN)) {
            if (player.getActivePerk1() instanceof Enterhaken enterhaken) {
                enterhaken.setEvent(event);
                enterhaken.activate();
            } else if (player.getActivePerk2() instanceof Enterhaken enterhaken) {
                enterhaken.setEvent(event);
                enterhaken.activate();
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball) {
            Snowball snowball = (Snowball) event.getEntity();

            if (snowball.getShooter() instanceof Player) {
                Player player = (Player) snowball.getShooter();
                Player target = (Player) event.getHitEntity();

                if(player != null && target != null) {
                    if(!game.handlePlayerHit(player, target)) {
                        WoolbattlePlayer woolbattlePlayer = playerManager.getWoolBattlePlayer(player);
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
            System.out.println("arrow shot");
            if (event.getHitEntity() instanceof Player) {
                Player damager = (Player) arrow.getShooter();
                Player target = (Player) event.getHitEntity();
                System.out.println("Player: " + damager.getDisplayName());
                System.out.println("Target: " + target.getDisplayName());
                if(!game.handlePlayerHit(damager, target)){
                    if (playerManager.getWoolBattlePlayer(target).isProtected()) {
                        event.setCancelled(true);
                    }
                }
            }
            if (event.getHitBlock() != null) {
                Block block = event.getHitBlock();
                if (block.getType() == Material.RED_WOOL || block.getType() == Material.BLUE_WOOL) {
                    block.setType(Material.AIR);
                }
            }

            event.getEntity().remove();
        }
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(event.getBlock().getType() == Material.RED_WOOL || event.getBlock().getType() == Material.BLUE_WOOL) {
            WoolbattlePlayer player = playerManager.getWoolBattlePlayer(event.getPlayer());
            game.handleWoolPlace(player, event.getBlock());
            event.setCancelled(false);
            return;
        } else if (event.getBlock().getType() == Material.STONE_PRESSURE_PLATE) {
            Location plateLocation = event.getBlock().getLocation();

            WoolbattlePlayer woolbattlePlayer = playerManager.getWoolBattlePlayer(event.getPlayer());

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
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();
        WoolbattlePlayer woolbattlePlayer = playerManager.getWoolBattlePlayer(p);
        game.handlePlayerDeath(woolbattlePlayer);
        event.setKeepInventory(true);
        event.getDrops().clear();
        event.setDeathMessage(null);
    }

//    @EventHandler
//    public void onFallDamage(EntityDamageEvent event) {
//        System.out.println("damage event called");
//        if (event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
//            Player p = (Player) event.getEntity();
//            WoolbattlePlayer woolbattlePlayer = playerManager.getWoolBattlePlayer(p);
//            if(!woolbattlePlayer.hasFalldamage()) {
//                System.out.println("falldamage event canceled");
//                event.setCancelled(true);
//            }
//        }
//    }

}
