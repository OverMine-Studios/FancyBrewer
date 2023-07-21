package dev.risas.autobrewer.models.menu.buttons;

import dev.risas.autobrewer.utilities.item.ItemBuilder;
import dev.risas.autobrewer.utilities.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BrewingStorageButton extends Button {

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(Material.MINECART)
                .setName("&9&lStorage")
                .setLore("&7Empty")
                .build();
    }
}
