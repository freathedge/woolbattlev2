package net.thevace.woolbattle.inventorys;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.RenderContext;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PerkSelector extends View {
    @Override
    public void onInit(ViewConfigBuilder config) {
        config.title(ChatColor.AQUA + "Perks");
        config.size(3);
        config.cancelOnClick();
    }

    @Override
    public void onFirstRender(RenderContext render) {

        ItemStack perk1 = new ItemStack(Material.CHEST);
        ItemMeta meta1 = perk1.getItemMeta();
        meta1.setDisplayName(ChatColor.AQUA + "Active Perk 1");
        perk1.setItemMeta(meta1);

        ItemStack perk2 = new ItemStack(Material.CHEST);
        ItemMeta meta2 = perk2.getItemMeta();
        meta2.setDisplayName(ChatColor.AQUA + "Active Perk 2");
        perk2.setItemMeta(meta2);

        ItemStack perk3 = new ItemStack(Material.ENDER_CHEST);
        ItemMeta meta3 = perk3.getItemMeta();
        meta3.setDisplayName(ChatColor.AQUA + "Passive Perk");
        perk3.setItemMeta(meta3);

        render.slot(2, 3)
                .withItem(perk1)
                .onClick(click -> click.openForPlayer(ActivePerk1Selector.class));
        render.slot(2, 5)
                .withItem(perk2)
                .onClick(click -> click.openForPlayer(ActivePerk2Selector.class));
        render.slot(2, 7)
                .withItem(perk3)
                .onClick(click -> click.openForPlayer(PassivePerkSelector.class));
        for(int i = 1; i <= 9; i++) {
            render.slot(1, i)
                    .withItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        }
        for(int i = 1; i <= 9; i++) {
            render.slot(3, i)
                    .withItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        }

    }
}
