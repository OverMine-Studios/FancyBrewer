package dev.risas.fancybrewer.models.brewer.menu.buttons;

import com.cryptomorin.xseries.XMaterial;
import dev.risas.fancybrewer.models.brewer.Brewer;
import dev.risas.fancybrewer.models.brewer.BrewerPotionStage;
import dev.risas.fancybrewer.models.brewer.BrewerState;
import dev.risas.fancybrewer.utilities.BukkitUtil;
import dev.risas.fancybrewer.utilities.item.ItemBuilder;
import dev.risas.fancybrewer.utilities.menu.Button;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

public class BrewingDisplayButton extends Button {

    private final Brewer brewer;

    public BrewingDisplayButton(Brewer brewer) {
        this.brewer = brewer;
    }

    @Override
    public ItemStack getButtonItem(Player player) {
        if (brewer.getState() == BrewerState.IDLE) {
            ItemBuilder itemBuilder = new ItemBuilder(XMaterial.POTION.parseMaterial());

            if (BukkitUtil.SERVER_VERSION_INT >= 13) {
                itemBuilder.setPotionData(PotionType.WATER);
            }

            return itemBuilder.build();
        }
        if (brewer.getCurrentStage() == BrewerPotionStage.AWKWARD) {
            ItemBuilder itemBuilder = new ItemBuilder(XMaterial.POTION.parseMaterial());

            if (BukkitUtil.SERVER_VERSION_INT >= 13) {
                itemBuilder.setPotionData(PotionType.WATER);
            }

            itemBuilder.setAmount(3);
            return itemBuilder.build();
        }
        else if (brewer.getCurrentStage() == BrewerPotionStage.INGREDIENT_1) {
            ItemBuilder itemBuilder = new ItemBuilder(XMaterial.POTION.parseMaterial());

            if (BukkitUtil.SERVER_VERSION_INT >= 13) {
                itemBuilder.setPotionData(PotionType.AWKWARD);
            }
            else {
                itemBuilder.setData(16);
            }

            itemBuilder.setAmount(3);
            return itemBuilder.build();
        }
        else if (brewer.getCurrentStage() == BrewerPotionStage.INGREDIENT_2) {
            return new ItemBuilder(brewer.getPotion().getResult().clone())
                    .setAmount(3)
                    .build();
        }
        else if (brewer.getCurrentStage() == BrewerPotionStage.INGREDIENT_3) {
            return new ItemBuilder(brewer.getPotion().getResult().clone())
                    .setAmount(3)
                    .build();
        }
        return null;
    }
}
