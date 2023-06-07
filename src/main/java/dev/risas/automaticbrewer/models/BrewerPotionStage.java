package dev.risas.automaticbrewer.models;

import dev.risas.automaticbrewer.utilities.TimeUtil;
import lombok.Getter;

@Getter
public enum BrewerPotionStage {
    AWKWARD(0), INGREDIENT_1(1), INGREDIENT_2(2), INGREDIENT_3(3);

    private final int index;
    private final int defaultStageCooldown;
    private int stageCooldown;

    BrewerPotionStage(int index) {
        this.index = index;
        this.defaultStageCooldown = 5;
        this.stageCooldown = defaultStageCooldown;
    }

    public int getIndex() {
        return index;
    }

    public String getColorStage(BrewerPotionStage stage) {
        return stage == this ? "&a" : "&7";
    }

    public String getStageCooldownFormatted() {
        return TimeUtil.formatInteger(stageCooldown);
    }

    public int getStageCooldown() {
        return stageCooldown;
    }

    public void decrementStageCooldown() {
        stageCooldown--;
    }

    public void resetStageCooldown() {
        stageCooldown = defaultStageCooldown;
    }
}
