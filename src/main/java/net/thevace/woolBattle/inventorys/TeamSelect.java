package net.thevace.woolBattle.inventorys;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.state.MutableIntState;
import me.devnatan.inventoryframework.context.RenderContext;
import net.thevace.woolBattle.WoolBattleQueue;
import net.thevace.woolBattle.WoolbattlePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class TeamSelect extends View {
    private final MutableIntState counterState = mutableState(0);


    private WoolBattleQueue queue;

    public TeamSelect(WoolBattleQueue queue) {
        this.queue = queue;
    }

    @Override
    public void onInit(ViewConfigBuilder config) {
        config.title("Team Select");
        config.size(3);
    }

    @Override
    public void onFirstRender(RenderContext render) {
        ItemStack red = new ItemStack(Material.RED_WOOL);
        ItemMeta redmeta = red.getItemMeta();
        redmeta.setDisplayName(ChatColor.RED + "Red Team");
        red.setItemMeta(redmeta);

        ItemStack blue = new ItemStack(Material.BLUE_WOOL);
        ItemMeta bluemeta = blue.getItemMeta();
        bluemeta.setDisplayName(ChatColor.BLUE + "Blue Team");
        blue.setItemMeta(bluemeta);

        render.slot(2, 3, red)
                .onClick(click -> changeTeam(click.getPlayer(), queue.getTeam1())).cancelOnClick();
        render.slot(2, 7, blue)
                .onClick(click -> changeTeam(click.getPlayer(), queue.getTeam2())).cancelOnClick();
    }

    private void changeTeam(Player player, List<WoolbattlePlayer> team) {
        WoolbattlePlayer target = queue.getTeam1().stream()
                .filter(wp -> wp.getPlayer().equals(player))
                .findFirst()
                .orElse(null);

        if (target != null) {
            queue.getTeam1().remove(target);  // Entferne ihn aus Team 1
            queue.getTeam2().add(target);     // Füge ihn zu Team 2 hinzu
            player.sendMessage("Du wurdest in Team 2 verschoben!");
            return;
        }

        // Falls der Spieler in Team 2 ist, umgekehrt verschieben
        target = queue.getTeam2().stream()
                .filter(wp -> wp.getPlayer().equals(player))
                .findFirst()
                .orElse(null);

        if (target != null) {
            queue.getTeam2().remove(target);
            queue.getTeam1().add(target);
            player.sendMessage("Du wurdest in Team 1 verschoben!");
        } else {
            player.sendMessage("Du bist in keinem Team!");
        }


    }
}
