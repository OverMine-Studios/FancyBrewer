package dev.risas.automaticbrewer.models.menu;

import dev.risas.automaticbrewer.AutomaticBrewer;
import dev.risas.automaticbrewer.models.Brewer;
import dev.risas.automaticbrewer.models.BrewerState;
import dev.risas.automaticbrewer.models.menu.buttons.*;
import dev.risas.automaticbrewer.utilities.menu.Button;
import dev.risas.automaticbrewer.utilities.menu.Menu;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class BrewerMenu extends Menu {

    private final AutomaticBrewer plugin;
    private final Brewer brewer;

    public BrewerMenu(AutomaticBrewer plugin, Brewer brewer) {
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
