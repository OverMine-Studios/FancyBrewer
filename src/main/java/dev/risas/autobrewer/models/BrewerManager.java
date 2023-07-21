package dev.risas.autobrewer.models;

import com.cryptomorin.xseries.XMaterial;
import dev.risas.autobrewer.AutoBrewer;
import dev.risas.autobrewer.utilities.BukkitUtil;
import dev.risas.autobrewer.utilities.file.FileConfig;
import dev.risas.autobrewer.utilities.item.ItemBuilder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Getter @Setter
public class BrewerManager {

    private final FileConfig brewerFile;
    private final Map<Location, Brewer> brewers;
    private final Map<UUID, Brewer> openedBrewers;
    private ItemStack brewerItem;

    public BrewerManager(AutoBrewer plugin) {
        this.brewerFile = plugin.getBrewerFile();
        this.brewers = new HashMap<>();
        this.openedBrewers = new HashMap<>();
        this.loadBrewerItem();
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
    }

    public void addOpenedBrewer(Player player, Brewer brewer) {
        openedBrewers.put(player.getUniqueId(), brewer);
    }

    public void removeBrewer(Brewer brewer) {
        brewers.remove(brewer.getLocation());
    }

    public void removeOpenedBrewer(Player player) {
        openedBrewers.remove(player.getUniqueId());
    }

    public boolean existBrewer(Location location) {
        return brewers.containsKey(location);
    }

    public boolean isBrewer(ItemStack itemStack) {
        return itemStack.isSimilar(brewerItem);
    }

    public void giveBrewer(Player player, int amount) {
        for (int i = 0; i < amount; i++) {
            player.getInventory().addItem(brewerItem);
        }
    }

    public void giveAllBrewer(int amount) {
        Bukkit.getOnlinePlayers().forEach(player -> giveBrewer(player, amount));
    }

    public void loadBrewerItem() {
        this.brewerItem = new ItemBuilder(XMaterial.BREWING_STAND.parseMaterial())
                .setName(brewerFile.getString("brewer-item.name"))
                .setLore(brewerFile.getStringList("brewer-item.description"))
                .build();
    }

    private void loadBrewers() {
        for (String key : brewerFile.getStringList("brewers")) {
            this.addBrewer(new Brewer(BukkitUtil.deserializeBlockLocation(key)));
        }
    }

    public void onDisable() {
        List<String> locations = new ArrayList<>();

        for (Brewer brewer : brewers.values()) {
            locations.add(BukkitUtil.serializeBlockLocation(brewer.getLocation()));
        }

        brewerFile.getConfiguration().set("brewers", locations);
        brewerFile.save();
    }
}
