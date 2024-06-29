package dev.risas.fancybrewer.models.brewer.menu.buttons;

import dev.risas.fancybrewer.FancyBrewerPlugin;
import dev.risas.fancybrewer.models.brewer.Brewer;
import dev.risas.fancybrewer.models.brewer.BrewerState;
import dev.risas.fancybrewer.utilities.item.ItemBuilder;
import dev.risas.fancybrewer.utilities.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class BrewingIngredientButton extends Button {

    private final FancyBrewerPlugin plugin;
    private final Brewer brewer;
    private final int index;

    public BrewingIngredientButton(FancyBrewerPlugin plugin, Brewer brewer, int index) {
        this.plugin = plugin;
        this.brewer = brewer;
        this.index = index;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        try {
            return brewer.getIngredients().get(index);
        }
        catch (IndexOutOfBoundsException exception) {
            return new ItemBuilder(Material.AIR).build();
        }
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if (brewer.getState() == BrewerState.IDLE && clickType == ClickType.LEFT) {
            ItemStack itemClicked = getButtonItem(player);
            if (itemClicked.getType() == Material.AIR) return;

            ItemStack ingredient = brewer.getIngredient();

            if (ingredient.getType() != Material.AIR && brewer.getIngredients().contains(ingredient)) {
                playNeutral(player);

                brewer.removeIngredient(player, ingredient);
                brewer.open(player, plugin);
            }
        }
    }
}
