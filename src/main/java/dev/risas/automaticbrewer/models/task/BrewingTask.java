package dev.risas.automaticbrewer.models.task;

import dev.risas.automaticbrewer.AutomaticBrewer;
import dev.risas.automaticbrewer.models.Brewer;
import dev.risas.automaticbrewer.models.BrewerPotionStage;
import dev.risas.automaticbrewer.models.BrewerState;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class BrewingTask extends BukkitRunnable {

    private final AutomaticBrewer plugin;
    private final Brewer brewer;

    public BrewingTask(AutomaticBrewer plugin, Brewer brewer) {
        this.plugin = plugin;
        this.brewer = brewer;
    }

    @Override
    public void run() {
        if (brewer.getState() != BrewerState.BREWING) {
            cancel();
            return;
        }

        BrewerPotionStage currentStage = brewer.getCurrentStage();

        if (currentStage.getStageCooldown() == 1) {
            BrewerPotionStage nextStage = brewer.getNextStage();

            if (brewer.isFinalStage()) {
                if (brewer.isTransfer() && brewer.hasHopperDown()) {
                    Inventory inventory = brewer.getHopperInventory();

                    if (inventory.firstEmpty() == -1) {
                        brewer.setTransfer(false);
                    }
                    else {
                        for (int i = 0; i < 3; i++) {
                            brewer.getHopperInventory().addItem(brewer.getPotionType().getResult());
                        }
                    }
                }

                if (brewer.getBottlesAmount() < 3 || brewer.getIngredientsAmount() <= 0) {
                    brewer.resetBrewer();
                    cancel();
                    return;
                }

                brewer.decrementBottle();
                brewer.resetStage();
            }

            brewer.setCurrentStage(nextStage);
            brewer.decrementIngredient(nextStage.getIndex());
            return;
        }

        currentStage.decrementStageCooldown();
    }

    public void start() {
        runTaskTimer(plugin, 0L, 20L);
    }
}
