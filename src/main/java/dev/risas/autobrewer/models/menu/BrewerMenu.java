package dev.risas.autobrewer.models.menu;

import dev.risas.autobrewer.AutoBrewerPlugin;
import dev.risas.autobrewer.models.Brewer;
import dev.risas.autobrewer.models.BrewerState;
import dev.risas.autobrewer.models.menu.buttons.*;
import dev.risas.autobrewer.servicies.types.ConfigService;
import dev.risas.autobrewer.utilities.ChatUtil;
import dev.risas.autobrewer.utilities.menu.Button;
import dev.risas.autobrewer.utilities.menu.Menu;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class BrewerMenu extends Menu {

    private final AutoBrewerPlugin plugin;
    private final Brewer brewer;

    public BrewerMenu(AutoBrewerPlugin plugin, Brewer brewer) {
        this.plugin = plugin;
        this.brewer = brewer;
    }

    @Override
    public String getTitle(Player player) {
        return ChatUtil.translate(ConfigService.BREWING_MENU_TITLE);
    }

    @Override
    public int getSize() {
        return ConfigService.BREWING_MENU_ROWS * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(ConfigService.BREWING_MENU_BUTTONS_START_SLOT, new BrewingStartButton(plugin, brewer));
        buttons.put(ConfigService.BREWING_MENU_BUTTONS_STORAGE_SLOT, new BrewingStorageButton(plugin, brewer));
        buttons.put(ConfigService.BREWING_MENU_BUTTONS_DISPLAY_SLOT, new BrewingDisplayButton(brewer));
        buttons.put(ConfigService.BREWING_MENU_BUTTONS_TRANSFER_SLOT, new BrewingTransferButton(brewer));

        buttons.put(ConfigService.BREWING_MENU_BUTTONS_BOTTLE_1_SLOT, new BrewingBottleButton(plugin, brewer, 0));
        buttons.put(ConfigService.BREWING_MENU_BUTTONS_BOTTLE_2_SLOT, new BrewingBottleButton(plugin, brewer, 1));
        buttons.put(ConfigService.BREWING_MENU_BUTTONS_BOTTLE_3_SLOT, new BrewingBottleButton(plugin, brewer, 2));
        buttons.put(ConfigService.BREWING_MENU_BUTTONS_INGREDIENT_1_SLOT, new BrewingIngredientButton(plugin, brewer, 0));
        buttons.put(ConfigService.BREWING_MENU_BUTTONS_INGREDIENT_2_SLOT, new BrewingIngredientButton(plugin, brewer, 1));
        buttons.put(ConfigService.BREWING_MENU_BUTTONS_INGREDIENT_3_SLOT, new BrewingIngredientButton(plugin, brewer, 2));
        buttons.put(ConfigService.BREWING_MENU_BUTTONS_INGREDIENT_4_SLOT, new BrewingIngredientButton(plugin, brewer, 3));
        return buttons;
    }

    @Override
    public boolean isCancelPlayerInventory() {
        return false;
    }

    @Override
    public boolean isPlaceholder() {
        return true;
    }

    @Override
    public boolean isAutoUpdate() {
        return brewer.getState() != BrewerState.IDLE;
    }
}
