package dev.risas.fancybrewer.controllers;

import dev.risas.fancybrewer.models.brewer.Brewer;
import dev.risas.fancybrewer.models.brewer.BrewerPotion;
import dev.risas.fancybrewer.models.plugin.FancyBrewer;
import dev.risas.fancybrewer.resources.types.ConfigResource;
import dev.risas.fancybrewer.utilities.NBTUtil;
import dev.risas.fancybrewer.utilities.SerializeUtil;
import dev.risas.fancybrewer.utilities.file.FileConfig;
import dev.risas.fancybrewer.utilities.item.ItemBuilder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Getter @Setter
public class BrewerController {

    private final FileConfig brewerDataFile;
    private final BrewerPotionController brewerPotionController;

    private final Map<Location, Brewer> brewers;
    private final Map<UUID, Brewer> openedBrewers;

    public BrewerController(FancyBrewer plugin) {
        this.brewerDataFile = plugin.getFile("brewer-data");
        this.brewerPotionController = plugin.getBrewerPotionController();
        this.brewers = new HashMap<>();
        this.openedBrewers = new HashMap<>();
        this.loadBrewers();
    }

    public Set<Material> getAvailableIngredients() {
        Set<Material> materials = new HashSet<>();

        for (BrewerPotion potion : brewerPotionController.getPotions().values()) {
            materials.addAll(potion.getIngredients());
        }

        return materials;
    }

    public Brewer getBrewer(Location location) {
        return brewers.get(location);
    }

    public Brewer getOpenedBrewer(Player player) {
        return openedBrewers.get(player.getUniqueId());
    }

    public void addBrewer(Brewer brewer) {
        brewers.put(brewer.getLocation(), brewer);
        saveBrewerConfig(brewer, brewerDataFile.getConfiguration().getConfigurationSection("brewers"), false, true);
    }

    public void removeBrewer(Brewer brewer) {
        Location location = brewer.getLocation();
        World world = location.getWorld();

        if (world != null) {
            for (ItemStack bottle : brewer.getBottles()) {
                if (bottle == null || bottle.getType() == Material.AIR) continue;
                world.dropItemNaturally(location, NBTUtil.deserializeAntiDupeItem(bottle));
            }

            for (ItemStack ingredient : brewer.getIngredients()) {
                if (ingredient == null || ingredient.getType() == Material.AIR) continue;
                world.dropItemNaturally(location, NBTUtil.deserializeAntiDupeItem(ingredient));
            }

            for (ItemStack potion : brewer.getStorage().getPotions()) {
                if (potion == null || potion.getType() == Material.AIR) continue;
                world.dropItemNaturally(location, potion);
            }
        }

        brewers.remove(brewer.getLocation());
        saveBrewerConfig(brewer, brewerDataFile.getConfiguration().getConfigurationSection("brewers"), true, true);
    }

    public void addOpenedBrewer(Player player, Brewer brewer) {
        openedBrewers.putIfAbsent(player.getUniqueId(), brewer);
    }

    public void removeOpenedBrewer(Player player) {
        openedBrewers.remove(player.getUniqueId());
    }

    public boolean existBrewer(Location location) {
        return brewers.containsKey(location);
    }

    public boolean isBrewer(ItemStack itemStack) {
        return itemStack.isSimilar(ConfigResource.BREWING_ITEM);
    }

    public void giveBrewer(Player player, int amount) {
        player.getInventory().addItem(new ItemBuilder(ConfigResource.BREWING_ITEM)
                .setAmount(amount)
                .build());
    }

    private void saveBrewerConfig(Brewer brewer, ConfigurationSection section, boolean remove, boolean save) {
        String id = SerializeUtil.serializeBlockLocation(brewer.getLocation());

        if (remove) {
            section.set(id, null);
        }
        else {
            section.set(id + ".bottles", SerializeUtil.serializeItemStackList(brewer.getBottles()));
            section.set(id + ".ingredients", SerializeUtil.serializeItemStackList(brewer.getIngredients()));
            section.set(id + ".storage", SerializeUtil.serializeItemStackList(brewer.getStorage().getPotions()));
        }

        if (save) brewerDataFile.save();
    }

    private void loadBrewers() {
        ConfigurationSection section = brewerDataFile.getConfiguration().getConfigurationSection("brewers");
        if (section == null) throw new IllegalStateException("Brewer data is empty.");

        section.getKeys(false).forEach(key -> {
            Location location = SerializeUtil.deserializeBlockLocation(key);

            Brewer brewer = new Brewer(brewerPotionController, location);
            brewer.setBottles(SerializeUtil.deserializeItemStackList(section.getString(key + ".bottles")));
            brewer.setIngredients(SerializeUtil.deserializeItemStackList(section.getString(key + ".ingredients")));
            brewer.getStorage().setPotions(SerializeUtil.deserializeItemStackList(section.getString(key + ".storage")));
            brewer.checkAndSetPotionType();
            brewers.put(location, brewer);
        });
    }

    public void onDisable() {
        ConfigurationSection section = brewerDataFile.getConfiguration().getConfigurationSection("brewers");
        brewers.values().forEach(brewer -> saveBrewerConfig(brewer, section, false, false));
        brewerDataFile.save();
    }
}
