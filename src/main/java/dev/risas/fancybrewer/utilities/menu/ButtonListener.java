package dev.risas.fancybrewer.utilities.menu;

import dev.risas.fancybrewer.FancyBrewerPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class ButtonListener implements Listener {

	private final FancyBrewerPlugin plugin;

	public ButtonListener(FancyBrewerPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	private void onButtonPress(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Menu menu = Menu.getMenu(player);

		if (menu != null) {
			event.setCancelled(menu.isCancelPlayerInventory());

			if (event.getSlot() != event.getRawSlot()) {
				if ((event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT)) {
					event.setCancelled(false);
				}
				return;
			}

			if (menu.getButtons().containsKey(event.getSlot())) {
				Button button = menu.getButtons().get(event.getSlot());
				boolean shouldCancel = button.shouldCancel(player, event.getSlot(), event.getClick());
				boolean shouldShift = button.shouldShift(player, event.getSlot(), event.getClick());

				if (shouldCancel && shouldShift) {
					event.setCancelled(true);
				}
				else {
					event.setCancelled(shouldCancel);
				}

				button.clicked(player, event.getSlot(), event.getClick(), event.getHotbarButton());

				if (menu.isUpdateAfterClick() || button.shouldUpdate(player, event.getSlot(), event.getClick())) {
					menu.setClosedByMenu(true);
					menu.openMenu(player, plugin);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	private void onInventoryClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		Menu menu = Menu.getMenu(player);

		if (menu != null) {
			menu.onClose(player);
		}
	}
}