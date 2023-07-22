package dev.risas.autobrewer.models.menu.buttons;

import com.cryptomorin.xseries.XMaterial;
import dev.risas.autobrewer.AutoBrewerPlugin;
import dev.risas.autobrewer.models.Brewer;
import dev.risas.autobrewer.models.BrewerPotionStage;
import dev.risas.autobrewer.models.BrewerState;
import dev.risas.autobrewer.servicies.types.ConfigService;
import dev.risas.autobrewer.servicies.types.LanguageService;
import dev.risas.autobrewer.utilities.ChatUtil;
import dev.risas.autobrewer.utilities.cooldown.CooldownUtil;
import dev.risas.autobrewer.utilities.item.ItemBuilder;
import dev.risas.autobrewer.utilities.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BrewingStartButton extends Button {

    private final AutoBrewerPlugin plugin;
    private final Brewer brewer;

    public BrewingStartButton(AutoBrewerPlugin plugin, Brewer brewer) {
        this.plugin = plugin;
        this.brewer = brewer;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        if (brewer.getState() == BrewerState.BREWING) {
            ItemStack itemStack = ConfigService.BREWING_MENU_BUTTONS_START_BREWING.clone();
            BrewerPotionStage stage = brewer.getCurrentStage();
            List<String> lore = new ArrayList<>();

            for (String line : itemStack.getItemMeta().getLore()) {
                if (line.contains("<stage-potion-type-3>")) {
                    if (brewer.getPotionType().getIngredients().contains(Material.GLOWSTONE_DUST)) {
                        lore.add(line
                                .replace("<stage-potion-type-3>", ConfigService.BREWING_MENU_BUTTONS_START_BREWING_STAGE_POTION_TYPES.get(2)
                                        .replace("<stage-potion-type>", brewer.getPotionType().getName())
                                        .replace("<stage-color>", stage.getColorStage(BrewerPotionStage.INGREDIENT_2))));
                    }
                    continue;
                }
                if (line.contains("<stage-potion-type-4>")) {
                    if (brewer.getPotionType().getIngredients().contains(XMaterial.GUNPOWDER.parseMaterial())) {
                        lore.add(line
                                .replace("<stage-potion-type-4>", ConfigService.BREWING_MENU_BUTTONS_START_BREWING_STAGE_POTION_TYPES.get(3)
                                        .replace("<stage-potion-type>", brewer.getPotionType().getName())
                                        .replace("<stage-color>", stage.getColorStage(BrewerPotionStage.INGREDIENT_3))));
                    }
                    continue;
                }

                lore.add(line
                        .replace("<stage-potion-type-1>", ConfigService.BREWING_MENU_BUTTONS_START_BREWING_STAGE_POTION_TYPES.get(0)
                                .replace("<stage-color>", stage.getColorStage(BrewerPotionStage.AWKWARD)))
                        .replace("<stage-potion-type-2>", ConfigService.BREWING_MENU_BUTTONS_START_BREWING_STAGE_POTION_TYPES.get(1)
                                .replace("<stage-color>", stage.getColorStage(BrewerPotionStage.INGREDIENT_1)))
                        .replace("<stage-potion-type>", brewer.getPotionType().getName())
                        .replace("<stage-phase>", String.valueOf(brewer.getCurrentStage().getIndex() + 1))
                        .replace("<stage-phase-time>", brewer.getCurrentStage().getStageCooldownFormatted())
                        .replace("<time>", brewer.getEstimatedTimeRemainingFormatted()));
            }

            return new ItemBuilder(itemStack)
                    .setLore(lore)
                    .build();
        }
        ItemStack itemStack = ConfigService.BREWING_MENU_BUTTONS_START_NOT_BREWING.clone();
        return new ItemBuilder(itemStack)
                .setLore(itemStack.getItemMeta().getLore()
                        .stream().map(line -> line
                                .replace("<time>", brewer.getEstimatedTimeRemainingFormatted()))
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if (CooldownUtil.hasCooldown(player, "brewing-start-button")) {
            ChatUtil.sendMessage(player, "&cYou can't click this button so fast!");
            return;
        }

        CooldownUtil.setCooldown(plugin, player, "brewing-start-button", 1);

        if (brewer.getState() == BrewerState.IDLE) {
            if (brewer.getCurrentStage() == null) {
                if (!brewer.hasMinBottles()) {
                    playFail(player);
                    ChatUtil.sendMessage(player, LanguageService.BREWER_MESSAGES_NEED_BOTTLES);
                    return;
                }

                if (brewer.getPotionType() == null) {
                    playFail(player);
                    ChatUtil.sendMessage(player, LanguageService.BREWER_MESSAGES_NEED_INGREDIENTS);
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
