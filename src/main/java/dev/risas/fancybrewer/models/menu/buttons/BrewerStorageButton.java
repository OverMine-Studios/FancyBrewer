package dev.risas.fancybrewer.models.menu.buttons;

import dev.risas.fancybrewer.models.BrewerStorage;
import dev.risas.fancybrewer.utilities.PlayerUtil;
import dev.risas.fancybrewer.utilities.menu.Button;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class BrewerStorageButton extends Button {

    private final BrewerStorage storage;
    private final ItemStack itemStack;

    @Override
    public ItemStack getButtonItem(Player player) {
        return itemStack;
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if (PlayerUtil.isInventoryFull(player)) {
            playFail(player);
            return;
        }

        storage.addPotionInventory(player, itemStack);
        playSuccess(player);
    }
}
