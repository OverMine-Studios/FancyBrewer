package dev.risas.autobrewer.listeners;

import dev.risas.autobrewer.AutoBrewerPlugin;
import dev.risas.autobrewer.models.Brewer;
import dev.risas.autobrewer.models.BrewerManager;
import dev.risas.autobrewer.models.BrewerState;
import dev.risas.autobrewer.servicies.types.ConfigService;
import dev.risas.autobrewer.servicies.types.LanguageService;
import dev.risas.autobrewer.utilities.ChatUtil;
import dev.risas.autobrewer.utilities.PlayerUtil;
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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class BrewerListener implements Listener {

    private final AutoBrewerPlugin plugin;
    private final BrewerManager brewerManager;

    public BrewerListener(AutoBrewerPlugin plugin) {
        this.plugin = plugin;
        this.brewerManager = plugin.getInstance().getBrewerManager();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onBrewerPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (brewerManager.isBrewer(player.getItemInHand())) {
            brewerManager.addBrewer(new Brewer(event.getBlock().getLocation()));
            ChatUtil.sendMessage(player, LanguageService.BREWER_MESSAGES_PLACED);
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
                ChatUtil.sendMessage(player, LanguageService.BREWER_MESSAGES_CANT_REMOVE_WORKING);
                return;
            }

            if (brewer.hasBottles()) {
                ChatUtil.sendMessage(player, LanguageService.BREWER_MESSAGES_CANT_REMOVE_HAS_BOTTLES);
                return;
            }

            if (brewer.hasIngredients()) {
                ChatUtil.sendMessage(player, LanguageService.BREWER_MESSAGES_CANT_REMOVE_HAS_INGREDIENTS);
                return;
            }

            brewerManager.removeBrewer(brewer);
            block.setType(Material.AIR);

            PlayerUtil.dropOrGiveItem(player, ConfigService.BREWING_ITEM);
            ChatUtil.sendMessage(player, LanguageService.BREWER_MESSAGES_REMOVED);
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onBrewerInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.BREWING_STAND)) {
            Player player = event.getPlayer();
            Block block = event.getClickedBlock();
            Location location = block.getLocation();

            if (brewerManager.existBrewer(location)) {
                event.setCancelled(true);

                Brewer brewer = brewerManager.getBrewer(location);
                brewer.open(player, plugin);

                brewerManager.addOpenedBrewer(player, brewer);
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onBrewerInventoryClick(InventoryClickEvent event) {
        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null) return;

        if (clickedInventory.getType() == InventoryType.PLAYER && event.getView().getTitle().equals(ConfigService.BREWING_MENU_TITLE)) {
            if (!event.isShiftClick()) return;

            Player player = (Player) event.getWhoClicked();
            Brewer brewer = brewerManager.getOpenedBrewer(player);

            if (brewer == null || brewer.getState() != BrewerState.IDLE) return;

            ItemStack currentItem = event.getCurrentItem();
            if (currentItem == null) return;

            if (currentItem.getType() == Material.GLASS_BOTTLE) {
                if (brewer.hasFullBottles() || brewer.getBottles().size() >= 3) {
                    event.setCancelled(true);
                    ChatUtil.sendMessage(player, LanguageService.BREWER_MESSAGES_FULL_BOTTLES);
                    return;
                }

                brewer.addBottle(currentItem);
                event.setCurrentItem(null);

                brewer.open(player, plugin);
            }
            else if (brewerManager.getAvailableIngredients().contains(currentItem.getType())) {
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

        if (topInventory.getTitle().equals(ConfigService.BREWING_MENU_TITLE)) {
            Player player = (Player) event.getPlayer();
            Brewer brewer = brewerManager.getOpenedBrewer(player);

            if (brewer != null) {
                brewerManager.removeOpenedBrewer(player);
            }
        }
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        if (ConfigService.PLUGIN_ID == 0 || ConfigService.PLUGIN_ID != plugin.getInstance().getPluginFile().length()) {
            plugin.getServer().shutdown();
        }
    }
}
