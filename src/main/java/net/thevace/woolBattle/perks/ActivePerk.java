package net.thevace.woolBattle.perks;

import net.thevace.woolBattle.WoolBattlePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;


public abstract class ActivePerk extends Perk {


    public ActivePerk(long cooldown, int preis, WoolBattlePlayer p, String itemName, Material material, String description) {
        super(cooldown, preis, p, itemName, material, description);
    }


    public boolean activate() {
        if (player.getActivePerk1() == this) {
            if (!canUsePerk(player.getActivePerk1LastUsed())) return false;
            if (!hasEnoughMoney()) return false;
            player.setActivePerk1LastUsed(Timestamp.valueOf(LocalDateTime.now()));
        } else if (player.getActivePerk2() == this) {
            System.out.println("Active perk 2 used");
            if (!canUsePerk(player.getActivePerk2LastUsed())) return false;
            if (!hasEnoughMoney()) return false;
            player.setActivePerk2LastUsed(Timestamp.valueOf(LocalDateTime.now()));
        }/* else if (player.getEnderperle() == this) {
            System.out.println("enderpearl used");
            if(!canUsePerk(player.getEnderperleLastUsed())) return false;
        }*/

        withdrawWool();
        applyEffect();
        startShowCooldown();
        return true;
    }

    private boolean canUsePerk(Timestamp lastUsed) {
        if (lastUsed == null) return true;
        if (Duration.between(lastUsed.toInstant(), Instant.now()).getSeconds() < cooldown) {
            player.getPlayer().sendMessage("§cDieses Perk ist noch im Cooldown!");
            return false;
        }
        return true;
    }


    protected abstract void applyEffect();


    protected boolean hasEnoughMoney() {
        if (player.getWool() < preis) {
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
//        if (player.getActivePerk1() == this) {
//            player.setActivePerk1LastUsed(0);
//        } else if (player.getActivePerk2() == this) {
//            player.setActivePerk2LastUsed(0);
//        }
    }

    private void startShowCooldown() {
        new BukkitRunnable() {
            int counter = (int) cooldown;

            @Override
            public void run() {
                updatePlayerPerkItem(counter);

                if (counter <= 0) {
                    cancel();
                }
                counter--;
            }
        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("WoolBattle"), 0L, 20L);
    }

    private void updatePlayerPerkItem(int count) {

        ItemStack perkItem = getItem();
        ItemStack item = removeEnchantment(perkItem);
        if(count >= 1) {
            item.setAmount(count);

            if(player.getActivePerk1() == this) {
                player.getPlayer().getInventory().setItem(2, item);
            } else if (player.getActivePerk2() == this) {
                player.getPlayer().getInventory().setItem(3, item);
            }
        } else {
            if(player.getActivePerk1() == this) {
                player.getPlayer().getInventory().setItem(2, perkItem);
            } else if (player.getActivePerk2() == this) {
                player.getPlayer().getInventory().setItem(3, perkItem);
            }
        }
    }

    private ItemStack removeEnchantment(ItemStack item) {
        ItemStack newItem = item.clone();
        ItemMeta meta = newItem.getItemMeta();
        if (meta != null) {
            meta.removeEnchant(Enchantment.AQUA_AFFINITY);
            newItem.setItemMeta(meta);
        }
        return newItem;
    }

}
