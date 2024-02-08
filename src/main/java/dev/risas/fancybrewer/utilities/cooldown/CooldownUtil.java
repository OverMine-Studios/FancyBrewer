package dev.risas.fancybrewer.utilities.cooldown;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import dev.risas.fancybrewer.FancyBrewerPlugin;
import dev.risas.fancybrewer.utilities.TimeUtil;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;

import java.util.UUID;

@UtilityClass
public class CooldownUtil {

    @Getter
    private final Table<UUID, String, Long> cooldowns = HashBasedTable.create();
    private CooldownTask cooldownTask;

    public boolean hasCooldown(Player player, String name) {
        return cooldowns.contains(player.getUniqueId(), name) && cooldowns.get(player.getUniqueId(), name) > System.currentTimeMillis();
    }

    public void setCooldown(FancyBrewerPlugin plugin, Player player, String name, int seconds) {
        if (cooldownTask == null) {
            startCooldownTask(plugin);
        }

        cooldowns.put(player.getUniqueId(), name, System.currentTimeMillis() + (seconds * 1000L));
    }

    public long getCooldown(UUID uuid, String name) {
        return cooldowns.get(uuid, name) - System.currentTimeMillis();
    }

    public long getCooldown(Player player, String name) {
        return cooldowns.get(player.getUniqueId(), name) - System.currentTimeMillis();
    }

    public void removeCooldown(UUID uuid, String name) {
        cooldowns.remove(uuid, name);
    }

    public void removeCooldown(Player player, String name) {
        cooldowns.remove(player.getUniqueId(), name);
    }

    public String getCooldownFormatted(Player player, String name) {
        return TimeUtil.formatMillis(getCooldown(player, name));
    }

    public void setCooldownTask(CooldownTask cooldownTask) {
        CooldownUtil.cooldownTask = cooldownTask;
    }

    public void startCooldownTask(FancyBrewerPlugin plugin) {
        cooldownTask = new CooldownTask(plugin);
        cooldownTask.start();
    }
}
