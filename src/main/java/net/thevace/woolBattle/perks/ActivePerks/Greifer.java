package net.thevace.woolBattle.perks.ActivePerks;

import net.thevace.woolBattle.WoolBattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import static org.bukkit.Bukkit.getPlayer;

public class Greifer extends ActivePerk implements Listener {
    public Greifer(WoolBattlePlayer p) {
        super(15, 20, p, ChatColor.GOLD + "Greifer", Material.STICK, "Zieht den anvisierten Gegner zu dir");
        if(p != null) {
            Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin("WoolBattle"));
        }
    }

    @Override
    protected void applyEffect() {
        Player p = player.getPlayer();
        Player target = getTargetPlayer(p);

        if (target != null && isLookingAt(p, target)) {
            pullPlayerToPlayer(p, target);
        } else {
            cancelEvent();
        }
    }

    private boolean isLookingAt(Player player, Player target) {
        Vector playerDirection = player.getLocation().getDirection().normalize();
        Vector targetDirection = target.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();

        double dotProduct = playerDirection.dot(targetDirection); // Berechnung des Winkels

        return dotProduct > 0.97; // Ca. 15 Grad Blickwinkel-Toleranz
    }

    private void pullPlayerToPlayer(Player player, Player target) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline() || !target.isOnline()) {
                    cancel();
                    return;
                }

                Vector direction = player.getLocation().toVector().subtract(target.getLocation().toVector());
                double distance = direction.length();

                if (distance < 1.5) {
                    cancel();
                    return;
                }

                direction.normalize().multiply(2);
                target.setVelocity(direction);
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("WoolBattle"), 0L, 1L);
    }

    private Player getTargetPlayer(Player player) {
        double maxDistance = 10;
        Player closest = null;
        double closestDistance = maxDistance;

        for (Player other : Bukkit.getOnlinePlayers()) {
            if (other == player) continue;

            double distance = player.getLocation().distance(other.getLocation());
            if (distance < closestDistance) {
                closest = other;
                closestDistance = distance;
            }
        }
        return closest;
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        if (event.getState().equals(PlayerFishEvent.State.IN_GROUND) || event.getState().equals(PlayerFishEvent.State.FAILED_ATTEMPT) || event.getState().equals(PlayerFishEvent.State.REEL_IN)) {
            activate();
        }
    }

}
