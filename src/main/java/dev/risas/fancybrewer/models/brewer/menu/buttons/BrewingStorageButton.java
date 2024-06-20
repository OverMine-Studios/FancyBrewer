package dev.risas.fancybrewer.models.brewer.menu.buttons;

import dev.risas.fancybrewer.FancyBrewerPlugin;
import dev.risas.fancybrewer.models.brewer.Brewer;
import dev.risas.fancybrewer.models.brewer.menu.BrewerStorageMenu;
import dev.risas.fancybrewer.resources.types.ConfigResource;
import dev.risas.fancybrewer.utilities.menu.Button;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class BrewingStorageButton extends Button {

    private final FancyBrewerPlugin plugin;
    private final Brewer brewer;

    @Override
    public ItemStack getButtonItem(Player player) {
        return ConfigResource.BREWING_MENU_BUTTONS_STORAGE;
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playNeutral(player);

        BrewerStorageMenu brewerStorageMenu = new BrewerStorageMenu(plugin, brewer);
        brewerStorageMenu.openMenu(player, plugin);
    }
}
