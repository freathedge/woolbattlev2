package net.thevace.woolBattle.listener;

import net.thevace.woolBattle.WoolBattleGame;
import net.thevace.woolBattle.WoolBattlePlayerManager;
import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.perks.ActivePerks.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class WoolBattleGameListener implements Listener {
    private WoolBattleGame game;
    WoolBattlePlayerManager playerManager;


    public WoolBattleGameListener(WoolBattleGame game, WoolBattlePlayerManager playerManager) {
        this.game = game;
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (event.getTo().getY() < 0) {
            game.handlePlayerHeight(player);
        }
    }



    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Block block = event.getBlock();
        Player p = event.getPlayer();

        if (block.getType() == Material.RED_WOOL || block.getType() == Material.BLUE_WOOL) {
            game.handleWoolBreak(p);
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().hasItemMeta()){

            Action action = event.getAction();
            Player player = event.getPlayer();
            WoolbattlePlayer woolbattlePlayer = playerManager.getWoolBattlePlayer(player);

            if (event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Pod")) {
                if (woolbattlePlayer.getActivePerk1() instanceof Pod pod) {
                    pod.activate();
                } else if (woolbattlePlayer.getActivePerk2() instanceof Pod pod) {
                    pod.activate();
                }

            } else if (event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Rettungskapsel")) {
                if (woolbattlePlayer.getActivePerk1() instanceof Rettungskapsel rettungskapsel) {
                    rettungskapsel.activate();
                } else if (woolbattlePlayer.getActivePerk2() instanceof Rettungskapsel rettungskapsel) {
                    rettungskapsel.activate();
                }
            } else if (event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Rettungsplattform")) {
                if (woolbattlePlayer.getActivePerk1() instanceof Rettungsplattform rettungsplattform) {
                    rettungsplattform.activate();
                } else if (woolbattlePlayer.getActivePerk2() instanceof Rettungsplattform rettungsplattform) {
                    rettungsplattform.activate();
                }
            } else if(event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Blink")) {
                if (woolbattlePlayer.getActivePerk1() instanceof Blink blink) {
                    blink.activate();
                } else if (woolbattlePlayer.getActivePerk2() instanceof Blink blink) {
                    blink.activate();
                }
            } else if(event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Feder")) {
                if (woolbattlePlayer.getActivePerk1() instanceof Feder feder) {
                    feder.activate();
                } else if (woolbattlePlayer.getActivePerk2() instanceof Feder feder) {
                    feder.activate();
                }
            } else if(event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Brückenbauer")) {
                if (woolbattlePlayer.getActivePerk1() instanceof Brueckenbauer brueckenbauer) {
                    brueckenbauer.activate();
                } else if (woolbattlePlayer.getActivePerk2() instanceof Brueckenbauer brueckenbauer) {
                    brueckenbauer.activate();
                }
            }

            event.setCancelled(true);

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
    public void onSnowballHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball) {
            Snowball snowball = (Snowball) event.getEntity();

            if (snowball.getShooter() instanceof Player) {
                Player player = (Player) snowball.getShooter();
                Player target = (Player) event.getHitEntity();
                WoolbattlePlayer woolbattlePlayer = playerManager.getWoolBattlePlayer(player);
                if (woolbattlePlayer.getActivePerk1() instanceof Tauscher tauscher) {
                    tauscher.setTarget(target);
                    tauscher.activate();
                } else if (woolbattlePlayer.getActivePerk2() instanceof Tauscher tauscher) {
                    tauscher.setTarget(target);
                    tauscher.activate();
                }

            }
        }
    }

    @EventHandler
    public void onMinePlaced(BlockPlaceEvent event) {
        if(event.getBlock().getType() == Material.RED_WOOL || event.getBlock().getType() == Material.BLUE_WOOL) {
            WoolbattlePlayer player = playerManager.getWoolBattlePlayer(event.getPlayer());
            player.removeWool(1);
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

    public void unregister() {
        PlayerMoveEvent.getHandlerList().unregister(this);
    }
}
