package net.thevace.woolBattle.inventorys;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.RenderContext;

import net.thevace.woolBattle.PerkManager;
import net.thevace.woolBattle.WoolBattlePlayerManager;
import net.thevace.woolBattle.WoolBattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class ActivePerk1Selector extends View {

    WoolBattlePlayerManager playerManager;
    PerkManager perkManager;



    public ActivePerk1Selector(WoolBattlePlayerManager playerManager, PerkManager perkManager) {
        this.playerManager = playerManager;
        this.perkManager = perkManager;
    }

    @Override
    public void onInit(ViewConfigBuilder config) {
        config.title(ChatColor.AQUA + "Active Perk 1");
        config.size(4);
        config.cancelOnClick();
    }


    @Override
    public void onFirstRender(RenderContext render) {
        Player p = render.getPlayer();
        WoolBattlePlayer player = playerManager.getWoolBattlePlayer(p);

        ItemStack arrow = new ItemStack(Material.ARROW);
        ItemMeta arrowmeta = arrow.getItemMeta();
        arrowmeta.setDisplayName(ChatColor.GOLD + "Zurück");
        arrow.setItemMeta(arrowmeta);


        for (int i = 0; i < PerkManager.PERKS.size(); i++) {
            Class<? extends ActivePerk> perkClass = PerkManager.PERKS.get(i);
            ActivePerk perkInstance = PerkManager.createPerkInstance(perkClass, null);

            int slotX = i / 9 + 1;
            int slotY = i % 9 + 1;

            ItemStack item = perkInstance.getItem();

            if(player.getActivePerk1() != null && player.getActivePerk1().getClass().equals(perkClass)) {
                ItemMeta meta = item.getItemMeta();
                meta.addEnchant(Enchantment.AQUA_AFFINITY, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                item.setItemMeta(meta);
            }

            render.slot(slotX, slotY)
                    .withItem(item)
                    .onClick(click -> {
                        if (player.getActivePerk2() == null || !player.getActivePerk2().getClass().equals(perkClass)) {
                            player.setActivePerk1(PerkManager.createPerkInstance(perkClass, player));
                            click.openForPlayer(ActivePerk1Selector.class);
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
