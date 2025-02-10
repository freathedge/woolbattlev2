package net.thevace.woolBattle.listener;

import net.thevace.woolBattle.WoolBattleGame;
import net.thevace.woolBattle.WoolBattlePlayerManager;
import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.inventorys.TeamSelect;
import net.thevace.woolBattle.perks.Enterhaken;
import net.thevace.woolBattle.perks.Pod;
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

    private Map<Player, Block> fishingRodTargets = new HashMap<>();

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

            if (event.getItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Pod")) {
                WoolbattlePlayer woolbattlePlayer = playerManager.getWoolBattlePlayer(player);
                if (woolbattlePlayer.getActivePerk1() instanceof Pod pod) {
                    pod.activate();
                } else if (woolbattlePlayer.getActivePerk2() instanceof Pod pod) {
                    pod.activate();
                }

            }

        }

    }

//    @EventHandler
//    public void onPlayerFish(PlayerFishEvent event) {
//        Vector vector3;
//        Entity entity;
//        Block block;
//        Player player;
//        double d;
//
//        float hookThreshold = 0.25f;
//        float hForceMult = 0.2f;
//        float hForceMax = 7.5f;
//        float vForceMult = 0.5f;
//        float vForceBonus = 0.5f;
//        float vForceMax = 1.5f;
//
//        if (event.getState().equals(PlayerFishEvent.State.IN_GROUND) || event.getState().equals(PlayerFishEvent.State.FAILED_ATTEMPT)) {
//            entity = event.getHook();
//            block = entity.getWorld().getBlockAt(entity.getLocation().add(0.0, -hookThreshold, 0.0));
//
//            if (!block.isEmpty() && !block.isLiquid()) {
//                player = event.getPlayer();
//
//                vector3 = entity.getLocation().subtract(player.getLocation()).toVector();
//
//                if (vector3.getY() < 0.0)
//                    vector3.setY(0.0);
//
//                vector3.setX(vector3.getX() * hForceMult);
//                vector3.setY(vector3.getY() * vForceMult + vForceBonus);
//                vector3.setZ(vector3.getZ() * hForceMult);
//
//                d = hForceMax * hForceMax;
//                if (vector3.clone().setY(0.0).lengthSquared() > d) {
//                    d = d / vector3.lengthSquared();
//                    vector3.setX(vector3.getX() * d);
//                    vector3.setZ(vector3.getZ() * d);
//                }
//
//                if (vector3.getY() > vForceMax)
//                    vector3.setY(vForceMax);
//
//                player.setVelocity(vector3);
//            }
//        }
//    }

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
