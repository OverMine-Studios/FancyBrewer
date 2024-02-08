package dev.risas.fancybrewer.utilities.cooldown;

import com.google.common.collect.Table;
import dev.risas.fancybrewer.FancyBrewerPlugin;
import dev.risas.fancybrewer.utilities.ChatUtil;
import org.bukkit.Bukkit;

import java.util.UUID;

public class CooldownTask implements Runnable {

    private final FancyBrewerPlugin plugin;
    private int id;

    public CooldownTask(FancyBrewerPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (CooldownUtil.getCooldowns().isEmpty()) {
            CooldownUtil.setCooldownTask(null);
            cancel();
            return;
        }

        try {
            for (Table.Cell<UUID, String, Long> table : CooldownUtil.getCooldowns().cellSet()) {
                UUID uuid = table.getRowKey();
                String name = table.getColumnKey();

                if (CooldownUtil.getCooldown(uuid, name) <= 0) {
                    CooldownUtil.removeCooldown(uuid, name);
                }
            }
        }
        catch (Exception ex) {
            ChatUtil.logger("Error while running cooldown task: " + ex.getMessage());
        }
    }

    public void start() {
        this.id = Bukkit.getScheduler().runTaskTimer(plugin, this, 0L, 20L)
                .getTaskId();
    }

    public void cancel() {
        Bukkit.getScheduler().cancelTask(id);
    }
}
