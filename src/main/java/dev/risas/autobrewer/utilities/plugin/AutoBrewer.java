package dev.risas.autobrewer.utilities.plugin;

import dev.risas.autobrewer.AutoBrewerPlugin;
import dev.risas.autobrewer.commands.BrewerCommand;
import dev.risas.autobrewer.commands.subcommands.BrewerGiveCommand;
import dev.risas.autobrewer.commands.subcommands.BrewerReloadCommand;
import dev.risas.autobrewer.listeners.BrewerListener;
import dev.risas.autobrewer.models.BrewerManager;
import dev.risas.autobrewer.servicies.ServiceManager;
import dev.risas.autobrewer.servicies.types.ConfigService;
import dev.risas.autobrewer.utilities.ChatUtil;
import dev.risas.autobrewer.utilities.command.CommandManager;
import dev.risas.autobrewer.utilities.file.FileConfig;
import dev.risas.autobrewer.utilities.menu.ButtonListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.HashMap;
import java.util.Map;

@Getter
public class AutoBrewer {

    private final AutoBrewerPlugin plugin;
    private final Map<String, FileConfig> files;

    private ServiceManager serviceManager;
    private CommandManager commandManager;
    private BrewerManager brewerManager;

    public AutoBrewer(AutoBrewerPlugin plugin) {
        this.plugin = plugin;
        this.files = new HashMap<>();
    }

    public void onEnable() {
        // Load files
        files.put("config", new FileConfig(plugin, "config.yml"));
        files.put("language", new FileConfig(plugin, "language.yml"));
        files.put("brewer-data", new FileConfig(plugin, "data/brewer-data.yml"));

        // Load managers
        this.serviceManager = new ServiceManager(this);

        Plugin newPlugin = new Plugin(ConfigService.LICENSE, plugin);

        if (newPlugin.isEnabled()) {
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
        }

        ChatUtil.logger(new String[]{
                ChatUtil.NORMAL_LINE,
                "&9&l" + plugin.getDescription().getName() + " &7version &f" + plugin.getDescription().getVersion(),
                "",
                "&7Welcome! Your Discord Tag is &9" + newPlugin.getDiscordName(),
                "&7License Status: " + (newPlugin.isEnabled() ? "&aON" : "&cOFF"),
                "",
                "&7For support join at &9https://risas.me/discord",
                ChatUtil.NORMAL_LINE
        });
    }

    public void onReload() {
        files.values().forEach(FileConfig::reload);
        serviceManager.initialize();
    }

    public FileConfig getFile(String name) {
        return files.get(name);
    }
}
