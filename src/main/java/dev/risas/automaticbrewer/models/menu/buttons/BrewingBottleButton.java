package dev.risas.automaticbrewer.models.menu.buttons;

import dev.risas.automaticbrewer.AutomaticBrewer;
import dev.risas.automaticbrewer.models.Brewer;
import dev.risas.automaticbrewer.models.BrewerState;
import dev.risas.automaticbrewer.utilities.item.ItemBuilder;
import dev.risas.automaticbrewer.utilities.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class BrewingBottleButton extends Button {

    private final AutomaticBrewer plugin;
    private final Brewer brewer;
    private final int index;

    public BrewingBottleButton(AutomaticBrewer plugin, Brewer brewer, int index) {
        this.plugin = plugin;
        this.brewer = brewer;
        this.index = index;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        try {
            return brewer.getBottles().get(index);
        }
        catch (IndexOutOfBoundsException exception) {
            return new ItemBuilder(Material.AIR).build();
        }
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if (brewer.getState() == BrewerState.IDLE && clickType == ClickType.LEFT) {
            ItemStack itemClicked = getButtonItem(player);

            if (itemClicked.getType() != Material.AIR && itemClicked.getType() == Material.GLASS_BOTTLE) {
                ItemStack bottle = brewer.getBottle();

                if (bottle.getType() == Material.GLASS_BOTTLE) {
                    player.getInventory().addItem(bottle);

                    playNeutral(player);

                    brewer.removeBottle();
                    brewer.open(player, plugin);
                }
            }
        }
    }
}
