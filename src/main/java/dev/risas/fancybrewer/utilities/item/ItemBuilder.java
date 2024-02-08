package dev.risas.fancybrewer.utilities.item;

import com.cryptomorin.xseries.XMaterial;
import dev.risas.fancybrewer.utilities.BukkitUtil;
import dev.risas.fancybrewer.utilities.ChatUtil;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material) {
        this(XMaterial.matchXMaterial(material).parseMaterial(), 1, 0);
    }

    public ItemBuilder(Material material, int amount) {
        this(XMaterial.matchXMaterial(material).parseMaterial(), amount, 0);
    }

    public ItemBuilder(String material) {
        String materialName = material.toUpperCase();
        Optional<XMaterial> xMaterial = XMaterial.matchXMaterial(materialName);

        if (!xMaterial.isPresent()) {
            ChatUtil.logger("&c[ItemBuilder] Invalid material: " + materialName + ".");
            this.itemStack = new ItemStack(Material.STONE);
            this.itemMeta = itemStack.getItemMeta();
            return;
        }

        this.itemStack = new ItemStack(Objects.requireNonNull(xMaterial.get().parseMaterial()), 1, (short) 0);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int amount, int data) {
        this.itemStack = new ItemStack(Objects.requireNonNull(XMaterial.matchXMaterial(material).parseMaterial()), amount, (short) data);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setMaterial(Material material) {
        this.itemStack.setType(material);
        return this;
    }

    public ItemBuilder setData(int data) {
        this.itemStack.setDurability((short) data);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder addAmount(int amount) {
        this.itemStack.setAmount(this.itemStack.getAmount() + amount);
        return this;
    }

    public ItemBuilder setName(String name) {
        this.itemMeta.setDisplayName(ChatUtil.translate(name));
        return this;
    }

    public ItemBuilder setSkullOwner(String ownerOrValue) {
        if (ownerOrValue == null || ownerOrValue.isEmpty()) return this;
        SkullMeta meta = (SkullMeta) this.itemMeta;
        meta.setOwner(ownerOrValue);
        return this;
    }

    public ItemBuilder setArmorColor(Color color) {
        if (color == null) return this;

        if (!itemStack.getType().name().startsWith("LEATHER")) {
            throw new IllegalArgumentException("setArmorColor() only applicable for LeatherArmor");
        }

        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) this.itemMeta;

        if (leatherArmorMeta != null) {
            leatherArmorMeta.setColor(color);
            itemStack.setItemMeta(leatherArmorMeta);
        }

        return this;
    }

    public ItemBuilder setPotionData(PotionType potionType) {
        PotionData potionData = new PotionData(potionType);
        PotionMeta potionMeta = (PotionMeta) this.itemMeta;
        potionMeta.setBasePotionData(potionData);
        return this;
    }

    public ItemBuilder setPotionData(PotionType potionType, boolean extended, boolean upgraded) {
        PotionData potionData = new PotionData(potionType, extended, upgraded);
        PotionMeta potionMeta = (PotionMeta) this.itemMeta;
        potionMeta.setBasePotionData(potionData);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        if (lore == null || lore.isEmpty()) return this;
        this.itemMeta.setLore(ChatUtil.translate(lore));
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        if (lore == null) return this;
        this.itemMeta.setLore(ChatUtil.translate(Arrays.asList(lore)));
        return this;
    }

    public ItemBuilder addLoreLine(String line) {
        this.itemMeta.getLore().add(ChatUtil.translate(line));
        return this;
    }

    public ItemBuilder setEnchanted(boolean enchanted) {
        if (enchanted) {
            this.itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            this.itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        return this;
    }

    public ItemBuilder addEnchantment() {
        this.itemStack.addEnchantment(Enchantment.DURABILITY, 1);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        this.itemStack.addEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public static ItemStack createPotion(String potionType, int data, boolean extended, boolean upgraded, boolean splash) {
        ItemBuilder itemBuilder = new ItemBuilder(Material.POTION);

        if (BukkitUtil.SERVER_VERSION_INT >= 13) {
            if (splash) itemBuilder.setMaterial(Material.SPLASH_POTION);
            itemBuilder.setPotionData(PotionType.valueOf(potionType), extended, upgraded);
        }
        else {
            itemBuilder.setData(data);
        }

        return itemBuilder.build();
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }
}