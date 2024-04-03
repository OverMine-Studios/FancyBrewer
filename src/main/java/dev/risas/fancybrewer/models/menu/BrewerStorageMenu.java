package dev.risas.fancybrewer.models.menu;

import com.google.common.collect.Maps;
import dev.risas.fancybrewer.FancyBrewerPlugin;
import dev.risas.fancybrewer.models.Brewer;
import dev.risas.fancybrewer.models.BrewerStorage;
import dev.risas.fancybrewer.models.menu.buttons.BrewerStorageBackButton;
import dev.risas.fancybrewer.models.menu.buttons.BrewerStorageButton;
import dev.risas.fancybrewer.utilities.menu.Button;
import dev.risas.fancybrewer.utilities.menu.buttons.PageButton;
import dev.risas.fancybrewer.utilities.menu.pagination.PaginatedMenu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class BrewerStorageMenu extends PaginatedMenu {

    private final FancyBrewerPlugin plugin;
    private final Brewer brewer;
    private final BrewerStorage storage;

    public BrewerStorageMenu(FancyBrewerPlugin plugin, Brewer brewer) {
        this.plugin = plugin;
        this.brewer = brewer;
        this.storage = brewer.getStorage();
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "Brewer Storage";
    }

    @Override
    public int getSize() {
        return 9 * 6;
    }

    @Override
    public int getMaxItemsPerPage(Player player) {
        return 9 * 5;
    }

    @Override
    public Map<Integer, Button> getGlobalButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        buttons.put(getSize() - 9, new PageButton(plugin, -1, this));
        buttons.put(getSize() - 1, new PageButton(plugin, 1, this));
        buttons.put(getSize() - 5, new BrewerStorageBackButton(plugin, brewer));

        return buttons;
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();

        for (ItemStack itemStack : brewer.getStorage().getPotions()) {
            buttons.put(buttons.size(), new BrewerStorageButton(storage, itemStack));
        }

        return buttons;
    }

    @Override
    public boolean isUpdateAfterClick() {
        return true;
    }
}
