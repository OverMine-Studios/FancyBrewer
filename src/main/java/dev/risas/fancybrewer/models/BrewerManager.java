package dev.risas.fancybrewer.models;

import dev.risas.fancybrewer.resources.types.ConfigResource;
import dev.risas.fancybrewer.utilities.BukkitUtil;
import dev.risas.fancybrewer.utilities.NBTUtil;
import dev.risas.fancybrewer.utilities.file.FileConfig;
import dev.risas.fancybrewer.utilities.item.ItemBuilder;
import dev.risas.fancybrewer.models.plugin.FancyBrewer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Getter @Setter
public class BrewerManager {

    private final FileConfig brewerDataFile;
    private final Map<Location, Brewer> brewers;
    private final Map<UUID, Brewer> openedBrewers;

    public BrewerManager(FancyBrewer plugin) {
        this.brewerDataFile = plugin.getFile("brewer-data");
        this.brewers = new HashMap<>();
        this.openedBrewers = new HashMap<>();
        this.loadBrewers();
    }

    public Set<Material> getAvailableIngredients() {
        Set<Material> materials = new HashSet<>();

        for (BrewerPotionType type : BrewerPotionType.values()) {
            materials.addAll(type.getIngredients());
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
        saveBrewerConfig(brewer, false);
    }

    public void removeBrewer(Brewer brewer) {
        Location location = brewer.getLocation();
        World world = location.getWorld();

        if (world != null) {
            for (ItemStack bottle : brewer.getBottles()) {
                world.dropItemNaturally(location, NBTUtil.deserializeAntiDupeItem(bottle));
            }

            for (ItemStack ingredient : brewer.getIngredients()) {
                world.dropItemNaturally(location, NBTUtil.deserializeAntiDupeItem(ingredient));
            }

            for (ItemStack potion : brewer.getStorage().getPotions()) {
                world.dropItemNaturally(location, potion);
            }
        }

        brewers.remove(brewer.getLocation());
        saveBrewerConfig(brewer, true);
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

    private void saveBrewerConfig(Brewer brewer, boolean remove) {
        String serializedLocation = BukkitUtil.serializeBlockLocation(brewer.getLocation());
        List<String> locations = brewerDataFile.getStringList("brewers");

        if (remove) {
            locations.remove(serializedLocation);
        }
        else {
            locations.add(serializedLocation);
        }

        brewerDataFile.getConfiguration().set("brewers", locations);
        brewerDataFile.save();
    }

    private void loadBrewers() {
        for (String key : brewerDataFile.getStringList("brewers")) {
            Location location = BukkitUtil.deserializeBlockLocation(key);
            brewers.put(location, new Brewer(location));
        }
    }
}
