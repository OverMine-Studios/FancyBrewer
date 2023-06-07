package dev.risas.automaticbrewer.models;

import dev.risas.automaticbrewer.AutomaticBrewer;
import dev.risas.automaticbrewer.models.menu.BrewerMenu;
import dev.risas.automaticbrewer.models.task.BrewingTask;
import dev.risas.automaticbrewer.utilities.TimeUtil;
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

    private final Location location;
    private List<ItemStack> ingredients;
    private List<ItemStack> bottles;
    private List<BrewerPotionStage> potionStages;
    private BrewerState state;
    private BrewerPotionType potionType;
    private BrewerPotionStage currentStage;
    private long startedTime, estimatedTime;
    private boolean transfer;

    public Brewer(Location location) {
        this.location = location;
        this.state = BrewerState.IDLE;
        this.ingredients = new ArrayList<>();
        this.bottles = new ArrayList<>();
        this.potionStages = new ArrayList<>();
        this.transfer = true;
    }

    public void startBrewing(AutomaticBrewer plugin) {
        if (currentStage == null) {
            setStartedTime(System.currentTimeMillis());
            setEstimatedTime(getEstimatedTimeByPotionType());
            setCurrentStage(BrewerPotionStage.AWKWARD);
            decrementBottle();
            decrementIngredient(currentStage.getIndex());
        }

        BrewingTask brewingPotionStageTask = new BrewingTask(plugin, this);
        brewingPotionStageTask.start();
    }

    public void checkAndSetPotionType() {
        for (BrewerPotionType type : BrewerPotionType.values()) {
            if (type.getIngredients().equals(getIngredientsMaterial())) {
                setPotionType(type);
                setPotionStages(type.getStages());
                break;
            }
        }

        if (potionType != null && !potionType.getIngredients().equals(getIngredientsMaterial())) {
            setEstimatedTime(0);
            setPotionType(null);
            setCurrentStage(null);
            resetStage();
        }
    }

    public ItemStack getIngredient() {
        return ingredients.get(0);
    }

    public void addIngredient(ItemStack itemStack) {
        ingredients.add(itemStack.clone());
        this.checkAndSetPotionType();
    }

    public void removeIngredient() {
        ingredients.remove(0);
        this.checkAndSetPotionType();
    }

    public void decrementIngredient(int index) {
        ItemStack itemStack = ingredients.get(index);
        itemStack.setAmount(itemStack.getAmount() - 1);
    }

    public int getIngredientsAmount() {
        int ingredientsAmount = 0;

        for (ItemStack itemStack : ingredients) {
            ingredientsAmount = itemStack.getAmount() + ingredientsAmount;
        }

        return ingredientsAmount;
    }

    public boolean hasIngredients() {
        return getIngredientsAmount() > 0;
    }

    public boolean hasBottles() {
        return getBottlesAmount() > 0;
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
        bottles.add(itemStack.clone());
        this.checkAndSetPotionType();
    }

    public void removeBottle() {
        bottles.remove(0);
        this.checkAndSetPotionType();
    }

    public void decrementBottle() {
        int index = 0;
        ItemStack bottle = bottles.get(index);
        int actualAmount = bottle.getAmount();

        if (actualAmount < 3) {
            List<ItemStack> toRemove = new ArrayList<>();
            int bottleCount = 0;

            for (ItemStack nextBottle : bottles) {
                if (nextBottle == bottle) continue;

                int nextAmount = nextBottle.getAmount();
                bottleCount = bottleCount + nextAmount;

                if (nextAmount < 3) toRemove.add(nextBottle);
            }

            bottle.setAmount(actualAmount + bottleCount - 3);

            for (int i = 0; i < toRemove.size(); i++) {
                bottles.remove(1);
            }
        }
        else {
            bottle.setAmount(actualAmount - 3);

            int newAmount = bottle.getAmount();

            if (newAmount < 3) {
                int nextIndex = bottles.size() - 1;
                if (index == nextIndex) return;

                ItemStack nextBottle = bottles.get(nextIndex);
                nextBottle.setAmount(nextBottle.getAmount() + newAmount);
                bottles.remove(0);
            }
        }
    }

    public String getEstimatedTimeRemainingFormatted() {
        if (potionType == null) return "N/A";
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

    public void resetBrewer() {
        this.resetStage();
        this.setPotionType(null);
        this.setCurrentStage(null);
        this.setPotionStages(new ArrayList<>());
        this.setStartedTime(0);
        this.setEstimatedTime(0);
        this.setState(BrewerState.IDLE);

        if (getIngredientsAmount() <= 0) {
            this.setIngredients(new ArrayList<>());
        }
    }

    public void open(Player player, AutomaticBrewer plugin) {
        BrewerMenu menu = new BrewerMenu(plugin, this);
        menu.openMenu(player, plugin);
    }
}
