package net.thevace.woolbattle.perks.passiveperks;

import com.destroystokyo.paper.event.entity.EntityKnockbackByEntityEvent;
import io.papermc.paper.event.entity.EntityKnockbackEvent;
import net.thevace.woolbattle.GameManager;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.WoolBattlePlayerManager;
import net.thevace.woolbattle.perks.PassivePerk;
import net.thevace.woolbattle.perks.Perk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

public class Reflektor extends PassivePerk implements Listener {

    private final Random random = new Random();


    public Reflektor(WoolBattlePlayer p) {
        super(8, p, ChatColor.GOLD + "Reflektor", Material.SLIME_BALL, "Wenn du getroffen wirst, gibt es eine 5 Prozentige Chance, dass statt dir der Gegner Rückstoß erhält");
    }

    @Override
    public void applyEffect() {

    }


    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) return;

        Player defender = (Player) event.getEntity();
        Player attacker = (Player) event.getDamager();

        if(attacker != player.getPlayer()) {
            if (random.nextInt(100) < 5) {
                Vector reverseDirection = attacker.getLocation().toVector().subtract(defender.getLocation().toVector()).normalize();

                ItemStack weapon = attacker.getInventory().getItemInMainHand();
                int knockbackLevel = weapon.getEnchantmentLevel(Enchantment.KNOCKBACK);
                double knockbackStrength = 0.5 + (0.5 * knockbackLevel);

                attacker.setVelocity(reverseDirection.multiply(knockbackStrength));
                attacker.damage(0.000001);
                WoolBattlePlayerManager.getWoolBattlePlayer(attacker).setLastHit(Timestamp.valueOf(LocalDateTime.now()));
                event.setCancelled(true);
            }
        }

    }
}
