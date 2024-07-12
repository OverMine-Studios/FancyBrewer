package dev.risas.fancybrewer.models.brewer.menu.buttons;

import dev.risas.fancybrewer.FancyBrewerPlugin;
import dev.risas.fancybrewer.models.brewer.Brewer;
import dev.risas.fancybrewer.models.brewer.BrewerPotionStage;
import dev.risas.fancybrewer.models.brewer.BrewerState;
import dev.risas.fancybrewer.resources.types.ConfigResource;
import dev.risas.fancybrewer.resources.types.LanguageResource;
import dev.risas.fancybrewer.utilities.ChatUtil;
import dev.risas.fancybrewer.utilities.cooldown.CooldownUtil;
import dev.risas.fancybrewer.utilities.item.ItemBuilder;
import dev.risas.fancybrewer.utilities.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BrewingStartButton extends Button {

    private final FancyBrewerPlugin plugin;
    private final Brewer brewer;

    public BrewingStartButton(FancyBrewerPlugin plugin, Brewer brewer) {
        this.plugin = plugin;
        this.brewer = brewer;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        if (brewer.getState() == BrewerState.BREWING) {
            ItemStack itemStack = ConfigResource.BREWING_MENU_BUTTONS_START_BREWING.clone();
            BrewerPotionStage stage = brewer.getCurrentStage();
            List<String> lore = new ArrayList<>();

            for (String line : itemStack.getItemMeta().getLore()) {
                if (line.contains("<stage-potion-type-3>") && brewer.getPotion().getStages().contains(BrewerPotionStage.INGREDIENT_2)) {
                    lore.add(line
                            .replace("<stage-potion-type-3>", ConfigResource.BREWING_MENU_BUTTONS_START_BREWING_STAGE_POTION_TYPES.get(2)
                                    .replace("<stage-potion-placeholder>", brewer.getPotion().getPlaceholder(2))
                                    .replace("<stage-potion-type>", brewer.getPotion().getName())
                                    .replace("<stage-color>", stage.getColorStage(BrewerPotionStage.INGREDIENT_2))));
                    continue;
                }
                if (line.contains("<stage-potion-type-4>") && brewer.getPotion().getStages().contains(BrewerPotionStage.INGREDIENT_3)) {
                    lore.add(line
                            .replace("<stage-potion-type-4>", ConfigResource.BREWING_MENU_BUTTONS_START_BREWING_STAGE_POTION_TYPES.get(3)
                                    .replace("<stage-potion-placeholder>", brewer.getPotion().getPlaceholder(3))
                                    .replace("<stage-potion-type>", brewer.getPotion().getName())
                                    .replace("<stage-color>", stage.getColorStage(BrewerPotionStage.INGREDIENT_3))));
                    continue;
                }

                lore.add(line
                        .replace("<stage-potion-type-1>", ConfigResource.BREWING_MENU_BUTTONS_START_BREWING_STAGE_POTION_TYPES.get(0)
                                .replace("<stage-color>", stage.getColorStage(BrewerPotionStage.AWKWARD)))
                        .replace("<stage-potion-type-2>", ConfigResource.BREWING_MENU_BUTTONS_START_BREWING_STAGE_POTION_TYPES.get(1)
                                .replace("<stage-color>", stage.getColorStage(BrewerPotionStage.INGREDIENT_1)))
                        .replace("<stage-potion-type>", brewer.getPotion().getName())
                        .replace("<stage-phase>", String.valueOf(brewer.getCurrentStage().getIndex() + 1))
                        .replace("<stage-phase-time>", brewer.getCurrentStage().getStageCooldownFormatted())
                        .replace("<time>", brewer.getEstimatedTimeRemainingFormatted()));
            }

            lore.removeIf(line -> line.contains("<stage-potion-type-3>") && !brewer.getPotion().getStages().contains(BrewerPotionStage.INGREDIENT_2));
            lore.removeIf(line -> line.contains("<stage-potion-type-4>") && !brewer.getPotion().getStages().contains(BrewerPotionStage.INGREDIENT_3));

            return new ItemBuilder(itemStack)
                    .setLore(lore)
                    .build();
        }
        ItemStack itemStack = ConfigResource.BREWING_MENU_BUTTONS_START_NOT_BREWING.clone();
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
                    ChatUtil.sendMessage(player, LanguageResource.BREWER_MESSAGES_NEED_BOTTLES);
                    return;
                }

                if (brewer.getPotion() == null) {
                    playFail(player);
                    ChatUtil.sendMessage(player, LanguageResource.BREWER_MESSAGES_NEED_INGREDIENTS);
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
