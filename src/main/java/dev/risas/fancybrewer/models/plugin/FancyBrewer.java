package dev.risas.fancybrewer.models.plugin;

import dev.risas.fancybrewer.FancyBrewerPlugin;
import dev.risas.fancybrewer.commands.BrewerCommand;
import dev.risas.fancybrewer.commands.subcommands.BrewerGiveCommand;
import dev.risas.fancybrewer.commands.subcommands.BrewerReloadCommand;
import dev.risas.fancybrewer.controllers.BrewerManager;
import dev.risas.fancybrewer.listeners.BrewerListener;
import dev.risas.fancybrewer.resources.ResourceManager;
import dev.risas.fancybrewer.utilities.ChatUtil;
import dev.risas.fancybrewer.utilities.command.CommandManager;
import dev.risas.fancybrewer.utilities.file.FileConfig;
import dev.risas.fancybrewer.utilities.menu.ButtonListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import java.util.HashMap;
import java.util.Map;

@Getter
public class FancyBrewer {

    private final FancyBrewerPlugin plugin;
    private final Map<String, FileConfig> files;

    private ResourceManager resourceManager;
    private CommandManager commandManager;
    private BrewerManager brewerManager;

    public FancyBrewer(FancyBrewerPlugin plugin) {
        this.plugin = plugin;
        this.files = new HashMap<>();
    }

    public void onEnable() {
        // Load files
        files.put("config", new FileConfig(plugin, "config.yml"));
        files.put("language", new FileConfig(plugin, "language.yml"));
        files.put("brewer-data", new FileConfig(plugin, "data/brewer-data.yml"));

        // Load managers
        this.resourceManager = new ResourceManager(this);
        this.commandManager = new CommandManager(plugin);
        this.brewerManager = new BrewerManager(this);

        // Register listeners
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ButtonListener(plugin), plugin);
        pluginManager.registerEvents(new BrewerListener(plugin), plugin);

        // Register commands
        commandManager.registerCommands(new BrewerCommand());
        commandManager.registerCommands(new BrewerReloadCommand(this));
        commandManager.registerCommands(new BrewerGiveCommand(this));


        ChatUtil.logger(new String[]{
                ChatUtil.NORMAL_LINE,
                "&9&lFancyBrewer &7version &f" + plugin.getDescription().getVersion(),
                "",
                "&7License Status: &aSUCCESS",
                "",
                "&7For support join at &9https://risas.me/discord",
                ChatUtil.NORMAL_LINE
        });
    }

    public void onDisable() {
        brewerManager.onDisable();
    }

    public void onReload() {
        files.values().forEach(FileConfig::reload);
        resourceManager.initialize();
    }

    public FileConfig getFile(String name) {
        return files.get(name);
    }
}
