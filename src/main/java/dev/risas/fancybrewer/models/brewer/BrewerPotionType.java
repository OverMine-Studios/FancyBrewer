package dev.risas.fancybrewer.models.brewer;

import com.cryptomorin.xseries.XMaterial;
import dev.risas.fancybrewer.utilities.item.ItemBuilder;
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
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), XMaterial.GLISTERING_MELON_SLICE.parseMaterial(), Material.GLOWSTONE_DUST),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2)),
    INSTANT_HEALTH_SPLASH(
            "Potion of Healing",
            ItemBuilder.createPotion("INSTANT_HEAL", 16421, false, true, true),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), XMaterial.GLISTERING_MELON_SLICE.parseMaterial(), Material.GLOWSTONE_DUST, XMaterial.GUNPOWDER.parseMaterial()),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2, BrewerPotionStage.INGREDIENT_3)),
    INSTANT_HEALTH_SPLASH_OTHER(
            "Potion of Healing",
            ItemBuilder.createPotion("INSTANT_HEAL", 16421, false, true, true),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), XMaterial.GLISTERING_MELON_SLICE.parseMaterial(), XMaterial.GUNPOWDER.parseMaterial(), Material.GLOWSTONE_DUST),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2, BrewerPotionStage.INGREDIENT_3)),
    SPEED_I(
            "Potion of Swiftness",
            ItemBuilder.createPotion("SPEED", 8194, false, false, false),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), Material.SUGAR),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1)),
    SPEED_I_EXTENDED(
            "Potion of Swiftness",
            ItemBuilder.createPotion("SPEED", 8258, true, false, false),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), Material.SUGAR, Material.REDSTONE),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2)),
    SPEED_II(
            "Potion of Swiftness",
            ItemBuilder.createPotion("SPEED", 8226, false, true, false),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), Material.SUGAR, Material.GLOWSTONE_DUST),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2)),
    SPEED_I_SPLASH(
            "Potion of Swiftness",
            ItemBuilder.createPotion("SPEED", 16386, false, false, true),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), Material.SUGAR, XMaterial.GUNPOWDER.parseMaterial()),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2)),
    SPEED_I_SPLASH_EXTENDED(
            "Potion of Swiftness",
            ItemBuilder.createPotion("SPEED", 16450, true, false, true),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), Material.SUGAR, Material.REDSTONE, XMaterial.GUNPOWDER.parseMaterial()),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2, BrewerPotionStage.INGREDIENT_3)),
    SPEED_II_SPLASH(
            "Potion of Swiftness",
            ItemBuilder.createPotion("SPEED", 16418, false, true, true),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), Material.SUGAR, Material.GLOWSTONE_DUST, XMaterial.GUNPOWDER.parseMaterial()),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2, BrewerPotionStage.INGREDIENT_3)),
    STRENGTH_I(
            "Potion of Strength",
            ItemBuilder.createPotion("STRENGTH", 8201, false, false, false),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), Material.BLAZE_POWDER),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1)),
    STRENGTH_I_EXTENDED(
            "Potion of Strength",
            ItemBuilder.createPotion("STRENGTH", 8265, true, false, false),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), Material.BLAZE_POWDER, Material.REDSTONE),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2)),
    STRENGTH_II(
            "Potion of Strength",
            ItemBuilder.createPotion("STRENGTH", 8233, false, true, false),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), Material.BLAZE_POWDER, Material.GLOWSTONE_DUST),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2)),
    STRENGTH_I_SPLASH(
            "Potion of Strength",
            ItemBuilder.createPotion("STRENGTH", 16393, false, false, true),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), Material.BLAZE_POWDER, XMaterial.GUNPOWDER.parseMaterial()),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2)),
    STRENGTH_I_SPLASH_EXTENDED(
            "Potion of Strength",
            ItemBuilder.createPotion("STRENGTH", 16457, true, false, true),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), Material.BLAZE_POWDER, Material.REDSTONE, XMaterial.GUNPOWDER.parseMaterial()),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2, BrewerPotionStage.INGREDIENT_3)),
    STRENGTH_II_SPLASH(
            "Potion of Strength",
            ItemBuilder.createPotion("STRENGTH", 16425, false, true, true),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), Material.BLAZE_POWDER, Material.GLOWSTONE_DUST, XMaterial.GUNPOWDER.parseMaterial()),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2, BrewerPotionStage.INGREDIENT_3)),
    INVISIBILITY(
            "Potion of Invisibility",
            ItemBuilder.createPotion("INVISIBILITY", 8238, false, false, false),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), Material.GOLDEN_CARROT, Material.FERMENTED_SPIDER_EYE),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2)),
    INVISIBILITY_EXTENDED(
            "Potion of Invisibility",
            ItemBuilder.createPotion("INVISIBILITY", 8270, true, false, false),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), Material.GOLDEN_CARROT, Material.FERMENTED_SPIDER_EYE, Material.REDSTONE),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2, BrewerPotionStage.INGREDIENT_3)),
    FIRE_RESISTANCE(
            "Potion of Fire Resistance",
            ItemBuilder.createPotion("FIRE_RESISTANCE", 8227, false, false, false),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), Material.MAGMA_CREAM),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1)),
    FIRE_RESISTANCE_EXTENDED(
            "Potion of Fire Resistance",
            ItemBuilder.createPotion("FIRE_RESISTANCE", 8259, true, false, false),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), Material.MAGMA_CREAM, Material.REDSTONE),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2)),
    FIRE_RESISTANCE_SPLASH(
            "Potion of Fire Resistance",
            ItemBuilder.createPotion("FIRE_RESISTANCE", 16419, false, false, true),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), Material.MAGMA_CREAM, XMaterial.GUNPOWDER.parseMaterial()),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2)),
    FIRE_RESISTANCE_SPLASH_EXTENDED(
            "Potion of Fire Resistance",
            ItemBuilder.createPotion("FIRE_RESISTANCE", 16451, true, false, true),
            Arrays.asList(XMaterial.NETHER_WART.parseMaterial(), Material.MAGMA_CREAM, Material.REDSTONE, XMaterial.GUNPOWDER.parseMaterial()),
            Arrays.asList(BrewerPotionStage.AWKWARD, BrewerPotionStage.INGREDIENT_1, BrewerPotionStage.INGREDIENT_2, BrewerPotionStage.INGREDIENT_3)),
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

    public String getPlaceholder(int slot) {
        Material ingredientSlot = ingredients.get(slot);

        if (ingredientSlot != null) {
            if (ingredientSlot.equals(Material.GLOWSTONE_DUST)) {
                return "II";
            }
            else if (ingredientSlot.equals(Material.REDSTONE)) {
                return "Extended";
            }
            else if (ingredientSlot.equals(XMaterial.GUNPOWDER.parseMaterial())) {
                return "Splash";
            }
            else if (ingredientSlot.equals(Material.FERMENTED_SPIDER_EYE)) {
                return "Invisibility";
            }
        }
        return "";
    }
}

