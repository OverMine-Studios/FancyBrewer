package dev.risas.fancybrewer.models.brewer;

import com.cryptomorin.xseries.XMaterial;
import dev.risas.fancybrewer.utilities.item.ItemBuilder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter @Setter
public class BrewerPotion {

    private final String id, name;
    private final List<Material> ingredients;
    private List<BrewerPotionStage> stages;
    private final ItemStack result;

    public BrewerPotion(String id, ConfigurationSection section) {
        this.id = id;
        this.name = section.getString("name");
        this.ingredients = new ArrayList<>();

        for (String ingredient : section.getStringList("ingredients")) {
            Optional<XMaterial> optional = XMaterial.matchXMaterial(ingredient);

            if (!optional.isPresent()) {
                throw new IllegalArgumentException("Invalid material: " + ingredient);
            }

            ingredients.add(optional.get().parseMaterial());
        }

        this.stages = new ArrayList<>();

        for (int i = 0; i < BrewerPotionStage.values().length; i++) {
            BrewerPotionStage stage = BrewerPotionStage.values()[i];
            if (ingredients.size() == i) break;

            stages.add(stage);
        }

        this.result = ItemBuilder.createPotion(
                section.getString("potion-item.type"),
                section.getInt("potion-item.data"),
                section.getBoolean("potion-item.extended"),
                section.getBoolean("potion-item.upgraded"),
                section.getBoolean("potion-item.splash"));
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
