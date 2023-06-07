package dev.risas.automaticbrewer.models.menu.buttons;

import dev.risas.automaticbrewer.AutomaticBrewer;
import dev.risas.automaticbrewer.models.Brewer;
import dev.risas.automaticbrewer.models.BrewerPotionStage;
import dev.risas.automaticbrewer.models.BrewerState;
import dev.risas.automaticbrewer.utilities.ChatUtil;
import dev.risas.automaticbrewer.utilities.CooldownUtil;
import dev.risas.automaticbrewer.utilities.file.FileConfig;
import dev.risas.automaticbrewer.utilities.item.ItemBuilder;
import dev.risas.automaticbrewer.utilities.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BrewingStartButton extends Button {

    private final AutomaticBrewer plugin;
    private final FileConfig languageFile;
    private final Brewer brewer;

    public BrewingStartButton(AutomaticBrewer plugin, Brewer brewer) {
        this.plugin = plugin;
        this.languageFile = plugin.getLanguageFile();
        this.brewer = brewer;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        if (brewer.getState() == BrewerState.BREWING) {
            List<String> lore = new ArrayList<>();
            lore.add("");

            BrewerPotionStage stage = brewer.getCurrentStage();

            lore.add(" " + stage.getColorStage(BrewerPotionStage.AWKWARD) + "► &fBrew Awkward Potion");
            lore.add(" " + stage.getColorStage(BrewerPotionStage.INGREDIENT_1) + "► &fBrew " + brewer.getPotionType().getName());

            if (brewer.getPotionType().getIngredients().contains(Material.GLOWSTONE_DUST)) {
                lore.add(" " + stage.getColorStage(BrewerPotionStage.INGREDIENT_2) + "► &fBrew " + brewer.getPotionType().getName() + " II");
            }
            if (brewer.getPotionType().getIngredients().contains(Material.SULPHUR)) {
                lore.add(" " + stage.getColorStage(BrewerPotionStage.INGREDIENT_3) + "► &fBrew Splash " + brewer.getPotionType().getName());
            }

            lore.add("");
            lore.add("&7Current Stage");
            lore.add("  &7Phase: &e" + (brewer.getCurrentStage().getIndex() + 1));
            lore.add("  &7Time: &e" + brewer.getCurrentStage().getStageCooldownFormatted());
            lore.add("");
            lore.add("&7Estimated Time: &e" + brewer.getEstimatedTimeRemainingFormatted());
            lore.add("");
            lore.add("&eClick to stop brewing.");

            return new ItemBuilder(Material.BREWING_STAND_ITEM)
                    .setName("&a&lBrewing")
                    .setLore(lore)
                    .build();
        }
        return new ItemBuilder(Material.BREWING_STAND_ITEM)
                .setName("&a&lStart Brewing")
                .setLore("&7Estimated Time: &e" + brewer.getEstimatedTimeRemainingFormatted())
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if (CooldownUtil.hasCooldown(player, "brewing-start-button")) {
            ChatUtil.sendMessage(player, "&cYou can't click this button yet.");
            return;
        }

        CooldownUtil.setCooldown(player, "brewing-start-button", 1000L);

        if (brewer.getState() == BrewerState.IDLE) {
            if (brewer.getCurrentStage() == null) {
                if (!brewer.hasMinBottles()) {
                    playFail(player);
                    ChatUtil.sendMessage(player, languageFile.getString("brewer-messages.need-bottles"));
                    return;
                }

                if (brewer.getPotionType() == null) {
                    playFail(player);
                    ChatUtil.sendMessage(player, languageFile.getString("brewer-messages.need-ingredients"));
                    return;
                }
            }

            playSuccess(player);
            brewer.setState(BrewerState.BREWING);
            brewer.startBrewing(plugin);
            brewer.open(player, plugin);
        }
        else if (brewer.getState() == BrewerState.BREWING) {
            playSuccess(player);
            brewer.setState(BrewerState.IDLE);
        }
    }
}
