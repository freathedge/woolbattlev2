package net.thevace.woolBattle;

import net.thevace.woolBattle.perks.ActivePerk;
import net.thevace.woolBattle.perks.PassivePerk;
import org.bukkit.entity.Player;

public class WoolbattlePlayer {
    private Player player;
    int Wool;

    ActivePerk activePerk1;
    ActivePerk activePerk2;
    PassivePerk passivePerk;

    public WoolbattlePlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

}
