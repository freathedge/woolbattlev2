package net.thevace.woolBattle.perks;

import net.thevace.woolBattle.WoolBattlePlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public abstract class ActivePerk extends Perk {



    public ActivePerk(long cooldown, int preis, WoolBattlePlayer p, String itemName, Material material, String description) {
        super(cooldown, preis, p, itemName, material, description);
    }


    public boolean activate() {
        if (player.getActivePerk1() == this) {
            if (!canUsePerk(player.getActivePerk1LastUsed())) return false;
            if (!hasEnoughMoney()) {
                player.getPlayer().sendMessage("§cDu hast nicht genug Geld für diesen Perk!");
                return false;
            }
            player.setActivePerk1LastUsed(System.currentTimeMillis());
        } else if (player.getActivePerk2() == this) {
            if (!canUsePerk(player.getActivePerk2LastUsed())) return false;
            if (!hasEnoughMoney()) {
                player.getPlayer().sendMessage("§cDu hast nicht genug Geld für diesen Perk!");
                return false;
            }
            player.setActivePerk2LastUsed(System.currentTimeMillis());
        } else if (player.getEnderperle() == this) {
            if(!canUsePerk(player.getEnderperleLastUsed())) return false;
        }

        withdrawWool();
        applyEffect();
        return true;
    }

    private boolean canUsePerk(long lastUsed) {
        if (System.currentTimeMillis() - lastUsed < cooldown * 1000L) {
            player.getPlayer().sendMessage("§cDieses Perk ist noch im Cooldown!");
            return false;
        }
        return true;
    }


    protected abstract void applyEffect();


    protected boolean hasEnoughMoney() {
        return player.getWool() >= preis;
    }


    protected void withdrawWool() {
        player.removeWool(preis);
    }

    public void cancelEvent() {
        player.addWool(preis);
        player.updatePlayerWool();
        if (player.getActivePerk1() == this) {
            player.setActivePerk1LastUsed(0);
        } else if (player.getActivePerk2() == this) {
            player.setActivePerk2LastUsed(0);
        }
    }

}
