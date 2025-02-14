package net.thevace.woolBattle;

import net.thevace.woolBattle.perks.*;
import net.thevace.woolBattle.perks.ActivePerks.*;

import java.util.List;

public class PerkManager {

    public static final List<Class<? extends ActivePerk>> PERKS = List.of(
            Pod.class,
            Enterhaken.class,
            Tauscher.class,
            Rettungskapsel.class,
            Mine.class,
            Rettungsplattform.class,
            Blink.class,
            Feder.class,
            Brueckenbauer.class
    );

    public static ActivePerk createPerkInstance(Class<? extends ActivePerk> perkClass, WoolbattlePlayer player) {
        try {
            return perkClass.getConstructor(WoolbattlePlayer.class).newInstance(player);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
