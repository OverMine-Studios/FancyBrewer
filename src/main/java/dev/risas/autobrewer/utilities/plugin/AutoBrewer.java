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
import okhttp3.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Getter
public class AutoBrewer {

    private final AutoBrewerPlugin plugin;
    private File pluginFile;
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

        boolean enabled = false;
        JSONObject obj = null;

        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n    \"license\": \""+ ConfigService.LICENSE +"\",\n    \"product\": \"AutoBrewer\",\n    \"version\": \""+ plugin.getDescription().getVersion() +"\"\n}");
            Request request = new Request.Builder()
                    .url("http://license.risas.me/api/client")
                    .method("POST", body)
                    .addHeader("Authorization", "42RDgHeygEg9pphK1Gxsj7VZEDURZEnF")
                    .build();

            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();

            if (responseBody != null) obj = new JSONObject(responseBody.string());
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to connect to the license server.");
        }

        this.pluginFile = new File("plugins/AutoBrewer-" + plugin.getDescription().getVersion() + ".jar");

        if (ConfigService.PLUGIN_ID == 0) {
            ChatUtil.logger("&cPlugin ID not found in config.yml, please contact the developer.");
        }

        if (obj != null && obj.has("status_msg")
                && obj.has("status_id") && obj.has("status_overview")
                && obj.getString("status_overview").equalsIgnoreCase("success")
                && ConfigService.PLUGIN_ID != 0 && ConfigService.PLUGIN_ID == pluginFile.length()) {
            enabled = true;
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
                "&7License Status: " + (enabled ? "&aON" : "&cOFF"),
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
