package net.thevace.woolBattle.perks;

import net.thevace.woolBattle.WoolbattlePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;


public abstract class ActivePerk {
    protected final int preis;
    protected final long cooldown;
    protected final WoolbattlePlayer player;

    String itemName;
    Material material;

    ItemStack item;


    public ActivePerk(long cooldown, int preis, WoolbattlePlayer p, String itemName, Material material) {
        this.cooldown = cooldown;
        this.preis = preis;
        this.player  = p;
        this.itemName = itemName;
        this.material = material;

        item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(itemName);
        item.setItemMeta(meta);
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
        } else {
            player.getPlayer().sendMessage("§cDieses Perk ist nicht aktiv!");
            return false;
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


    public void addItem() {
        Player p = player.getPlayer();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(itemName);
        item.setItemMeta(meta);
        p.getInventory().addItem(item);
    }

    public ItemStack getItem() {
        return item;
    }

    protected abstract void applyEffect();


    protected boolean hasEnoughMoney() {
        return player.getWool() >= preis;
    }


    protected void withdrawWool() {
        player.removeWool(preis);
    }

}
