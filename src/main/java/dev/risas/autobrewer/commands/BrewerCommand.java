package dev.risas.autobrewer.commands;

import dev.risas.autobrewer.servicies.types.LanguageService;
import dev.risas.autobrewer.utilities.ChatUtil;
import dev.risas.autobrewer.utilities.command.BaseCommand;
import dev.risas.autobrewer.utilities.command.Command;
import dev.risas.autobrewer.utilities.command.CommandArgs;

public class BrewerCommand extends BaseCommand {

    @Command(name = "brewer", permission = "autobrewer.command.brewer", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        ChatUtil.sendMessage(command.getSender(), LanguageService.BREWER_COMMAND_MESSAGES_HELP);
    }
}
