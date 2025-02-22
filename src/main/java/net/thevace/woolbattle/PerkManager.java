package net.thevace.woolbattle;

import net.thevace.woolbattle.perks.*;
import net.thevace.woolbattle.perks.activeperks.*;
import net.thevace.woolbattle.perks.activeperks.Enterhaken;
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
            Stampfer.class,
            LongJump.class,
            TNTPfeil.class,
            RocketJump.class,
            Bohrpfeil.class,
            Pfeilregen.class,
            Harpune.class,
            PassiveEnterhaken.class,
            Aufzug.class,
            Spinne.class,
            Berserker.class,
            Reflektor.class,
            RueckstoßPfeil.class,
            FastArrow.class
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
