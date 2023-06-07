package dev.risas.automaticbrewer;

import dev.risas.automaticbrewer.commands.BrewerCommand;
import dev.risas.automaticbrewer.listeners.BrewerListener;
import dev.risas.automaticbrewer.models.BrewerManager;
import dev.risas.automaticbrewer.utilities.command.CommandManager;
import dev.risas.automaticbrewer.utilities.file.FileConfig;
import dev.risas.automaticbrewer.utilities.menu.ButtonListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class AutomaticBrewer extends JavaPlugin {

    private FileConfig brewerFile, languageFile;

    private CommandManager commandManager;
    private BrewerManager brewerManager;

    @Override
    public void onEnable() {
        this.brewerFile = new FileConfig(this, "brewer.yml");
        this.languageFile = new FileConfig(this, "language.yml");

        this.commandManager = new CommandManager(this);
        this.brewerManager = new BrewerManager(this);

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ButtonListener(this), this);
        pluginManager.registerEvents(new BrewerListener(this), this);

        commandManager.registerCommands(new BrewerCommand(this));
    }

    @Override
    public void onDisable() {
        this.brewerManager.onDisable();
    }

    public void onReload() {
        this.brewerFile.reload();
        this.languageFile.reload();
        this.brewerManager.loadBrewerItem();
    }
}
