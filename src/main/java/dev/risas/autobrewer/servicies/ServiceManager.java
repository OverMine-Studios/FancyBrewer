package dev.risas.autobrewer.servicies;

import dev.risas.autobrewer.servicies.types.ConfigService;
import dev.risas.autobrewer.servicies.types.LanguageService;
import dev.risas.autobrewer.utilities.plugin.AutoBrewer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ServiceManager {

    private final AutoBrewer plugin;
    private final List<Service> services;

    public ServiceManager(AutoBrewer plugin) {
        this.plugin = plugin;
        this.services = new ArrayList<>();
        this.services.add(new ConfigService());
        this.services.add(new LanguageService());
        this.initialize();
    }

    public void initialize() {
        services.forEach(service -> service.initialize(plugin));
    }
}
