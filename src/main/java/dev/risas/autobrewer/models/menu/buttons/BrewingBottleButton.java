package dev.risas.autobrewer.models.menu.buttons;

import dev.risas.autobrewer.AutoBrewer;
import dev.risas.autobrewer.models.Brewer;
import dev.risas.autobrewer.models.BrewerState;
import dev.risas.autobrewer.utilities.item.ItemBuilder;
import dev.risas.autobrewer.utilities.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class BrewingBottleButton extends Button {

    private final AutoBrewer plugin;
    private final Brewer brewer;
    private final int index;

    public BrewingBottleButton(AutoBrewer plugin, Brewer brewer, int index) {
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
