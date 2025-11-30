package dev.risas.fancybrewer.utilities;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

@UtilityClass
public class BukkitUtil {

    public int SERVER_VERSION_INT = Integer.parseInt(Bukkit.getBukkitVersion().split("\\.")[1]
            .split("-")[0]);
}
