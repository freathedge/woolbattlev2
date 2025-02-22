package net.thevace.woolbattle.perks.activeperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.ActivePerk;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

public class Woolbomb extends ActivePerk implements Listener {

    public Woolbomb(WoolBattlePlayer p) {
        super(15, 16, p, ChatColor.GOLD + "Woolbomb", Material.WHITE_WOOL, "Wirft ein TNT in die richtung die du schaust und gibt Spielern Rückstoß, die in der Nähe sind");
        if(p != null) {
            Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("WoolBattle"));
        }
    }

    @Override
    protected void applyEffect() {
        Snowball snowball = player.getPlayer().launchProjectile(Snowball.class);
        Vector direction = player.getPlayer().getLocation().getDirection().multiply(1.5);
        snowball.setVelocity(direction);

        NamespacedKey key = new NamespacedKey("woolbattle", "perktype");
        snowball.getPersistentDataContainer().set(key, PersistentDataType.STRING, "woolbomb");

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

            NamespacedKey key = new NamespacedKey("woolbattle", "perktype");
            String perkType = snowball.getPersistentDataContainer().get(key, PersistentDataType.STRING);
            if (!"woolbomb".equals(perkType)) return;


            if (event.getHitBlock() != null) {
                Player player = (Player) snowball.getShooter();
                Block targetBlock = event.getHitBlock();

                if(player != null && targetBlock != null) {
                    Location location = targetBlock.getLocation();
                    TNTPrimed tnt = (TNTPrimed) location.getWorld().spawnEntity(location.add(0.5, 1, 0.5), EntityType.TNT);
                    tnt.setFuseTicks(10);
                }
            }
        }
    }

    @EventHandler
    public void onTNTExplosion(EntityExplodeEvent event) {
        if (event.getEntity() instanceof TNTPrimed) {
            event.setCancelled(true);

            TNTPrimed tnt = (TNTPrimed) event.getEntity();
            Location tntLocation = tnt.getLocation();

            double radius = 3.0;

            for (Entity entity : tntLocation.getWorld().getNearbyEntities(tntLocation, radius, radius, radius)) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    Vector knockback = player.getLocation().toVector().subtract(tntLocation.toVector()).normalize();
                    knockback.multiply(4);
                    knockback.setY(0.5);
                    player.setVelocity(knockback);
                }
            }

            for (Player player : tnt.getWorld().getPlayers()) {
                player.spawnParticle(Particle.EXPLOSION_EMITTER, tntLocation, 1);
                player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);
            }

        }
    }
}
