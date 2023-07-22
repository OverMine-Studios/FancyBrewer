package dev.risas.autobrewer.utilities.menu;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Maps;
import dev.risas.autobrewer.AutoBrewerPlugin;
import dev.risas.autobrewer.utilities.item.ItemBuilder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;
import java.util.UUID;

@Getter @Setter
public abstract class Menu {

    @Getter
    private static Map<UUID, Menu> menus = Maps.newHashMap();

    @Getter
    private Map<Integer, Button> buttons = Maps.newHashMap();

    private boolean autoUpdate;
    private boolean updateAfterClick;
    private boolean closedByMenu;
    private boolean cancelPlayerInventory = true;
    private boolean placeholder;
    private Button placeholderButton = Button.placeholder(new ItemBuilder(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial())
            .setData(XMaterial.BLACK_STAINED_GLASS_PANE.getData())
            .setName(" ")
            .build());
    private BukkitTask task = null;

    private ItemStack createItemStack(Player player, Button button) {
        return new ItemBuilder(button.getButtonItem(player)).build();
    }

    public void openMenu(Player player, AutoBrewerPlugin plugin) {
        this.buttons = this.getButtons(player);

        Menu previousMenu = Menu.getMenu(player);
        Inventory inventory = null;
        String title = this.getTitle(player);
        int size = this.getSize() == -1 ? this.size(this.buttons) : this.getSize();
        boolean update = false;
        boolean autoUpdate = this.isAutoUpdate();

        if (title.length() > 32) {
            title = title.substring(0, 32);
        }

        if (player.getOpenInventory() != null) {
            if (previousMenu == null) {
                player.closeInventory();
            }
            else {
                int previousSize = player.getOpenInventory().getTopInventory().getSize();

                if (previousSize == size && player.getOpenInventory().getTitle().equals(title)) {
                    inventory = player.getOpenInventory().getTopInventory();
                    update = true;
                }
                else {
                    previousMenu.setClosedByMenu(true);
                    player.closeInventory();
                }
            }
        }

        if (inventory == null) {
            inventory = Bukkit.createInventory(null, size, title);
        }

        inventory.setContents(new ItemStack[inventory.getSize()]);

        menus.put(player.getUniqueId(), this);

        for (Map.Entry<Integer, Button> buttonEntry : this.buttons.entrySet()) {
            inventory.setItem(buttonEntry.getKey(), createItemStack(player, buttonEntry.getValue()));
        }

        if (isPlaceholder()) {
            for (int index = 0; index < size; index++) {
                if (buttons.get(index) == null) {
                    buttons.put(index, placeholderButton);
                    inventory.setItem(index, placeholderButton.getButtonItem(player));
                }
            }
        }

        if (update) {
            player.updateInventory();
        }
        else {
            player.openInventory(inventory);
        }

        this.setClosedByMenu(false);

        if (autoUpdate && task == null) {
            task = Bukkit.getScheduler().runTaskTimer(plugin, () -> openMenu(player, plugin), 0L, 20L);
        }
        if (!autoUpdate && task != null) {
            task.cancel();
            task = null;
        }
    }

    public int size(Map<Integer, Button> buttons) {
        int highest = 0;

        for (int buttonValue : buttons.keySet()) {
            if (buttonValue > highest) {
                highest = buttonValue;
            }
        }

        return (int) (Math.ceil((highest + 1) / 9D) * 9D);
    }

    public int getSlot(int x, int y) {
        return ((9 * y) + x);
    }

    public int getSize() {
        return -1;
    }

    public void onClose(Player player) {
        menus.remove(player.getUniqueId());

        if (task != null) {
            task.cancel();
        }
    }

    public abstract String getTitle(Player player);

    public abstract Map<Integer, Button> getButtons(Player player);

    public static Menu getMenu(Player player) {
        return menus.get(player.getUniqueId());
    }
}