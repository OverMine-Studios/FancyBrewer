package dev.risas.autobrewer;

import dev.risas.autobrewer.utilities.plugin.AutoBrewer;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class AutoBrewerPlugin extends JavaPlugin {

    private AutoBrewer instance;

    @Override
    public void onEnable() {
        this.instance = new AutoBrewer(this);
        this.instance.onEnable();
    }
}
