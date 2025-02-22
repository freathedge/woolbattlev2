package net.thevace.woolbattle.inventorys;


import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.RenderContext;
import net.thevace.woolbattle.PerkManager;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.WoolBattlePlayerManager;
import net.thevace.woolbattle.perks.ActivePerk;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PassivePerkSelector extends View {
    PerkManager perkManager;



    public PassivePerkSelector(PerkManager perkManager) {
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
        WoolBattlePlayer player = WoolBattlePlayerManager.getWoolBattlePlayer(p);

        ItemStack arrow = new ItemStack(Material.ARROW);
        ItemMeta arrowmeta = arrow.getItemMeta();
        arrowmeta.setDisplayName(ChatColor.GOLD + "Zurück");
        arrow.setItemMeta(arrowmeta);


        for (int i = 0; i < PerkManager.passivePerks.size(); i++) {
            Class<? extends PassivePerk> perkClass = PerkManager.passivePerks.get(i);
            PassivePerk perkInstance = PerkManager.createPassivePerkInstance(perkClass, null);

            int slotX = i / 9 + 1;
            int slotY = i % 9 + 1;

            ItemStack item = perkInstance.getItem();

            if(player.getPassivePerk() != null && player.getPassivePerk().getClass().equals(perkClass)) {
                ItemMeta meta = item.getItemMeta();
                meta.addEnchant(Enchantment.AQUA_AFFINITY, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                item.setItemMeta(meta);
            }

            render.slot(slotX, slotY)
                    .withItem(item)
                    .onClick(click -> {
                        if (player.getPassivePerk() == null || !player.getPassivePerk().getClass().equals(perkClass)) {
                            player.setPassivePerk(PerkManager.createPassivePerkInstance(perkClass, player));
                            click.openForPlayer(PassivePerkSelector.class);
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
