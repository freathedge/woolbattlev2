package net.thevace.woolbattle.inventorys;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.RenderContext;
import net.thevace.woolbattle.QueueManager;
import net.thevace.woolbattle.WoolBattlePlayerManager;
import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.WoolBattleTeam;
import org.bukkit.ChatColor;
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

    List<WoolBattleTeam> teams = new ArrayList<>();

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
        WoolBattlePlayer player = WoolBattlePlayerManager.getWoolBattlePlayer(p);

        teams = queueManager.getQueue(player).getTeams();

        for (int i = 0; i < teams.size(); i++) {
            WoolBattleTeam team = teams.get(i);

            ItemStack teamItem = new ItemStack(team.getWoolColorMaterial());
            ItemMeta teamMeta = teamItem.getItemMeta();

            teamMeta.setDisplayName(team.getChatColor() + team.getColorName() + " Team");

            List<String> lore = new ArrayList<>();
            if (team.getPlayers().isEmpty()) {
                lore.add(ChatColor.GRAY + "Keine Spieler im Team.");
            } else {
                lore.add(ChatColor.GRAY + "Spieler im Team:");
                for (WoolBattlePlayer woolbattlePlayer : team.getPlayers()) {
                    lore.add(ChatColor.GRAY + "> " + ChatColor.YELLOW + woolbattlePlayer.getPlayer().getName());
                }
            }
            teamMeta.setLore(lore);
            teamItem.setItemMeta(teamMeta);

            if (team.getPlayers().contains(player)) {
                ItemMeta meta = teamItem.getItemMeta();
                meta.addEnchant(Enchantment.AQUA_AFFINITY, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                teamItem.setItemMeta(meta);
            }

            render.slot(2, 3 + i)
                    .withItem(teamItem)
                    .onClick(click -> {
                        changeTeam(click.getPlayer(), team);
                        click.openForPlayer(TeamSelect.class);
                    });
        }
    }



    private void changeTeam(Player player, WoolBattleTeam targetTeam) {

        WoolBattlePlayer woolbattlePlayer = WoolBattlePlayerManager.getWoolBattlePlayer(player);

        for (WoolBattleTeam team : teams) {
            if(targetTeam.equals(team)) {
                if(!team.getPlayers().contains(woolbattlePlayer)) {
                    team.getPlayers().add(woolbattlePlayer);
                }
            } else {
                team.getPlayers().remove(woolbattlePlayer);
            }
        }

    }

}
