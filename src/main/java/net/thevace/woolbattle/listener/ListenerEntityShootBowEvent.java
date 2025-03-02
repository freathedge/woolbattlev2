package net.thevace.woolbattle.listener;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.WoolBattlePlayerManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class ListenerEntityShootBowEvent implements Listener {
    @EventHandler
    public void onBowShoot(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player shooter) {
            WoolBattlePlayer player = WoolBattlePlayerManager.getWoolBattlePlayer(shooter);

            if(player.getWool() >= 1) {
                player.removeWool(1);
            } else {
                event.setCancelled(true);
                player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);
            }

        }
    }
}
