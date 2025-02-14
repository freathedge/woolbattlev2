package net.thevace.woolBattle.inventorys;

import me.devnatan.inventoryframework.View;
import me.devnatan.inventoryframework.ViewConfigBuilder;
import me.devnatan.inventoryframework.context.RenderContext;
import net.thevace.woolBattle.PerkManager;
import net.thevace.woolBattle.WoolBattlePlayerManager;
import net.thevace.woolBattle.WoolbattlePlayer;
import net.thevace.woolBattle.perks.ActivePerk;
import net.thevace.woolBattle.perks.ActivePerks.*;
import org.bukkit.entity.Player;

public class ActivePerk2Selector extends View {

    WoolBattlePlayerManager playerManager;
    PerkManager perkManager;
    int perkIndex;

    public ActivePerk2Selector(WoolBattlePlayerManager playerManager, PerkManager perkManager) {
        this.playerManager = playerManager;
        this.perkManager = perkManager;
    }

    @Override
    public void onInit(ViewConfigBuilder config) {
        config.title("Perk 2 Selector");
        config.size(3);
        config.cancelOnClick();
    }


    @Override
    public void onFirstRender(RenderContext render) {
        Player p = render.getPlayer();
        WoolbattlePlayer player = playerManager.getWoolBattlePlayer(p);

        int perksPerRow = 9;

        for (int i = 0; i < PerkManager.PERKS.size(); i++) {
            Class<? extends ActivePerk> perkClass = PerkManager.PERKS.get(i);
            ActivePerk perkInstance = PerkManager.createPerkInstance(perkClass, null);

            int slotX = i / perksPerRow + 1;
            int slotY = i % perksPerRow + 1;

            render.slot(slotX, slotY)
                    .withItem(perkInstance.getItem())
                    .onClick(click -> player.setActivePerk2(PerkManager.createPerkInstance(perkClass, player)))
                    .closeOnClick();
        }
    }
}
