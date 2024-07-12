package dev.risas.fancybrewer.controllers;

import dev.risas.fancybrewer.models.brewer.BrewerPotion;
import dev.risas.fancybrewer.models.plugin.FancyBrewer;
import dev.risas.fancybrewer.utilities.file.FileConfig;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;

public class BrewerPotionController {

    private final FileConfig potionDataFile;
    @Getter private final Map<String, BrewerPotion> potions;

    public BrewerPotionController(FancyBrewer plugin) {
        this.potionDataFile = plugin.getFile("potion-data");
        this.potions = new HashMap<>();
        this.onReload();
    }

    public void onReload() {
        this.potions.clear();

        ConfigurationSection section = potionDataFile.getConfiguration().getConfigurationSection("potions");
        if (section == null) throw new IllegalArgumentException("Invalid potion section");

        for (String potionId : section.getKeys(false)) {
            ConfigurationSection potionSection = section.getConfigurationSection(potionId);
            if (potionSection == null) throw new IllegalArgumentException("Invalid potion id " + potionId + " section");

            BrewerPotion potion = new BrewerPotion(potionId, potionSection);
            this.potions.put(potionId, potion);
        }
    }
}
