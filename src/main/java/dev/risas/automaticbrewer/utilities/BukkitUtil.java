package dev.risas.automaticbrewer.utilities;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

@UtilityClass
public class BukkitUtil {

    public String getLocation(Location location) {
        if (location == null) return null;
        return location.getBlockX() + ", "
                + location.getBlockY() + ", "
                + location.getBlockZ() + " ("
                + location.getWorld().getName() + ")";
    }

    public String serializeBlockLocation(Location location) {
        if (location == null) return null;
        return location.getWorld().getName() + ":" +
                location.getBlockX() + ":" +
                location.getBlockY() + ":" +
                location.getBlockZ();
    }

    public Location deserializeBlockLocation(String data) {
        if (data == null) return null;

        String[] splittedData = data.split(":");

        if (splittedData.length < 4) return null;

        World world = Bukkit.getWorld(splittedData[0]);
        double x = Double.parseDouble(splittedData[1]);
        double y = Double.parseDouble(splittedData[2]);
        double z = Double.parseDouble(splittedData[3]);

        return new Location(world, x, y, z);
    }
}
