package net.thevace.woolBattle;

import net.thevace.woolBattle.perks.*;
import net.thevace.woolBattle.perks.ActivePerks.*;

import java.util.List;

public class PerkManager {

    public static final List<Class<? extends ActivePerk>> PERKS = List.of(
            Pod.class,
            Enterhaken.class,
            Uhr.class,
            Tauscher.class,
            Rettungskapsel.class,
            Mine.class,
            Pfeilbombe.class,
            Rettungsplattform.class,
            Woolbomb.class,
            Blink.class,
            Feder.class,
            Brueckenbauer.class,
            Schutzschild.class,
            Freeze.class,
            Minigun.class,
            Tuermchenbauer.class,
            Greifer.class
    );


    public static ActivePerk createPerkInstance(Class<? extends ActivePerk> perkClass, WoolBattlePlayer player) {
        try {
            return perkClass.getConstructor(WoolBattlePlayer.class).newInstance(player);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
