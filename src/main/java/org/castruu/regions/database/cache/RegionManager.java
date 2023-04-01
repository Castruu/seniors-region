package org.castruu.regions.database.cache;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.castruu.regions.entities.PlayerData;
import org.castruu.regions.entities.Region;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.List;

public class RegionManager {

    private static final RegionManager INSTANCE = new RegionManager();
    private final Map<UUID, Region> playersCreatingRegion = new HashMap<>();
    private ItemStack magicalWand;

    public static RegionManager get() {
        return INSTANCE;
    }


    public Region getRegionPlayerIsCreating(Player player) {
        Region region = playersCreatingRegion.get(player.getUniqueId());
        if(region == null) {
            region = new Region();
            PlayerData owner = new PlayerData();
            owner.setUuid(player.getUniqueId());
            owner.setName(player.getName());
            region.setOwner(owner);
            playersCreatingRegion.put(player.getUniqueId(), region);
        }
        return region;
    }

    public ItemStack getMagicalWand() {
        if (magicalWand != null) return magicalWand;

        magicalWand = new ItemStack(Material.GOLDEN_SHOVEL, 1);
        ItemMeta itemMeta = magicalWand.getItemMeta();
        Objects.requireNonNull(itemMeta).setDisplayName("§6Region Wand");
        List<String> lore = List.of(
                "",
                "§dRight click to define where the region starts",
                "§bLeft click to define where the region ends"
        );
        itemMeta.setLore(lore);
        itemMeta.addEnchant(Enchantment.LUCK, 1, false);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        magicalWand.setItemMeta(itemMeta);
        return magicalWand;
    }

}
