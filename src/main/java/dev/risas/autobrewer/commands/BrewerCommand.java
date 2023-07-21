package dev.risas.autobrewer.commands;

import dev.risas.autobrewer.AutoBrewer;
import dev.risas.autobrewer.models.BrewerManager;
import dev.risas.autobrewer.utilities.ChatUtil;
import dev.risas.autobrewer.utilities.JavaUtil;
import dev.risas.autobrewer.utilities.command.BaseCommand;
import dev.risas.autobrewer.utilities.command.Command;
import dev.risas.autobrewer.utilities.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BrewerCommand extends BaseCommand {

    private final AutoBrewer plugin;
    private final BrewerManager brewerManager;

    public BrewerCommand(AutoBrewer plugin) {
        this.plugin = plugin;
        this.brewerManager = plugin.getBrewerManager();
    }

    @Command(name = "brewer", permission = "fancybrewer.command.admin", inGameOnly = false)
    @Override
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 1) {
            ChatUtil.sendMessage(sender, new String[]{
                    ChatUtil.NORMAL_LINE,
                    "&b&lBrewer Command",
                    "",
                    " &f<> &7= &fRequired &7| &f[] &7= &fOptional",
                    "",
                    " &7▶ &b/brewer give <player|all> [amount] &7- &fGive a brewer to a player",
                    " &7▶ &b/brewer reload &7- &fReload the plugin",
                    ChatUtil.NORMAL_LINE
            });
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.onReload();
            ChatUtil.sendMessage(sender, "&aFancy Brewer has been reloaded.");
        }
        else if (args[0].equalsIgnoreCase("give")) {
            if (args.length < 2) {
                ChatUtil.sendMessage(sender, "&cUsage: /brewer give <player|all> [amount]");
                return;
            }

            Integer amount = 1;

            if (args.length > 2) {
                amount = JavaUtil.tryParseInt(args[2]);

                if (amount == null) {
                    ChatUtil.sendMessage(sender, "&cAmount must be a number.");
                    return;
                }

                if (amount < 1) {
                    ChatUtil.sendMessage(sender, "&cAmount must be greater than 0.");
                    return;
                }
            }

            if (args[1].equalsIgnoreCase("all")) {
                brewerManager.giveAllBrewer(amount);
                ChatUtil.sendMessage(sender, "&aYou have given all online players a brewer.");
                return;
            }

            String playerName = args[1];
            Player player = Bukkit.getPlayer(playerName);

            if (player == null) {
                ChatUtil.sendMessage(sender, "&cPlayer '" + playerName + "' not found.");
                return;
            }

            brewerManager.giveBrewer(player, amount);
            ChatUtil.sendMessage(sender, "&aYou have given " + player.getName() + " x" + amount + " fancy brewer.");
            ChatUtil.sendMessage(player, "&aYou have received a fancy brewer.");
        }
    }
}
