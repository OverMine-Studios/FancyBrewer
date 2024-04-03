package dev.risas.fancybrewer.models.menu.buttons;

import com.cryptomorin.xseries.XMaterial;
import dev.risas.fancybrewer.FancyBrewerPlugin;
import dev.risas.fancybrewer.models.Brewer;
import dev.risas.fancybrewer.utilities.item.ItemBuilder;
import dev.risas.fancybrewer.utilities.menu.Button;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class BrewerStorageBackButton extends Button {

    private final FancyBrewerPlugin plugin;
    private final Brewer brewer;

    public BrewerStorageBackButton(FancyBrewerPlugin plugin , Brewer brewer) {
        this.plugin = plugin;
        this.brewer = brewer;
    }

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
        close(player);

        Bukkit.getScheduler().runTaskLater(plugin, () -> brewer.open(player, plugin), 1L);
    }
}
