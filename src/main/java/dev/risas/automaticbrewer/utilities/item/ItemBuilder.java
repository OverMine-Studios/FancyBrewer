package dev.risas.automaticbrewer.utilities.item;

import dev.risas.automaticbrewer.utilities.ChatUtil;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private final ItemStack itemStack;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material, 1);
    }

    public ItemBuilder(String material) {
        this.itemStack = new ItemStack(Material.valueOf(material), 1);
    }

    public ItemBuilder(int material) {
        this.itemStack = new ItemStack(material, 1);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack.clone();
    }

    public ItemBuilder(Material material, int data) {
        this.itemStack = new ItemStack(material, 1, (short) data);
    }

    public ItemBuilder(Material material, int amount, int data) {
        this.itemStack = new ItemStack(material, amount, (short) data);
    }

    public ItemBuilder setName(String name) {
        if (name != null) {
            name = ChatUtil.translate(name);
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(name);
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        if (lore != null) {
            ItemMeta meta = itemStack.getItemMeta();
            meta.setLore(ChatUtil.translate(lore));
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        if (lore != null) {
            ItemMeta meta = itemStack.getItemMeta();
            meta.setLore(ChatUtil.translate(Arrays.asList(lore)));
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setEnchant(boolean enchanted) {
        if (enchanted) {
            ItemMeta meta = itemStack.getItemMeta();
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder setEnchant(boolean enchanted, int level) {
        if (enchanted) {
            ItemMeta meta = itemStack.getItemMeta();
            meta.addEnchant(Enchantment.DURABILITY, level, true);
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder setEnchant(boolean enchanted, Enchantment enchant, int level) {
        if (enchanted) {
            ItemMeta meta = itemStack.getItemMeta();
            meta.addEnchant(enchant, level, true);
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder setData(int dur) {
        itemStack.setDurability((short) dur);
        return this;
    }

    public ItemBuilder setOwner(String owner) {
        if (itemStack.getType() == Material.SKULL_ITEM) {
            SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
            meta.setOwner(owner);
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder setArmorColor(Color color) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        leatherArmorMeta.setColor(color);
        itemStack.setItemMeta(leatherArmorMeta);
        return this;
    }

    public static ItemStack getSkull(String name) {
        return new ItemBuilder(Material.SKULL_ITEM)
                .setData(3)
                .setOwner(name)
                .build();
    }

    public ItemStack build() {
        return itemStack;
    }
}