package net.thevace.woolBattle.inventorys;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.ViewFrame;
import me.devnatan.inventoryframework.context.OpenContext;
import me.devnatan.inventoryframework.state.MutableIntState;
import me.devnatan.inventoryframework.context.RenderContext;
import net.thevace.woolBattle.QueueManager;
import net.thevace.woolBattle.WoolBattlePlayerManager;
import net.thevace.woolBattle.WoolBattleQueue;
import net.thevace.woolBattle.WoolbattlePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TeamSelect extends View {

    WoolBattlePlayerManager playerManager;
    private QueueManager queueManager;

    List<WoolbattlePlayer> team1;
    List<WoolbattlePlayer> team2;

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
        WoolbattlePlayer player = playerManager.getWoolBattlePlayer(p);

        team1 = queueManager.getQueue(player).getTeam1();
        team2 = queueManager.getQueue(player).getTeam2();

        p.sendMessage("Team 1: ");
        for(WoolbattlePlayer woolbattlePlayer : team1) {
            p.sendMessage(woolbattlePlayer.getPlayer().getName());
        }
        p.sendMessage("Team 2: ");
        for(WoolbattlePlayer woolbattlePlayer : team2) {
            p.sendMessage(woolbattlePlayer.getPlayer().getName());
        }

        ItemStack red = new ItemStack(Material.RED_WOOL);
        ItemStack blue = new ItemStack(Material.BLUE_WOOL);
        p.sendMessage("[LOG] Created new ItemStacks");

        ItemMeta redmeta = red.getItemMeta();
        redmeta.setDisplayName(ChatColor.RED + "" + team1.size());
        p.sendMessage("[LOG] Set red items displayname");
        List<String> redlore = new ArrayList<>();
        if (team1.isEmpty()) {
            p.sendMessage("team 1 is  empty");
            redlore.add(ChatColor.GRAY + "Keine Spieler im Team.");
        } else {
            redlore.add(ChatColor.GRAY + "Spieler im Team:");
            for (WoolbattlePlayer woolbattlePlayer : team1) {
                redlore.add(ChatColor.GRAY + "> " + ChatColor.YELLOW + woolbattlePlayer.getPlayer().getName());
            }
        }
        redmeta.setLore(redlore);
        p.sendMessage("[LOG] Set Red items lore");
        red.setItemMeta(redmeta);


        ItemMeta bluemeta = blue.getItemMeta();
        bluemeta.setDisplayName(ChatColor.BLUE + "" + team2.size());
        p.sendMessage("[LOG] Set blue items displayname");
        List<String> bluelore = new ArrayList<>();
        if (team2.isEmpty()) {
            bluelore.add(ChatColor.GRAY + "Keine Spieler im Team.");
        } else {
            bluelore.add(ChatColor.GRAY + "Spieler im Team:");
            for (WoolbattlePlayer woolbattlePlayer : team2) {
                bluelore.add(ChatColor.GRAY + "> " + ChatColor.YELLOW + woolbattlePlayer.getPlayer().getName());
            }
        }
        bluemeta.setLore(bluelore);
        p.sendMessage("[LOG] Set blue items lore");
        blue.setItemMeta(bluemeta);

        p.sendMessage(redlore.toString());
        p.sendMessage(bluelore.toString());


        render.slot(2, 3)
                .withItem(red)
                .onClick(click -> changeTeam(click.getPlayer(), queueManager.getQueue(playerManager.getWoolBattlePlayer(click.getPlayer())).getTeam1())).closeOnClick();
        render.slot(2, 7, blue)
                .withItem(blue)
                .onClick(click -> changeTeam(click.getPlayer(), queueManager.getQueue(playerManager.getWoolBattlePlayer(click.getPlayer())).getTeam2())).closeOnClick();
        if(team2.size() == 1) {
            render.slot(2, 3, new ItemStack(Material.DIAMOND));
        }
    }


    private void changeTeam(Player player, List<WoolbattlePlayer> targetTeam) {

        WoolbattlePlayer woolbattlePlayer = playerManager.getWoolBattlePlayer(player);

        List<WoolbattlePlayer> team1 = queueManager.getQueue(woolbattlePlayer).getTeam1();
        List<WoolbattlePlayer> team2 = queueManager.getQueue(woolbattlePlayer).getTeam2();

        queueManager.getQueue(woolbattlePlayer).removeFromTeam(woolbattlePlayer);

        if(targetTeam == team1) {
            if(team1.contains(woolbattlePlayer)) {
                player.sendMessage("Du bist schon in diesem Team!");
            } else {
                team2.remove(woolbattlePlayer);
                team1.add(woolbattlePlayer);
                player.sendMessage(ChatColor.RED + "Du bist dem Team 1 beigetreten");
            }
        } else if(targetTeam == team2) {
            if(team2.contains(woolbattlePlayer)) {
                player.sendMessage("Du bist schon in diesem Team!");
            } else {
                team1.remove(woolbattlePlayer);
                team2.add(woolbattlePlayer);
                player.sendMessage(ChatColor.BLUE + "Du bist dem Team 2 beigetreten");
            }
        }

        player.sendMessage("Team 1: ");
        for(WoolbattlePlayer wbp : team1) {
            player.sendMessage(wbp.getPlayer().getName());
        }
        player.sendMessage("Team 2: ");
        for(WoolbattlePlayer wbp : team2) {
            player.sendMessage(wbp.getPlayer().getName());
        }

        queueManager.getQueue(woolbattlePlayer).setTeam1(team1);
        queueManager.getQueue(woolbattlePlayer).setTeam2(team2);
    }

}
