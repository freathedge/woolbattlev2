package net.thevace.woolBattle.listener;

import net.thevace.woolBattle.WoolBattleGame;
import net.thevace.woolBattle.WoolBattlePlayerManager;
import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.inventorys.TeamSelect;
import net.thevace.woolBattle.perks.Enterhaken;
import net.thevace.woolBattle.perks.Pod;
import net.thevace.woolBattle.perks.Rettungskapsel;
import net.thevace.woolBattle.perks.Tauscher;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if(block.getType() == Material.RED_WOOL || block.getType() == Material.BLUE_WOOL) {
            Player player = event.getPlayer();
            game.handleWoolBreak(player);
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

    public void unregister() {
        PlayerMoveEvent.getHandlerList().unregister(this);
    }
}
