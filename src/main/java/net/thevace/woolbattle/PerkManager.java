package net.thevace.woolbattle;

import net.thevace.woolbattle.perks.*;
import net.thevace.woolbattle.perks.activeperks.*;
import net.thevace.woolbattle.perks.passiveperks.*;

import java.util.List;

public class PerkManager {

    public static final List<Class<? extends ActivePerk>> activePerks = List.of(
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

    public static final List<Class<? extends PassivePerk>> passivePerks = List.of(
            FreezeArrow.class,
            Naehkasten.class,
            Schurke.class,
            LongJump.class,
            RocketJump.class,
            Pfeilregen.class,
            RueckstossPfeil.class,
            FastArrow.class,
            Reflektor.class,
            TNTPfeil.class,
            Bohrpfeil.class,
            Stampfer.class
    );


    public static ActivePerk createActivePerkInstance(Class<? extends ActivePerk> perkClass, WoolBattlePlayer player) {
        try {
            return perkClass.getConstructor(WoolBattlePlayer.class).newInstance(player);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static PassivePerk createPassivePerkInstance(Class<? extends PassivePerk> perkClass, WoolBattlePlayer player) {
        try {
            return perkClass.getConstructor(WoolBattlePlayer.class).newInstance(player);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
