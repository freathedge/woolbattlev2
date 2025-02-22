package net.thevace.woolbattle.inventorys;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.RenderContext;
import net.thevace.woolbattle.PerkManager;
import net.thevace.woolbattle.WoolBattlePlayerManager;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.ActivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ActivePerk2Selector extends View {

    PerkManager perkManager;

    public ActivePerk2Selector(PerkManager perkManager) {
        this.perkManager = perkManager;
    }

    @Override
    public void onInit(ViewConfigBuilder config) {
        config.title(ChatColor.AQUA + "Active Perk 2");
        config.size(4);
        config.cancelOnClick();
    }


    @Override
    public void onFirstRender(RenderContext render) {
        Player p = render.getPlayer();
        WoolBattlePlayer player = WoolBattlePlayerManager.getWoolBattlePlayer(p);

        ItemStack arrow = new ItemStack(Material.ARROW);
        ItemMeta arrowmeta = arrow.getItemMeta();
        arrowmeta.setDisplayName(ChatColor.GOLD + "Zurück");
        arrow.setItemMeta(arrowmeta);

        for (int i = 0; i < PerkManager.activePerks.size(); i++) {
            Class<? extends ActivePerk> perkClass = PerkManager.activePerks.get(i);
            ActivePerk perkInstance = PerkManager.createActivePerkInstance(perkClass, null);

            int slotX = i / 9 + 1;
            int slotY = i % 9 + 1;

            ItemStack item = perkInstance.getItem();

            if(player.getActivePerk2() != null && player.getActivePerk2().getClass().equals(perkClass)) {
                ItemMeta meta = item.getItemMeta();
                meta.addEnchant(Enchantment.AQUA_AFFINITY, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                item.setItemMeta(meta);
            }

            render.slot(slotX, slotY)
                    .withItem(item)
                    .onClick(click -> {
                        if (player.getActivePerk1() == null || !player.getActivePerk1().getClass().equals(perkClass)) {
                            player.setActivePerk2(PerkManager.createActivePerkInstance(perkClass, player));
                            click.openForPlayer(ActivePerk2Selector.class);
                        }
                    });
        }

        render.slot(4, 1)
                .withItem(arrow)
                .onClick(click -> {
                    click.openForPlayer(PerkSelector.class);
                });
    }
}
