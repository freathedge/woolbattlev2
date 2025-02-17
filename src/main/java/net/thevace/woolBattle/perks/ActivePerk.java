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
        System.out.println("Perk activated" + this);
        System.out.println("Perk aktiviert: " + itemName);
        System.out.println("Letztes Mal benutzt: " + player.getActivePerk1LastUsed());
        System.out.println("Cooldown: " + cooldown);


        if (player.getActivePerk1() == this) {
            System.out.println("Active perk 1 used");
            if (!canUsePerk(player.getActivePerk1LastUsed())) return false;
            if (!hasEnoughMoney()) return false;
            player.setActivePerk1LastUsed(System.currentTimeMillis());
        } else if (player.getActivePerk2() == this) {
            System.out.println("Active perk 2 used");
            if (!canUsePerk(player.getActivePerk2LastUsed())) return false;
            if (!hasEnoughMoney()) return false;
            player.setActivePerk2LastUsed(System.currentTimeMillis());
        } else if (player.getEnderperle() == this) {
            System.out.println("enderpearl used");
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
        if(player.getWool() < preis) {
            player.getPlayer().sendMessage("§cDu hast nicht genug Geld für diesen Perk!");
        }
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
