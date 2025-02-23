package net.thevace.woolbattle.perks.activeperks;

import net.thevace.woolbattle.GameManager;
import net.thevace.woolbattle.PerkListenerManager;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.ActivePerk;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Pfeilbombe extends ActivePerk implements Listener {

    public Pfeilbombe(WoolBattlePlayer p) {
        super(15, 10, p, ChatColor.GOLD + "Pfeilbombe", Material.FIREWORK_STAR, "Schießt Pfeile in jede Richtung und kann Feind als auch Freund treffen");
    }

    @Override
    protected void applyEffect() {
        Snowball snowball = player.getPlayer().launchProjectile(Snowball.class);
        Vector direction = player.getPlayer().getLocation().getDirection().multiply(1.5);
        snowball.setVelocity(direction);

        NamespacedKey key = new NamespacedKey("woolbattle", "perktype");
        snowball.getPersistentDataContainer().set(key, PersistentDataType.STRING, "pfeilbombe");

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

            if (!"pfeilbombe".equals(perkType)) return;

            if (event.getHitBlock() != null) {
                Block targetBlock = event.getHitBlock();
                Location location = targetBlock.getLocation().clone().add(0, 2, 0);

                for (int i = 0; i < 100; i++) {
                    double phi = Math.random() * Math.PI;
                    double theta = Math.random() * 2 * Math.PI;

                    double xDirection = Math.sin(phi) * Math.cos(theta);
                    double yDirection = Math.cos(phi);
                    double zDirection = Math.sin(phi) * Math.sin(theta);

                    Arrow arrow = targetBlock.getWorld().spawn(location, Arrow.class);
                    arrow.setShooter(player.getPlayer());
                    Vector direction = new Vector(xDirection, yDirection, zDirection).normalize().multiply(1);
                    arrow.setVelocity(direction);

                }
            }
        }
    }

}
