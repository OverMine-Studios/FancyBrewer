package dev.risas.fancybrewer.models.brewer;

import dev.risas.fancybrewer.FancyBrewerPlugin;
import dev.risas.fancybrewer.controllers.BrewerPotionController;
import dev.risas.fancybrewer.models.brewer.menu.BrewerMenu;
import dev.risas.fancybrewer.models.brewer.task.BrewingTask;
import dev.risas.fancybrewer.utilities.NBTUtil;
import dev.risas.fancybrewer.utilities.TimeUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Brewer {

    private BrewerPotionController brewerPotionController;

    private final Location location;
    private List<ItemStack> ingredients, bottles;
    private List<BrewerPotionStage> potionStages;
    private BrewerState state;
    private BrewerPotion potion;
    private BrewerPotionStage currentStage;
    private BrewerStorage storage;
    private long startedTime, estimatedTime;
    private boolean transfer;

    public Brewer(BrewerPotionController brewerPotionController, Location location) {
        this.brewerPotionController = brewerPotionController;
        this.location = location;
        this.state = BrewerState.IDLE;
        this.ingredients = new ArrayList<>();
        this.bottles = new ArrayList<>();
        this.potionStages = new ArrayList<>();
        this.storage = new BrewerStorage();
        this.transfer = true;
    }

    public void startBrewing(FancyBrewerPlugin plugin) {
        BrewingTask brewingPotionStageTask = new BrewingTask(plugin, this);
        brewingPotionStageTask.start();

        if (currentStage == null) {
            setStartedTime(System.currentTimeMillis());
            setEstimatedTime(getEstimatedTimeByPotionType());
            setCurrentStage(BrewerPotionStage.AWKWARD);
            decrementBottle();
            decrementIngredient(currentStage.getIndex());
        }
    }

    public void checkAndSetPotionType() {
        for (BrewerPotion potion : brewerPotionController.getPotions().values()) {
            if (potion.getIngredients().equals(getIngredientsMaterial())) {
                setPotion(potion);
                setPotionStages(potion.getStages());
                break;
            }
        }

        if (potion != null && !potion.getIngredients().equals(getIngredientsMaterial())) {
            setEstimatedTime(0);
            setPotion(null);
            setCurrentStage(null);
            resetStage();
        }
    }

    public BrewerPotion checkPotionType() {
        for (BrewerPotion potion : brewerPotionController.getPotions().values()) {
            if (potion.getIngredients().equals(getIngredientsMaterial())) {
                return potion;
            }
        }
        return null;
    }

    public ItemStack getIngredient() {
        return ingredients.get(0);
    }

    public void addIngredient(ItemStack itemStack) {
        ingredients.add(NBTUtil.serializeAntiDupeItem(itemStack));
        this.checkAndSetPotionType();
    }

    public void removeIngredient(Player player, ItemStack itemStack) {
        player.getInventory().addItem(NBTUtil.deserializeAntiDupeItem(itemStack));
        ingredients.remove(0);
        this.checkAndSetPotionType();
    }

    public void decrementIngredient(int index) {
        ItemStack itemStack = ingredients.get(index);

        if (itemStack != null) {
            int amount = itemStack.getAmount();

            if (amount <= 1) {
                ingredients.set(index, new ItemStack(Material.AIR));
            }
            else {
                itemStack.setAmount(amount - 1);
            }
        }
    }

    public boolean hasFullIngredients() {
        return ingredients.size() >= 4;
    }

    public boolean hasMinBottles() {
        return getBottlesAmount() >= 3;
    }

    public boolean hasFullBottles() {
        return getBottlesAmount() >= 192;
    }

    public ItemStack getBottle() {
        return bottles.get(0);
    }

    public int getBottlesAmount() {
        int glassBottles = 0;

        for (ItemStack itemStack : bottles) {
            glassBottles = itemStack.getAmount() + glassBottles;
        }

        return glassBottles;
    }

    public void addBottle(ItemStack itemStack) {
        bottles.add(NBTUtil.serializeAntiDupeItem(itemStack));
        this.checkAndSetPotionType();
    }

    public void removeBottle(Player player, ItemStack itemStack) {
        player.getInventory().addItem(NBTUtil.deserializeAntiDupeItem(itemStack));
        bottles.remove(0);
        this.checkAndSetPotionType();
    }

    public void decrementBottle() {
        int index = 0;
        ItemStack bottle = bottles.get(index);
        int actualAmount = bottle.getAmount();

        if (actualAmount <= 3) {
            bottles.remove(index);

            if (bottles.size() > 1) {
                int newAmount = actualAmount - 3;

                ItemStack nextBottle = bottles.get(index);
                nextBottle.setAmount(nextBottle.getAmount() + (newAmount));
            }
        }
        else {
            bottle.setAmount(actualAmount - 3);
        }
    }

    public String getEstimatedTimeRemainingFormatted() {
        if (potion == null) return "N/A";
        if (estimatedTime == 0) return TimeUtil.formatMillis(getEstimatedTimeByPotionType());
        return TimeUtil.formatMillis(getEstimatedTimeRemaining());
    }

    public long getEstimatedTimeRemaining() {
        return estimatedTime - (System.currentTimeMillis() - startedTime);
    }

    public long getEstimatedTimeByPotionType() {
        return 5 * potionStages.size() * (getBottlesAmount() / 3) * 1000L;
    }

    public List<Material> getIngredientsMaterial() {
        List<Material> materials = new ArrayList<>();
        ingredients.forEach(itemStack -> materials.add(itemStack.getType()));
        return materials;
    }

    public boolean isFinalStage() {
        return potionStages.get(potionStages.size() - 1).equals(currentStage);
    }

    public BrewerPotionStage getNextStage() {
        try {
            return potionStages.get(currentStage.getIndex() + 1);
        } catch (IndexOutOfBoundsException e) {
            return BrewerPotionStage.AWKWARD;
        }
    }

    public boolean hasHopperDown() {
        return location.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.HOPPER);
    }

    public Inventory getHopperInventory() {
        return ((Hopper) location.getBlock().getRelative(BlockFace.DOWN).getState()).getInventory();
    }

    public void resetStage() {
        for (BrewerPotionStage stage : this.getPotionStages()) {
            stage.resetStageCooldown();
        }
    }

    public void resetIngredientAir() {
        ingredients.removeIf(itemStack -> itemStack.getType() == Material.AIR);
    }

    public void resetBrewer() {
        this.resetStage();
        this.setPotion(null);
        this.setCurrentStage(null);
        this.setPotionStages(new ArrayList<>());
        this.setStartedTime(0);
        this.setEstimatedTime(0);
        this.setState(BrewerState.IDLE);
        this.resetIngredientAir();
    }

    public void open(Player player, FancyBrewerPlugin plugin) {
        BrewerMenu menu = new BrewerMenu(plugin, this);
        menu.openMenu(player, plugin);

        plugin.getInstance().getBrewerManager().addOpenedBrewer(player, this);
    }
}
