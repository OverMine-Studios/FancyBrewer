package dev.risas.fancybrewer.listeners;

import dev.risas.fancybrewer.FancyBrewerPlugin;
import dev.risas.fancybrewer.controllers.BrewerManager;
import dev.risas.fancybrewer.models.brewer.Brewer;
import dev.risas.fancybrewer.models.brewer.BrewerState;
import dev.risas.fancybrewer.resources.types.ConfigResource;
import dev.risas.fancybrewer.resources.types.LanguageResource;
import dev.risas.fancybrewer.utilities.ChatUtil;
import dev.risas.fancybrewer.utilities.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class BrewerListener implements Listener {

    private final FancyBrewerPlugin plugin;
    private final BrewerManager brewerManager;

    public BrewerListener(FancyBrewerPlugin plugin) {
        this.plugin = plugin;
        this.brewerManager = plugin.getInstance().getBrewerManager();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onBrewerPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (brewerManager.isBrewer(player.getItemInHand())) {
            brewerManager.addBrewer(new Brewer(event.getBlock().getLocation()));
            ChatUtil.sendMessage(player, LanguageResource.BREWER_MESSAGES_PLACED);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onBrewerBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location location = block.getLocation();

        if (brewerManager.existBrewer(location)) {
            event.setCancelled(true);

            Brewer brewer = brewerManager.getBrewer(location);

            if (brewer.getState() != BrewerState.IDLE) {
                ChatUtil.sendMessage(player, LanguageResource.BREWER_MESSAGES_CANT_REMOVE_WORKING);
                return;
            }

            brewerManager.removeBrewer(brewer);
            block.setType(Material.AIR);

            PlayerUtil.dropOrGiveItem(player, ConfigResource.BREWING_ITEM);
            ChatUtil.sendMessage(player, LanguageResource.BREWER_MESSAGES_REMOVED);
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onBrewerInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Block block = event.getClickedBlock();

            if (block == null || !block.getType().equals(Material.BREWING_STAND)) return;

            Player player = event.getPlayer();
            Location location = block.getLocation();

            if (brewerManager.existBrewer(location)) {
                event.setCancelled(true);

                Brewer brewer = brewerManager.getBrewer(location);
                brewer.open(player, plugin);
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onBrewerInventoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null) return;

        if (clickedInventory.getType() == InventoryType.PLAYER && event.getView().getTitle().equals(ConfigResource.BREWING_MENU_TITLE)) {
            if (!event.isShiftClick()) return;

            Player player = (Player) event.getWhoClicked();
            Brewer brewer = brewerManager.getOpenedBrewer(player);
            if (brewer == null) return;

            ItemStack currentItem = event.getCurrentItem();
            if (currentItem == null) return;

            if (brewer.getState() != BrewerState.IDLE) {
                event.setCancelled(true);
                ChatUtil.sendMessage(player, LanguageResource.BREWER_MESSAGES_CANT_ADD_MATERIALS);
                return;
            }

            if (currentItem.getType() == Material.GLASS_BOTTLE) {
                if (brewer.hasFullBottles() || brewer.getBottles().size() >= 3) {
                    event.setCancelled(true);
                    ChatUtil.sendMessage(player, LanguageResource.BREWER_MESSAGES_FULL_BOTTLES);
                    return;
                }

                brewer.addBottle(currentItem);
                event.setCurrentItem(null);

                brewer.open(player, plugin);
            }
            else if (brewerManager.getAvailableIngredients().contains(currentItem.getType())) {
                if (brewer.hasFullIngredients()) {
                    event.setCancelled(true);
                    ChatUtil.sendMessage(player, LanguageResource.BREWER_MESSAGES_FULL_INGREDIENTS);
                    return;
                }

                brewer.addIngredient(currentItem);
                event.setCurrentItem(null);

                brewer.open(player, plugin);
            }
            else {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onBrewerInventoryClose(InventoryCloseEvent event) {
        InventoryView topInventory = event.getView();

        if (topInventory.getTitle().equals(ConfigResource.BREWING_MENU_TITLE)) {
            Player player = (Player) event.getPlayer();
            Brewer brewer = brewerManager.getOpenedBrewer(player);

            if (brewer != null) {
                brewerManager.removeOpenedBrewer(player);
            }
        }
    }
}
