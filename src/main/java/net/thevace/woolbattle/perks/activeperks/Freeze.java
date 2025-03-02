package net.thevace.woolbattle.perks.activeperks;

import net.thevace.woolbattle.*;
import net.thevace.woolbattle.perks.ActivePerk;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

public class Freeze extends ActivePerk implements Listener {

    WoolBattlePlayer target = null;

    public Freeze(WoolBattlePlayer p) {
        super(10, 16, p, ChatColor.GOLD + "Freeze", Material.ICE, "Friert den Spieler den du triffst ein");
    }

    @Override
    protected void applyEffect() {
        Snowball snowball = player.getPlayer().launchProjectile(Snowball.class);
        Vector direction = player.getPlayer().getLocation().getDirection().multiply(1.5);
        snowball.setVelocity(direction);

        NamespacedKey key = new NamespacedKey("woolbattle", "perktype");
        snowball.getPersistentDataContainer().set(key, PersistentDataType.STRING, "freeze");

        player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_SNOWBALL_THROW, 1.0f, 1.0f);
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() != null && event.getItem().getItemMeta() != null && event.getItem().hasItemMeta() && event.getPlayer().equals(player.getPlayer())) {
            if (event.getItem().getItemMeta().getDisplayName().equals(itemName)) {
                activate();
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball) {
            Snowball snowball = (Snowball) event.getEntity();

            if (snowball.getShooter() instanceof Player) {

                if(!snowball.getShooter().equals(player.getPlayer())) return;

                NamespacedKey key = new NamespacedKey("woolbattle", "perktype");
                String perkType = snowball.getPersistentDataContainer().get(key, PersistentDataType.STRING);

                if (!"freeze".equals(perkType)) return;

                Player player = (Player) snowball.getShooter();
                Player target = (Player) event.getHitEntity();
                WoolBattleGame game = WoolBattleGameManager.getPlayerGame(player);

                if (player != null && target != null) {
                    if (!game.checkPlayerHit(player, target)) {
                        target.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0);
                        target.getAttribute(Attribute.JUMP_STRENGTH).setBaseValue(0);
                        WoolBattlePlayerManager.getWoolBattlePlayer(target).setCanDoubleJump(false);
                        Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("WoolBattle"), () -> {
                            target.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(0.1);
                            target.getAttribute(Attribute.JUMP_STRENGTH).setBaseValue(0.42);
                            WoolBattlePlayerManager.getWoolBattlePlayer(target).setCanDoubleJump(true);
                        }, 100L);
                    }
                }
            }
        }
    }
}
