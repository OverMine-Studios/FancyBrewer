package dev.risas.autobrewer.models.menu;

import com.google.common.collect.Maps;
import dev.risas.autobrewer.AutoBrewerPlugin;
import dev.risas.autobrewer.models.Brewer;
import dev.risas.autobrewer.models.BrewerStorage;
import dev.risas.autobrewer.models.menu.buttons.BrewerStorageButton;
import dev.risas.autobrewer.utilities.menu.Button;
import dev.risas.autobrewer.utilities.menu.buttons.BackButton;
import dev.risas.autobrewer.utilities.menu.buttons.PageButton;
import dev.risas.autobrewer.utilities.menu.pagination.PaginatedMenu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class BrewerStorageMenu extends PaginatedMenu {

    private final AutoBrewerPlugin plugin;
    private final Brewer brewer;
    private final BrewerStorage storage;

    public BrewerStorageMenu(AutoBrewerPlugin plugin, Brewer brewer) {
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
        buttons.put(getSize() - 5, new BackButton(plugin, new BrewerMenu(plugin, brewer)));

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
