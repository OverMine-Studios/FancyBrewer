package dev.risas.automaticbrewer.listeners;

import dev.risas.automaticbrewer.AutomaticBrewer;
import dev.risas.automaticbrewer.models.Brewer;
import dev.risas.automaticbrewer.models.BrewerManager;
import dev.risas.automaticbrewer.models.BrewerState;
import dev.risas.automaticbrewer.utilities.ChatUtil;
import dev.risas.automaticbrewer.utilities.PlayerUtil;
import dev.risas.automaticbrewer.utilities.file.FileConfig;
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
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BrewerListener implements Listener {

    private final AutomaticBrewer plugin;
    private final FileConfig languageFile;
    private final BrewerManager brewerManager;

    public BrewerListener(AutomaticBrewer plugin) {
        this.plugin = plugin;
        this.languageFile = plugin.getLanguageFile();
        this.brewerManager = plugin.getBrewerManager();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onBrewerPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (brewerManager.isBrewer(player.getItemInHand())) {
            brewerManager.addBrewer(new Brewer(event.getBlock().getLocation()));
            ChatUtil.sendMessage(player, languageFile.getString("brewer-messages.placed"));
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
                ChatUtil.sendMessage(player, languageFile.getString("brewer-messages.cant-remove.working"));
                return;
            }

            if (brewer.hasBottles()) {
                ChatUtil.sendMessage(player, languageFile.getString("brewer-messages.cant-remove.has-bottles"));
                return;
            }

            if (brewer.hasIngredients()) {
                ChatUtil.sendMessage(player, languageFile.getString("brewer-messages.cant-remove.has-ingredients"));
                return;
            }

            brewerManager.removeBrewer(brewer);
            block.setType(Material.AIR);

            PlayerUtil.dropOrGiveItem(player, brewerManager.getBrewerItem());
            ChatUtil.sendMessage(player, languageFile.getString("brewer-messages.removed"));
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
        if (clickedInventory == null || event.getClick() != ClickType.LEFT) return;

        Inventory topInventory = event.getView().getTopInventory();
        if (topInventory == null) return;

        if (clickedInventory.getType() == InventoryType.PLAYER && topInventory.getTitle().equals("Fancy Brewer")) {
            if (event.isShiftClick()) {
                event.setCancelled(true);
                return;
            }

            Player player = (Player) event.getWhoClicked();
            Brewer brewer = brewerManager.getOpenedBrewer(player);

            if (brewer == null || brewer.getState() != BrewerState.IDLE) return;

            ItemStack currentItem = event.getCurrentItem();

            if (currentItem != null && currentItem.getType() == Material.GLASS_BOTTLE) {
                if (brewer.hasFullBottles()) {
                    ChatUtil.sendMessage(player, languageFile.getString("brewer-messages.full-bottles"));
                    return;
                }

                brewer.addBottle(currentItem);
                event.setCurrentItem(null);

                brewer.open(player, plugin);
            }

            if (currentItem != null && brewerManager.getAvailableIngredients().contains(currentItem.getType())) {
                brewer.addIngredient(currentItem);
                event.setCurrentItem(null);

                brewer.open(player, plugin);
            }
        }
    }

    @EventHandler
    private void onBrewerInventoryClose(InventoryCloseEvent event) {
        Inventory topInventory = event.getView().getTopInventory();
        if (topInventory == null) return;

        if (topInventory.getTitle().equals(languageFile.getString("brewer-menu.title"))) {
            Player player = (Player) event.getPlayer();
            Brewer brewer = brewerManager.getOpenedBrewer(player);

            if (brewer != null) {
                brewerManager.removeOpenedBrewer(player);
            }
        }
    }
}
