package dev.risas.autobrewer.utilities.menu.buttons;

import com.cryptomorin.xseries.XMaterial;
import dev.risas.autobrewer.AutoBrewerPlugin;
import dev.risas.autobrewer.utilities.item.ItemBuilder;
import dev.risas.autobrewer.utilities.menu.Button;
import dev.risas.autobrewer.utilities.menu.Menu;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class BackButton extends Button {

    private final AutoBrewerPlugin plugin;
    private final Menu back;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(XMaterial.REDSTONE.parseMaterial())
                .setName("&cBack")
                .setLore("&7Click to go back.")
                .build();
    }

    @Override
    public void clicked(Player player, int i, ClickType clickType, int hb) {
        playNeutral(player);
        Bukkit.getScheduler().runTaskLater(plugin, () -> back.openMenu(player, plugin), 1L);
    }
}
