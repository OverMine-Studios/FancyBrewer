package dev.risas.fancybrewer.commands.subcommands;

import dev.risas.fancybrewer.utilities.ChatUtil;
import dev.risas.fancybrewer.utilities.command.BaseCommand;
import dev.risas.fancybrewer.utilities.command.Command;
import dev.risas.fancybrewer.utilities.command.CommandArgs;
import dev.risas.fancybrewer.models.plugin.FancyBrewer;

public class BrewerReloadCommand extends BaseCommand {

    private final FancyBrewer plugin;

    public BrewerReloadCommand(FancyBrewer plugin) {
        this.plugin = plugin;
    }

    @Command(name = "brewer.reload", permission = "fancybrewer.command.brewer.reload", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        plugin.onReload();
        ChatUtil.sendMessage(command.getSender(), "&aFancyBrewer has been reloaded.");
    }
}
