package dev.risas.autobrewer.commands.subcommands;

import dev.risas.autobrewer.models.BrewerManager;
import dev.risas.autobrewer.servicies.types.LanguageService;
import dev.risas.autobrewer.utilities.ChatUtil;
import dev.risas.autobrewer.utilities.JavaUtil;
import dev.risas.autobrewer.utilities.command.BaseCommand;
import dev.risas.autobrewer.utilities.command.Command;
import dev.risas.autobrewer.utilities.command.CommandArgs;
import dev.risas.autobrewer.utilities.plugin.AutoBrewer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BrewerGiveCommand extends BaseCommand {

    private final BrewerManager brewerManager;

    public BrewerGiveCommand(AutoBrewer plugin) {
        this.brewerManager = plugin.getBrewerManager();
    }

    @Command(name = "brewer.give", permission = "autobrewer.command.brewer.give", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 1) {
            ChatUtil.sendMessage(sender, "&cUsage: /brewer give <player|all> [amount]");
            return;
        }

        Integer amount;

        if (args.length > 1) {
            amount = JavaUtil.tryParseInt(args[1]);

            if (amount == null) {
                ChatUtil.sendMessage(sender, "&cAmount must be a number.");
                return;
            }

            if (amount < 1) {
                ChatUtil.sendMessage(sender, "&cAmount must be greater than 0.");
                return;
            }
        }
        else {
            amount = 1;
        }

        if (args[0].equalsIgnoreCase("all")) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                brewerManager.giveBrewer(player, amount);
                ChatUtil.sendMessage(player, LanguageService.BREWER_COMMAND_MESSAGES_RECEIVED_PLAYER
                        .replace("<amount>", amount.toString()));
            });
            ChatUtil.sendMessage(sender, LanguageService.BREWER_COMMAND_MESSAGES_GIVE_ALL
                    .replace("<amount>", amount.toString()));
            return;
        }

        String playerName = args[0];
        Player player = Bukkit.getPlayer(playerName);

        if (player == null) {
            ChatUtil.sendMessage(sender, "&cPlayer '" + playerName + "' not found.");
            return;
        }

        brewerManager.giveBrewer(player, amount);
        ChatUtil.sendMessage(sender, LanguageService.BREWER_COMMAND_MESSAGES_GIVE_PLAYER
                .replace("<player>", player.getName())
                .replace("<amount>", amount.toString()));
        ChatUtil.sendMessage(player, LanguageService.BREWER_COMMAND_MESSAGES_RECEIVED_PLAYER
                .replace("<amount>", amount.toString()));
    }
}
