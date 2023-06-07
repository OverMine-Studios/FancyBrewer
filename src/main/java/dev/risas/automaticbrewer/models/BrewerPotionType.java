package dev.risas.automaticbrewer.models;

import dev.risas.automaticbrewer.utilities.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@Getter
public enum BrewerPotionType {

    INSTANT_HEALTH(
            "Potion of Healing",
            new ItemBuilder(Material.POTION)
                    .setData(8229)
                    .build(),
            Arrays.asList(Material.NETHER_STALK, Material.SPECKLED_MELON, Material.GLOWSTONE_DUST),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2)),
    INSTANT_HEALTH_SPLASH(
            "Potion of Healing",
            new ItemBuilder(Material.POTION)
                    .setData(16421)
                    .build(),
            Arrays.asList(Material.NETHER_STALK, Material.SPECKLED_MELON, Material.GLOWSTONE_DUST, Material.SULPHUR),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2, BrewerPotionStage.INGREDIENT_3)),
    SPEED(
            "Potion of Swiftness",
            new ItemBuilder(Material.POTION)
                    .setData(8226)
                    .build(),
            Arrays.asList(Material.NETHER_STALK, Material.SUGAR, Material.GLOWSTONE_DUST),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2)),
    INVISIBILITY(
            "Potion of Invisibility",
            new ItemBuilder(Material.POTION)
                    .setData(8270)
                    .build(),
            Arrays.asList(Material.NETHER_STALK, Material.GOLDEN_CARROT, Material.FERMENTED_SPIDER_EYE, Material.REDSTONE),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2, BrewerPotionStage.INGREDIENT_3)),
    FIRE_RESISTANCE(
            "Potion of Fire Resistance",
            new ItemBuilder(Material.POTION)
                    .setData(8259)
                    .build(),
            Arrays.asList(Material.NETHER_STALK, Material.MAGMA_CREAM, Material.REDSTONE),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2)),
    POISON_SPLASH(
            "Potion of Poison",
            new ItemBuilder(Material.POTION)
                    .setData(16388)
                    .build(),
            Arrays.asList(Material.NETHER_STALK, Material.SPIDER_EYE, Material.REDSTONE, Material.SULPHUR),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2, BrewerPotionStage.INGREDIENT_3)),
    SLOWNESS_SPLASH(
            "Potion of Slowness",
            new ItemBuilder(Material.POTION)
                    .setData(16394)
                    .build(),
            Arrays.asList(Material.NETHER_STALK, Material.SUGAR, Material.FERMENTED_SPIDER_EYE, Material.SULPHUR),
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

