package net.thevace.woolBattle.inventorys;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.RenderContext;
import net.thevace.woolBattle.QueueManager;
import net.thevace.woolBattle.WoolBattlePlayerManager;
import net.thevace.woolBattle.WoolBattlePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TeamSelect extends View {

    private final WoolBattlePlayerManager playerManager;
    private final QueueManager queueManager;

    List<WoolBattlePlayer> team1;
    List<WoolBattlePlayer> team2;

    public TeamSelect(WoolBattlePlayerManager playerManager, QueueManager queueManager) {
        this.playerManager = playerManager;
        this.queueManager = queueManager;
    }

    @Override
    public void onInit(ViewConfigBuilder config) {
        config.title("Team Select");
        config.size(3);
        config.cancelOnClick();
    }

    @Override
    public void onFirstRender(RenderContext render) {
        Player p = render.getPlayer();
        WoolBattlePlayer player = playerManager.getWoolBattlePlayer(p);


        team1 = queueManager.getQueue(player).getTeam1();
        team2 = queueManager.getQueue(player).getTeam2();

        ItemStack red = new ItemStack(Material.RED_WOOL);
        ItemStack blue = new ItemStack(Material.BLUE_WOOL);

        ItemMeta redmeta = red.getItemMeta();
        redmeta.setDisplayName(ChatColor.RED + "Rotes Team");
        List<String> redlore = new ArrayList<>();
        if (team1.isEmpty()) {
            redlore.add(ChatColor.GRAY + "Keine Spieler im Team.");
        } else {
            redlore.add(ChatColor.GRAY + "Spieler im Team:");
            for (WoolBattlePlayer woolbattlePlayer : team1) {
                redlore.add(ChatColor.GRAY + "> " + ChatColor.YELLOW + woolbattlePlayer.getPlayer().getName());
            }
        }
        redmeta.setLore(redlore);
        red.setItemMeta(redmeta);


        ItemMeta bluemeta = blue.getItemMeta();
        bluemeta.setDisplayName(ChatColor.BLUE + "Blaues Team");
        List<String> bluelore = new ArrayList<>();
        if (team2.isEmpty()) {
            bluelore.add(ChatColor.GRAY + "Keine Spieler im Team.");
        } else {
            bluelore.add(ChatColor.GRAY + "Spieler im Team:");
            for (WoolBattlePlayer woolbattlePlayer : team2) {
                bluelore.add(ChatColor.GRAY + "> " + ChatColor.YELLOW + woolbattlePlayer.getPlayer().getName());
            }
        }
        bluemeta.setLore(bluelore);
        blue.setItemMeta(bluemeta);

        if(queueManager.getQueue(player).getTeam1().contains(player)) {
            ItemMeta meta = red.getItemMeta();
            meta.addEnchant(Enchantment.AQUA_AFFINITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            red.setItemMeta(meta);
        } else if(queueManager.getQueue(player).getTeam2().contains(player)) {
            ItemMeta meta = blue.getItemMeta();
            meta.addEnchant(Enchantment.AQUA_AFFINITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            blue.setItemMeta(meta);
        }


        render.slot(2, 3)
                .withItem(red)
                .onClick(click -> {
                    changeTeam(click.getPlayer(), queueManager.getQueue(playerManager.getWoolBattlePlayer(click.getPlayer())).getTeam1());
                    click.openForPlayer(TeamSelect.class);
                });
        render.slot(2, 7)
                .withItem(blue)
                .onClick(click -> {
                    changeTeam(click.getPlayer(), queueManager.getQueue(playerManager.getWoolBattlePlayer(click.getPlayer())).getTeam2());
                    click.openForPlayer(TeamSelect.class);
                });
    }


    private void changeTeam(Player player, List<WoolBattlePlayer> targetTeam) {

        WoolBattlePlayer woolbattlePlayer = playerManager.getWoolBattlePlayer(player);

        List<WoolBattlePlayer> team1 = queueManager.getQueue(woolbattlePlayer).getTeam1();
        List<WoolBattlePlayer> team2 = queueManager.getQueue(woolbattlePlayer).getTeam2();


        if(targetTeam.equals(team1)) {
            if (!team1.contains(woolbattlePlayer)) {
                if(team1.size() < queueManager.getQueue(woolbattlePlayer).getTeamSize()) {
                    team2.remove(woolbattlePlayer);
                    team1.add(woolbattlePlayer);
                    woolbattlePlayer.setWoolMaterial(Material.RED_WOOL);
                }
            }
        } else if(targetTeam.equals(team2)) {
            if(!team2.contains(woolbattlePlayer)) {
                if(team2.size() < queueManager.getQueue(woolbattlePlayer).getTeamSize()) {
                    team1.remove(woolbattlePlayer);
                    team2.add(woolbattlePlayer);
                    woolbattlePlayer.setWoolMaterial(Material.BLUE_WOOL);
                }
            }
        }
    }

}
