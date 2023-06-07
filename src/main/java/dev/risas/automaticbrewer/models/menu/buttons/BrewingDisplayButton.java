package dev.risas.automaticbrewer.models.menu.buttons;

import dev.risas.automaticbrewer.models.Brewer;
import dev.risas.automaticbrewer.models.BrewerPotionStage;
import dev.risas.automaticbrewer.models.BrewerState;
import dev.risas.automaticbrewer.utilities.item.ItemBuilder;
import dev.risas.automaticbrewer.utilities.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BrewingDisplayButton extends Button {

    private final Brewer brewer;

    public BrewingDisplayButton(Brewer brewer) {
        this.brewer = brewer;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        if (brewer.getState() == BrewerState.IDLE) {
            return new ItemBuilder(Material.POTION)
                    .build();
        }
        if (brewer.getCurrentStage() == BrewerPotionStage.AWKWARD) {
            return new ItemBuilder(Material.POTION)
                    .setAmount(3)
                    .build();
        }
        else if (brewer.getCurrentStage() == BrewerPotionStage.INGREDIENT_1) {
            return new ItemBuilder(Material.POTION)
                    .setData(16)
                    .setAmount(3)
                    .build();
        }
        else if (brewer.getCurrentStage() == BrewerPotionStage.INGREDIENT_2) {
            return new ItemBuilder(brewer.getPotionType().getResult())
                    .setAmount(3)
                    .build();
        }
        else if (brewer.getCurrentStage() == BrewerPotionStage.INGREDIENT_3) {
            return new ItemBuilder(brewer.getPotionType().getResult())
                    .setAmount(3)
                    .build();
        }
        return new ItemBuilder(Material.POTION)
                .build();
    }
}
