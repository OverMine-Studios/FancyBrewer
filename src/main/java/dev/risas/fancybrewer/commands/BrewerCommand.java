package dev.risas.fancybrewer.commands;

import dev.risas.fancybrewer.resources.types.LanguageResource;
import dev.risas.fancybrewer.utilities.ChatUtil;
import dev.risas.fancybrewer.utilities.command.BaseCommand;
import dev.risas.fancybrewer.utilities.command.Command;
import dev.risas.fancybrewer.utilities.command.CommandArgs;

public class BrewerCommand extends BaseCommand {

    @Command(name = "brewer", permission = "fancybrewer.command.brewer", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        ChatUtil.sendMessage(command.getSender(), LanguageResource.BREWER_COMMAND_MESSAGES_HELP);
    }
}
