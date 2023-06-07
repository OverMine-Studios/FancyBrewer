package dev.risas.automaticbrewer.utilities;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

import java.util.UUID;

@UtilityClass
public class CooldownUtil {

    @Getter
    private final Table<UUID, String, Long> cooldowns = HashBasedTable.create();

    public boolean hasCooldown(Player player, String name) {
        return cooldowns.contains(player.getUniqueId(), name) && cooldowns.get(player.getUniqueId(), name) > System.currentTimeMillis();
    }

    public void setCooldown(Player player, String name, long time) {
        cooldowns.put(player.getUniqueId(), name, System.currentTimeMillis() + time);
    }

    public long getCooldown(Player player, String name) {
        return cooldowns.get(player.getUniqueId(), name) - System.currentTimeMillis();
    }

    public String getCooldownFormatted(Player player, String name) {
        return TimeUtil.formatMillis(getCooldown(player, name));
    }

    public void removeCooldown(Player player, String name) {
        cooldowns.remove(player.getUniqueId(), name);
    }
}
