package net.thevace.woolbattle.perks;

import net.thevace.woolbattle.WoolBattlePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;


public abstract class ActivePerk extends Perk {


    private long lastActivationTime = 0;

    public ActivePerk(long cooldown, int preis, WoolBattlePlayer p, String itemName, Material material, String description) {
        super(cooldown, preis, p, itemName, material, description);

    }

    public boolean activate() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastActivationTime < 100) {
            return false;
        }
        lastActivationTime = currentTime;


        if (player.getActivePerk1() == this) {
            if (!canUsePerk(player.getActivePerk1LastUsed())) return false;
            if (!hasEnoughMoney()) return false;
            player.setActivePerk1LastUsed(Timestamp.from(Instant.now()));
        } else if (player.getActivePerk2() == this) {
            if (!canUsePerk(player.getActivePerk2LastUsed())) return false;
            if (!hasEnoughMoney()) return false;
            player.setActivePerk2LastUsed(Timestamp.from(Instant.now()));
        } else if (player.getEnderperle() == this) {
            if(!canUsePerk(player.getEnderpearlLastUsed())) return false;
            player.setEnderpearlLastUsed(Timestamp.from(Instant.now()));
        }

        withdrawWool();
        applyEffect();
        startShowCooldown();
        return true;
    }

    protected boolean canUsePerk(Timestamp lastUsed) {
        if (lastUsed == null) return true;

        long secondsSinceLastUse = Duration.between(lastUsed.toInstant(), Instant.now()).getSeconds();

        if (secondsSinceLastUse < cooldown) {
            player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
            return false;
        }
        return true;
    }



    protected abstract void applyEffect();


    protected boolean hasEnoughMoney() {
        if (player.getWool() < preis) {
            player.getPlayer().playSound(player.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 1.0f);

        }
        return player.getWool() >= preis;
    }


    protected void withdrawWool() {
        if(preis > 0) {
            player.removeWool(preis);
        }
    }

    public void cancelEvent() {
        player.addWool(preis);
        player.updatePlayerWool();
        if (player.getActivePerk1() == this) {
            player.setActivePerk1LastUsed(Timestamp.valueOf(LocalDateTime.now().minusSeconds(cooldown)));
        } else if (player.getActivePerk2() == this) {
            player.setActivePerk2LastUsed(Timestamp.valueOf(LocalDateTime.now().minusSeconds(cooldown)));
        }
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
            } else if (player.getEnderperle() == this) {
                player.getPlayer().getInventory().setItem(4, item);
            }
        } else {
            if(player.getActivePerk1() == this) {
                player.getPlayer().getInventory().setItem(2, perkItem);
            } else if (player.getActivePerk2() == this) {
                player.getPlayer().getInventory().setItem(3, perkItem);
            } else if (player.getEnderperle() == this) {
                player.getPlayer().getInventory().setItem(4, perkItem);
            }
        }
    }

    public ItemStack addEnchantment(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.addEnchant(Enchantment.AQUA_AFFINITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);
        }
        return item;
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
