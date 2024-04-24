package dev.risas.fancybrewer.utilities;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class SerializeUtil {

    public String serializeBlockLocation(Location location) {
        if (location == null || location.getWorld() == null) return null;
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

    public String serializeItemStackList(List<ItemStack> itemStacks) {
        if (itemStacks == null || itemStacks.isEmpty()) return "";

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(itemStacks.size());

            for (ItemStack item : itemStacks) {
                dataOutput.writeObject(item);
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        }
        catch (Exception e) {
            throw new IllegalStateException("Unable to serialize ItemStackList.", e);
        }
    }

    public List<ItemStack> deserializeItemStackList(String data) {
        if (data == null || data.isEmpty()) return new ArrayList<>();

        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);

            List<ItemStack> itemStacks = new ArrayList<>();
            int size = dataInput.readInt();

            for (int i = 0; i < size; i++) {
                itemStacks.add((ItemStack) dataInput.readObject());
            }

            dataInput.close();
            return itemStacks;
        }
        catch (Exception e) {
            throw new IllegalStateException("Unable to deserialize ItemStackList.", e);
        }
    }
}
