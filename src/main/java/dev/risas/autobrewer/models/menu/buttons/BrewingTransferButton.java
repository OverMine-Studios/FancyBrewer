package dev.risas.autobrewer.models.menu.buttons;

import dev.risas.autobrewer.models.Brewer;
import dev.risas.autobrewer.servicies.types.ConfigService;
import dev.risas.autobrewer.utilities.item.ItemBuilder;
import dev.risas.autobrewer.utilities.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Collectors;

public class BrewingTransferButton extends Button {

    private final Brewer brewer;

    public BrewingTransferButton(Brewer brewer) {
        this.brewer = brewer;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        ItemStack itemStack = ConfigService.BREWING_MENU_BUTTONS_TRANSFER.clone();
        String status = brewer.isTransfer() ? "&aEnabled" : "&cDisabled";
        return new ItemBuilder(itemStack)
                .setLore(itemStack.getItemMeta().getLore()
                        .stream().map(lore -> lore
                                .replace("<status>", status))
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        brewer.setTransfer(!brewer.isTransfer());
        playSuccess(player);
    }

    @Override
    public boolean shouldUpdate(Player player, int slot, ClickType clickType) {
        return true;
    }
}
