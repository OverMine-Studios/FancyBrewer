package dev.risas.autobrewer.models.menu.buttons;

import dev.risas.autobrewer.AutoBrewerPlugin;
import dev.risas.autobrewer.models.Brewer;
import dev.risas.autobrewer.models.menu.BrewerStorageMenu;
import dev.risas.autobrewer.servicies.types.ConfigService;
import dev.risas.autobrewer.utilities.menu.Button;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class BrewingStorageButton extends Button {

    private final AutoBrewerPlugin plugin;
    private final Brewer brewer;

    @Override
    public ItemStack getButtonItem(Player player) {
        return ConfigService.BREWING_MENU_BUTTONS_STORAGE;
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playNeutral(player);

        BrewerStorageMenu brewerStorageMenu = new BrewerStorageMenu(plugin, brewer);
        brewerStorageMenu.openMenu(player, plugin);
    }
}
