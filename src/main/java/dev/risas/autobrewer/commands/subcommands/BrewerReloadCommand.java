package dev.risas.autobrewer.commands.subcommands;

import dev.risas.autobrewer.utilities.ChatUtil;
import dev.risas.autobrewer.utilities.command.BaseCommand;
import dev.risas.autobrewer.utilities.command.Command;
import dev.risas.autobrewer.utilities.command.CommandArgs;
import dev.risas.autobrewer.utilities.plugin.AutoBrewer;

public class BrewerReloadCommand extends BaseCommand {

    private final AutoBrewer plugin;

    public BrewerReloadCommand(AutoBrewer plugin) {
        this.plugin = plugin;
    }

    @Command(name = "brewer.reload", permission = "autobrewer.command.brewer.reload", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        plugin.onReload();
        ChatUtil.sendMessage(command.getSender(), "&aAutoBrewer has been reloaded.");
    }
}
