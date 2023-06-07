package dev.risas.automaticbrewer.models.menu.buttons;

import dev.risas.automaticbrewer.utilities.item.ItemBuilder;
import dev.risas.automaticbrewer.utilities.menu.Button;
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
