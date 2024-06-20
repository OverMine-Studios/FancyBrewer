package dev.risas.fancybrewer.models.brewer.menu;

import dev.risas.fancybrewer.FancyBrewerPlugin;
import dev.risas.fancybrewer.models.brewer.Brewer;
import dev.risas.fancybrewer.models.brewer.BrewerState;
import dev.risas.fancybrewer.models.brewer.menu.buttons.*;
import dev.risas.fancybrewer.resources.types.ConfigResource;
import dev.risas.fancybrewer.utilities.ChatUtil;
import dev.risas.fancybrewer.utilities.menu.Button;
import dev.risas.fancybrewer.utilities.menu.Menu;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class BrewerMenu extends Menu {

    private final FancyBrewerPlugin plugin;
    private final Brewer brewer;

    public BrewerMenu(FancyBrewerPlugin plugin, Brewer brewer) {
        this.plugin = plugin;
        this.brewer = brewer;
    }

    @Override
    public String getTitle(Player player) {
        return ChatUtil.translate(ConfigResource.BREWING_MENU_TITLE);
    }

    @Override
    public int getSize() {
        return ConfigResource.BREWING_MENU_ROWS * 9;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(ConfigResource.BREWING_MENU_BUTTONS_START_SLOT, new BrewingStartButton(plugin, brewer));
        buttons.put(ConfigResource.BREWING_MENU_BUTTONS_STORAGE_SLOT, new BrewingStorageButton(plugin, brewer));
        buttons.put(ConfigResource.BREWING_MENU_BUTTONS_DISPLAY_SLOT, new BrewingDisplayButton(brewer));
        buttons.put(ConfigResource.BREWING_MENU_BUTTONS_TRANSFER_SLOT, new BrewingTransferButton(brewer));

        buttons.put(ConfigResource.BREWING_MENU_BUTTONS_BOTTLE_1_SLOT, new BrewingBottleButton(plugin, brewer, 0));
        buttons.put(ConfigResource.BREWING_MENU_BUTTONS_BOTTLE_2_SLOT, new BrewingBottleButton(plugin, brewer, 1));
        buttons.put(ConfigResource.BREWING_MENU_BUTTONS_BOTTLE_3_SLOT, new BrewingBottleButton(plugin, brewer, 2));
        buttons.put(ConfigResource.BREWING_MENU_BUTTONS_INGREDIENT_1_SLOT, new BrewingIngredientButton(plugin, brewer, 0));
        buttons.put(ConfigResource.BREWING_MENU_BUTTONS_INGREDIENT_2_SLOT, new BrewingIngredientButton(plugin, brewer, 1));
        buttons.put(ConfigResource.BREWING_MENU_BUTTONS_INGREDIENT_3_SLOT, new BrewingIngredientButton(plugin, brewer, 2));
        buttons.put(ConfigResource.BREWING_MENU_BUTTONS_INGREDIENT_4_SLOT, new BrewingIngredientButton(plugin, brewer, 3));
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
