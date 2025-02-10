package net.thevace.woolBattle.inventorys;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.RenderContext;

import net.thevace.woolBattle.WoolBattlePlayerManager;
import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import net.thevace.woolBattle.perks.Enterhaken;
import net.thevace.woolBattle.perks.Pod;
import net.thevace.woolBattle.perks.Tauscher;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ActivePerk1Selector extends View {

    WoolBattlePlayerManager playerManager;
    int perkIndex;

    public ActivePerk1Selector(WoolBattlePlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public void onInit(ViewConfigBuilder config) {
        config.title("Perk 1 Selector");
        config.size(3);
        config.cancelOnClick();
    }


    @Override
    public void onFirstRender(RenderContext render) {
        Player p = render.getPlayer();
        WoolbattlePlayer player = playerManager.getWoolBattlePlayer(p);

        Pod pod = new Pod(null);
        Enterhaken enterhaken = new Enterhaken(null);
        Tauscher tauscher = new Tauscher(null);

        render.slot(1, 1)
                .withItem(pod.getItem())
                .onClick(click -> player.setActivePerk1(new Pod(player))).closeOnClick();
        render.slot(1, 2)
                .withItem(enterhaken.getItem())
                .onClick(click -> player.setActivePerk1(new Enterhaken(player))).closeOnClick();
        render.slot(1, 3)
                .withItem(tauscher.getItem())
                .onClick(click -> player.setActivePerk1(new Tauscher(player))).closeOnClick();
    }
}
