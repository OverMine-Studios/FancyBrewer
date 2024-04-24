package dev.risas.fancybrewer.models;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class BrewerStorage {

    private List<ItemStack> potions;

    public BrewerStorage() {
        this.potions = new ArrayList<>();
    }

    public void addPotion(ItemStack itemStack) {
        potions.add(itemStack);
    }

    public void addPotionInventory(Player player, ItemStack itemStack) {
        potions.remove(itemStack);
        player.getInventory().addItem(itemStack);
    }
}
