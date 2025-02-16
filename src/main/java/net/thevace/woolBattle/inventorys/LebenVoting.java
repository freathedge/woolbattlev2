package net.thevace.woolBattle.inventorys;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.RenderContext;
import net.thevace.woolBattle.QueueManager;
import net.thevace.woolBattle.WoolBattlePlayerManager;
import net.thevace.woolBattle.WoolBattleQueue;
import net.thevace.woolBattle.WoolbattlePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;


public class LebenVoting extends View {

    private WoolBattlePlayerManager playerManager;
    private QueueManager queueManager;

    public LebenVoting(WoolBattlePlayerManager playerManager, QueueManager queueManager) {
        this.playerManager = playerManager;
        this.queueManager = queueManager;
    }

    @Override
    public void onInit(ViewConfigBuilder config) {
        config.title(ChatColor.RED + "Leben");
        config.size(3);
        config.cancelOnClick();
    }

    @Override
    public void onFirstRender(RenderContext render) {

        ItemStack three = new ItemStack(Material.NAME_TAG);
        ItemMeta threeMeta = three.getItemMeta();
        threeMeta.setDisplayName(ChatColor.RED + "3");
        three.setItemMeta(threeMeta);

        ItemStack five = new ItemStack(Material.NAME_TAG);
        ItemMeta fiveMeta = five.getItemMeta();
        fiveMeta.setDisplayName(ChatColor.RED + "5");
        five.setItemMeta(fiveMeta);

        ItemStack ten = new ItemStack(Material.NAME_TAG);
        ItemMeta tenMeta = ten.getItemMeta();
        tenMeta.setDisplayName(ChatColor.RED + "10");
        ten.setItemMeta(tenMeta);

        ItemStack twenty = new ItemStack(Material.NAME_TAG);
        ItemMeta twentyMeta = twenty.getItemMeta();
        twentyMeta.setDisplayName(ChatColor.RED + "20");
        twenty.setItemMeta(twentyMeta);

        ItemStack thirty = new ItemStack(Material.NAME_TAG);
        ItemMeta thirtyMeta = thirty.getItemMeta();
        thirtyMeta.setDisplayName(ChatColor.RED + "30");
        thirty.setItemMeta(thirtyMeta);

        WoolbattlePlayer player = playerManager.getWoolBattlePlayer(render.getPlayer());
        Integer currentVote = queueManager.getQueue(player).getLifeVoting().get(player);

        if(currentVote != null) {
            if (currentVote == 3) {
                ItemMeta meta = three.getItemMeta();
                meta.addEnchant(Enchantment.AQUA_AFFINITY, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                three.setItemMeta(meta);
            } else if (currentVote == 5) {
                ItemMeta meta = five.getItemMeta();
                meta.addEnchant(Enchantment.AQUA_AFFINITY, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                five.setItemMeta(meta);
            } else if (currentVote == 10) {
                ItemMeta meta = ten.getItemMeta();
                meta.addEnchant(Enchantment.AQUA_AFFINITY, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                ten.setItemMeta(meta);
            } else if (currentVote == 20) {
                ItemMeta meta = twenty.getItemMeta();
                meta.addEnchant(Enchantment.AQUA_AFFINITY, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                twenty.setItemMeta(meta);
            } else if (currentVote == 30) {
                ItemMeta meta = thirty.getItemMeta();
                meta.addEnchant(Enchantment.AQUA_AFFINITY, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                thirty.setItemMeta(meta);
            }
        }


        render.slot(2, 1)
                .withItem(three)
                .onClick(click -> {
                    setLives(click.getPlayer(), 3);
                    click.openForPlayer(LebenVoting.class);
                });
        render.slot(2, 3)
                .withItem(five)
                .onClick(click -> {
                    setLives(click.getPlayer(), 5);
                    click.openForPlayer(LebenVoting.class);
                });
        render.slot(2, 5)
                .withItem(ten)
                .onClick(click -> {
                    setLives(click.getPlayer(), 10);
                    click.openForPlayer(LebenVoting.class);
                });
        render.slot(2, 7)
                .withItem(twenty)
                .onClick(click -> {
                    setLives(click.getPlayer(), 20);
                    click.openForPlayer(LebenVoting.class);
                });
        render.slot(2, 9)
                .withItem(thirty)
                .onClick(click -> {
                    setLives(click.getPlayer(), 30);
                    click.openForPlayer(LebenVoting.class);
                });

    }

    public void setLives(Player p, int lives) {
        WoolbattlePlayer player = playerManager.getWoolBattlePlayer(p);
        queueManager.getQueue(player).setLifeVoting(player, lives);
    }
}
