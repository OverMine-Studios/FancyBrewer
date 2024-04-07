package dev.risas.fancybrewer.utilities;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class NBTUtil {

    public ItemStack serializeAntiDupeItem(ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setString("anti-dupe", "anti-dupe");
        return nbtItem.getItem();
    }

    public ItemStack deserializeAntiDupeItem(ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.removeKey("anti-dupe");
        return nbtItem.getItem();
    }
}
