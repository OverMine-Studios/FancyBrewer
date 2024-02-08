package dev.risas.fancybrewer;

import dev.risas.fancybrewer.models.plugin.FancyBrewer;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class FancyBrewerPlugin extends JavaPlugin {

    private FancyBrewer instance;

    @Override
    public void onEnable() {
        this.instance = new FancyBrewer(this);
        this.instance.onEnable();
    }
}
