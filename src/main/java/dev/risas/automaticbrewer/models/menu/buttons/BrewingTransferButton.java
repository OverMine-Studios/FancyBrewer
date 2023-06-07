package dev.risas.automaticbrewer.models.menu.buttons;

import dev.risas.automaticbrewer.models.Brewer;
import dev.risas.automaticbrewer.utilities.item.ItemBuilder;
import dev.risas.automaticbrewer.utilities.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class BrewingTransferButton extends Button {

    private final Brewer brewer;

    public BrewingTransferButton(Brewer brewer) {
        this.brewer = brewer;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(Material.HOPPER)
                .setName("&a&lTransfer")
                .setLore("&7Status: " + (brewer.isTransfer() ? "&aEnabled" : "&cDisabled"))
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        brewer.setTransfer(!brewer.isTransfer());
    }

    @Override
    public boolean shouldUpdate(Player player, int slot, ClickType clickType) {
        return true;
    }
}
