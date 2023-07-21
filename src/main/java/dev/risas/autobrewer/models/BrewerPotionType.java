package dev.risas.autobrewer.models;

import com.cryptomorin.xseries.XMaterial;
import dev.risas.autobrewer.utilities.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@Getter
public enum BrewerPotionType {

    INSTANT_HEALTH(
            "Potion of Healing",
            ItemBuilder.createPotion("INSTANT_HEAL", 8229, false,true, false),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), XMaterial.GLISTERING_MELON_SLICE.parseMaterial(), XMaterial.GLOWSTONE_DUST.parseMaterial()),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2)),
    INSTANT_HEALTH_SPLASH(
            "Potion of Healing",
            ItemBuilder.createPotion("INSTANT_HEAL", 16421, false, true, true),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), XMaterial.GLISTERING_MELON_SLICE.parseMaterial(), XMaterial.GLOWSTONE_DUST.parseMaterial(), XMaterial.GUNPOWDER.parseMaterial()),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2, BrewerPotionStage.INGREDIENT_3)),
    SPEED(
            "Potion of Swiftness",
            ItemBuilder.createPotion("SPEED", 8226, false, true, false),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), Material.SUGAR, XMaterial.GLOWSTONE_DUST.parseMaterial()),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2)),
    INVISIBILITY(
            "Potion of Invisibility",
            ItemBuilder.createPotion("INVISIBILITY", 8270, true, false, false),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), Material.GOLDEN_CARROT, Material.FERMENTED_SPIDER_EYE, Material.REDSTONE),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2, BrewerPotionStage.INGREDIENT_3)),
    FIRE_RESISTANCE(
            "Potion of Fire Resistance",
            ItemBuilder.createPotion("FIRE_RESISTANCE", 8259, true, false, false),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), Material.MAGMA_CREAM, Material.REDSTONE),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2)),
    POISON_SPLASH(
            "Potion of Poison",
            ItemBuilder.createPotion("POISON", 16388, true, false, true),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), Material.SPIDER_EYE, Material.REDSTONE, XMaterial.GUNPOWDER.parseMaterial()),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2, BrewerPotionStage.INGREDIENT_3)),
    SLOWNESS_SPLASH(
            "Potion of Slowness",
            ItemBuilder.createPotion("SLOWNESS", 16394, false, false, true),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), Material.SUGAR, Material.FERMENTED_SPIDER_EYE, XMaterial.GUNPOWDER.parseMaterial()),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2, BrewerPotionStage.INGREDIENT_3));

    private final String name;
    private final List<Material> ingredients;
    private final List<BrewerPotionStage> stages;
    private final ItemStack result;

    BrewerPotionType(String name, ItemStack result, List<Material> ingredients, List<BrewerPotionStage> stages) {
        this.name = name;
        this.result = result;
        this.ingredients = ingredients;
        this.stages = stages;
    }
}

