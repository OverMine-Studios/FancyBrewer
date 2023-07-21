package dev.risas.autobrewer.models.menu;

import dev.risas.autobrewer.AutoBrewer;
import dev.risas.autobrewer.models.Brewer;
import dev.risas.autobrewer.models.BrewerState;
import dev.risas.autobrewer.models.menu.buttons.*;
import dev.risas.autobrewer.utilities.menu.Button;
import dev.risas.autobrewer.utilities.menu.Menu;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class BrewerMenu extends Menu {

    private final AutoBrewer plugin;
    private final Brewer brewer;

    public BrewerMenu(AutoBrewer plugin, Brewer brewer) {
        this.plugin = plugin;
        this.brewer = brewer;
    }

    @Override
    public String getTitle(Player player) {
        return plugin.getLanguageFile().getString("brewer-menu.title");
    }

    @Override
    public int getSize() {
        return 9 * 6;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(16, new BrewingStartButton(plugin, brewer));
        buttons.put(25, new BrewingStorageButton());
        buttons.put(31, new BrewingDisplayButton(brewer));
        buttons.put(34, new BrewingTransferButton(brewer));

        buttons.put(12, new BrewingBottleButton(plugin, brewer, 0));
        buttons.put(13, new BrewingBottleButton(plugin, brewer, 1));
        buttons.put(14, new BrewingBottleButton(plugin, brewer, 2));
        buttons.put(10, new BrewingIngredientButton(plugin, brewer, 0));
        buttons.put(19, new BrewingIngredientButton(plugin, brewer, 1));
        buttons.put(28, new BrewingIngredientButton(plugin, brewer, 2));
        buttons.put(37, new BrewingIngredientButton(plugin, brewer, 3));

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
