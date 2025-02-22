package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FastArrow extends PassivePerk {

    public FastArrow(WoolBattlePlayer p) {
//        ItemStack arrow = new ItemStack(Material.TIPPED_ARROW);
//        PotionMeta arrowmeta = (PotionMeta) arrow.getItemMeta();
//        arrowmeta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1), true);
        super(3, p, ChatColor.GOLD + "Rückstoß-Pfeil", Material.ARROW, "Geschossene Pfeile fliegen um 20% schneller");
    }
}
