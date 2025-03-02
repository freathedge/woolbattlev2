package net.thevace.woolbattle.listener;

import net.thevace.woolbattle.WoolBattleGame;
import net.thevace.woolbattle.WoolBattleGameManager;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.WoolBattlePlayerManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ListenerProjectileHit implements Listener {

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player damager)) return;
        if (!WoolBattlePlayerManager.isRegistered(damager)) return;

        WoolBattlePlayer damagerPlayer = WoolBattlePlayerManager.getWoolBattlePlayer(damager);
        WoolBattleGame game = WoolBattleGameManager.getPlayerGame(damagerPlayer);
        if (game == null || !game.isPlayerInGame(damager)) return;

        if (event.getEntity() instanceof Arrow arrow) {
            handleArrowHit(event, arrow, game, damagerPlayer);
        } else if (event.getEntity() instanceof EnderPearl enderPearl) {
            handleEnderPearlHit(event, enderPearl);
        }
    }

    private void handleArrowHit(ProjectileHitEvent event, Arrow arrow, WoolBattleGame game, WoolBattlePlayer damagerPlayer) {
        arrow.remove();
        if (event.getHitEntity() instanceof Player target) {
            handleArrowPlayerHit(event, arrow, target, game, damagerPlayer);
        } else if (event.getHitBlock() != null) {
            handleArrowBlockHit(event.getHitBlock(), game, damagerPlayer);
        }
    }

    private void handleArrowPlayerHit(ProjectileHitEvent event, Arrow arrow, Player target, WoolBattleGame game, WoolBattlePlayer damagerPlayer) {
        target.setArrowsInBody(0);
        if (!(arrow.getShooter() instanceof Player damager)) return;

        if (damager == target || game.checkPlayerHit(damager, target) || WoolBattlePlayerManager.getWoolBattlePlayer(target).isProtected()) {
            event.setCancelled(true);
        }
    }

    private void handleArrowBlockHit(Block block, WoolBattleGame game, WoolBattlePlayer damagerPlayer) {
        if (block.getType() == Material.RED_WOOL || block.getType() == Material.BLUE_WOOL) {
            if (game.getPlayerBlocks().contains(block.getLocation())) {
                game.addBlockHit(block.getLocation(), damagerPlayer.getWoolDamage());
                if (game.getBlockHit(block.getLocation()) >= 3) {
                    block.setType(Material.AIR);
                    game.getPlayerBlocks().remove(block.getLocation());
                }
            }
        }
    }

    private void handleEnderPearlHit(ProjectileHitEvent event, EnderPearl enderPearl) {
        if (!(enderPearl.getShooter() instanceof Player damager)) return;
        if (event.getHitEntity() instanceof Player target) {
            damager.teleport(target.getLocation());
            damager.playSound(damager.getLocation(), Sound.ENTITY_PLAYER_TELEPORT, 1.0f, 1.0f);
            event.setCancelled(true);
        }
    }
}