package dev.risas.fancybrewer.models;

import dev.risas.fancybrewer.utilities.TimeUtil;
import lombok.Getter;

@Getter
public enum BrewerPotionStage {
    AWKWARD(0), INGREDIENT_1(1), INGREDIENT_2(2), INGREDIENT_3(3);

    @Getter private final int index;
    private final int defaultStageCooldown;
    @Getter private int stageCooldown;

    BrewerPotionStage(int index) {
        this.index = index;
        this.defaultStageCooldown = 5;
        this.stageCooldown = defaultStageCooldown;
    }

    public String getColorStage(BrewerPotionStage stage) {
        return stage == this ? "&a" : "&7";
    }

    public String getStageCooldownFormatted() {
        return TimeUtil.formatInteger(stageCooldown);
    }

    public void decrementStageCooldown() {
        stageCooldown--;
    }

    public void resetStageCooldown() {
        stageCooldown = defaultStageCooldown;
    }
}
