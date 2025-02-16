package net.thevace.woolBattle.inventorys;

import io.papermc.paper.datacomponent.item.ItemLore;
import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.RenderContext;
import net.thevace.woolBattle.WoolbattlePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Voting extends View {

    @Override
    public void onInit(ViewConfigBuilder config) {
        config.title(ChatColor.GOLD + "Voting");
        config.size(3);
        config.cancelOnClick();
    }

    @Override
    public void onFirstRender(RenderContext render) {

        ItemStack map = new ItemStack(Material.MAP);
        ItemMeta mapMeta = map.getItemMeta();
        mapMeta.setDisplayName(ChatColor.GOLD + "Map");
        List<String> maplore = new ArrayList<>();
        maplore.add(ChatColor.GRAY + "Stimme für deine Lieblingsmap");
        mapMeta.setLore(maplore);
        map.setItemMeta(mapMeta);

        ItemStack leben = new ItemStack(Material.NAME_TAG);
        ItemMeta lebenMeta = leben.getItemMeta();
        lebenMeta.setDisplayName(ChatColor.RED + "Leben");
        List<String> lebenlore = new ArrayList<>();
        lebenlore.add(ChatColor.GRAY + "Stimme für die Lebensanzahl ab");
        lebenMeta.setLore(lebenlore);
        leben.setItemMeta(lebenMeta);


        render.slot(2, 3)
                .withItem(map)
                .onClick(click -> click.openForPlayer(MapVoting.class));
        render.slot(2, 7)
                .withItem(leben)
                .onClick(click -> click.openForPlayer(LebenVoting.class));
    }
}
