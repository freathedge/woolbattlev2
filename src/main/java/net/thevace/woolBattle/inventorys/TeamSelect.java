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

    private final WoolBattlePlayerManager playerManager;
    private final QueueManager queueManager;

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

        ItemStack red = new ItemStack(Material.RED_WOOL);
        ItemStack blue = new ItemStack(Material.BLUE_WOOL);

        ItemMeta redmeta = red.getItemMeta();
        redmeta.setDisplayName(ChatColor.RED + "Rotes Team");
        List<String> redlore = new ArrayList<>();
        if (team1.isEmpty()) {
            redlore.add(ChatColor.GRAY + "Keine Spieler im Team.");
        } else {
            redlore.add(ChatColor.GRAY + "Spieler im Team:");
            for (WoolbattlePlayer woolbattlePlayer : team1) {
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
            for (WoolbattlePlayer woolbattlePlayer : team2) {
                bluelore.add(ChatColor.GRAY + "> " + ChatColor.YELLOW + woolbattlePlayer.getPlayer().getName());
            }
        }
        bluemeta.setLore(bluelore);
        blue.setItemMeta(bluemeta);


        render.slot(2, 3)
                .withItem(red)
                .onClick(click -> changeTeam(click.getPlayer(), queueManager.getQueue(playerManager.getWoolBattlePlayer(click.getPlayer())).getTeam1())).closeOnClick();
        render.slot(2, 7)
                .withItem(blue)
                .onClick(click -> changeTeam(click.getPlayer(), queueManager.getQueue(playerManager.getWoolBattlePlayer(click.getPlayer())).getTeam2())).closeOnClick();
    }


    private void changeTeam(Player player, List<WoolbattlePlayer> targetTeam) {

        WoolbattlePlayer woolbattlePlayer = playerManager.getWoolBattlePlayer(player);

        List<WoolbattlePlayer> team1 = queueManager.getQueue(woolbattlePlayer).getTeam1();
        List<WoolbattlePlayer> team2 = queueManager.getQueue(woolbattlePlayer).getTeam2();


        if(targetTeam == team1) {
            if(team1.contains(woolbattlePlayer)) {
                player.sendMessage("Du bist schon in diesem Team!");
            } else {
                if(team1.size() < queueManager.getQueue(woolbattlePlayer).getTeamSize()) {
                    team2.remove(woolbattlePlayer);
                    team1.add(woolbattlePlayer);
                    woolbattlePlayer.setWoolMaterial(Material.RED_WOOL);
                    player.sendMessage(ChatColor.RED + "Du bist dem roten Team beigetreten");
                } else {
                    player.sendMessage(ChatColor.RED + "Dieses Team ist schon voll");
                }


            }
        } else if(targetTeam == team2) {
            if(team2.contains(woolbattlePlayer)) {
                player.sendMessage("Du bist schon in diesem Team!");
            } else {
                if(team2.size() < queueManager.getQueue(woolbattlePlayer).getTeamSize()) {
                    team1.remove(woolbattlePlayer);
                    team2.add(woolbattlePlayer);
                    woolbattlePlayer.setWoolMaterial(Material.BLUE_WOOL);
                    player.sendMessage(ChatColor.BLUE + "Du bist dem blauen Team beigetreten");
                } else {
                    player.sendMessage(ChatColor.RED + "Dieses Team ist schon voll");
                }

            }
        }
    }

}
