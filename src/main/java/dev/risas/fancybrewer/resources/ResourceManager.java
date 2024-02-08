package dev.risas.fancybrewer.resources;

import dev.risas.fancybrewer.models.plugin.FancyBrewer;
import dev.risas.fancybrewer.resources.types.ConfigResource;
import dev.risas.fancybrewer.resources.types.LanguageResource;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ResourceManager {

    private final FancyBrewer plugin;
    private final List<Resource> resources;

    public ResourceManager(FancyBrewer plugin) {
        this.plugin = plugin;
        this.resources = new ArrayList<>();
        this.resources.add(new ConfigResource());
        this.resources.add(new LanguageResource());
        this.initialize();
    }

    public void initialize() {
        resources.forEach(resource -> resource.initialize(plugin));
    }
}
