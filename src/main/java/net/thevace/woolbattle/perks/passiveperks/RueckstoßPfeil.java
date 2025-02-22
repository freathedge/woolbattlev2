package net.thevace.woolbattle.perks.passiveperks;

import net.thevace.woolbattle.WoolBattlePlayer;
import net.thevace.woolbattle.perks.PassivePerk;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class RueckstoßPfeil extends PassivePerk {

    public RueckstoßPfeil(WoolBattlePlayer p) {
//        ItemStack arrow = new ItemStack(Material.TIPPED_ARROW);
//        PotionMeta arrowmeta = (PotionMeta) arrow.getItemMeta();
//        arrowmeta.addCustomEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 200, 1), true);
        super(3, p, ChatColor.GOLD + "Rückstoß-Pfeil", Material.ARROW, "Alle 10 Pfeile wird ein Pfeil geschossen, welcher 15% mehr Rückstoß erteilt");
    }
}
