package net.thevace.woolbattle.perks.activeperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.ActivePerk;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.stream.Collectors;

public class Greifer extends ActivePerk implements Listener {

    public Greifer(WoolBattlePlayer p) {
        super(15, 20, p, ChatColor.GOLD + "Greifer", Material.STICK, "Zieht den anvisierten Gegner zu dir");
    }

    @Override
    protected void applyEffect() {
        Player p = player.getPlayer();
        Player target = getLookedAtPlayer(p);

        if (target != null) {
            pullPlayerToPlayer(p, target);
        } else {
            cancelEvent();
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() != null && event.getItem().hasItemMeta() && event.getPlayer().equals(player.getPlayer())) {
            if (event.getItem().getItemMeta().getDisplayName().equals(itemName)) {
                activate();
                event.setCancelled(true);
            }
        }
    }

    private Player getLookedAtPlayer(Player player) {
        double maxDistance = 20;
        List<Player> potentialTargets = Bukkit.getOnlinePlayers().stream()
                .filter(other -> other != player && player.hasLineOfSight(other))
                .filter(other -> player.getLocation().distance(other.getLocation()) <= maxDistance)
                .sorted((p1, p2) -> Double.compare(player.getLocation().distance(p1.getLocation()), player.getLocation().distance(p2.getLocation())))
                .collect(Collectors.toList());

        for (Player target : potentialTargets) {
            if (isLookingAt(player, target)) {
                return target;
            }
        }

        return null;
    }

    private boolean isLookingAt(Player player, Player target) {
        Vector playerDirection = player.getLocation().getDirection().normalize();
        Vector targetDirection = target.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
        return playerDirection.dot(targetDirection) > 0.97;
    }

    private void pullPlayerToPlayer(Player player, Player target) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline() || !target.isOnline()) {
                    cancel();
                    return;
                }

                Vector playerPos = player.getLocation().toVector();
                Vector targetPos = target.getLocation().toVector();
                Vector direction = playerPos.clone().subtract(targetPos);
                double distance = direction.length();

                direction.normalize().multiply(Math.min(2, distance));
                Vector newTargetPos = targetPos.add(direction);

                if (newTargetPos.distance(playerPos) < 0.1) {
                    target.setVelocity(new Vector(0, 0, 0));
                    cancel();
                    return;
                }

                target.setVelocity(direction);
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("WoolBattle"), 0L, 1L);
    }
}
