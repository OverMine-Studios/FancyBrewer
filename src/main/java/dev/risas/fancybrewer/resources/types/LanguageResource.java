package dev.risas.fancybrewer.resources.types;

import dev.risas.fancybrewer.models.plugin.FancyBrewer;
import dev.risas.fancybrewer.resources.Resource;
import dev.risas.fancybrewer.utilities.file.FileConfig;

public class LanguageResource extends Resource {

    public static String[] BREWER_COMMAND_MESSAGES_HELP, BREWER_MESSAGES_CANT_ADD_MATERIALS;
    public static String BREWER_COMMAND_MESSAGES_GIVE_ALL, BREWER_COMMAND_MESSAGES_GIVE_PLAYER, BREWER_COMMAND_MESSAGES_RECEIVED_PLAYER;
    public static String BREWER_MESSAGES_PLACED, BREWER_MESSAGES_REMOVED, BREWER_MESSAGES_CANT_REMOVE_WORKING,
            BREWER_MESSAGES_CANT_REMOVE_HAS_BOTTLES, BREWER_MESSAGES_CANT_REMOVE_HAS_INGREDIENTS, BREWER_MESSAGES_FULL_BOTTLES,
            BREWER_MESSAGES_NEED_BOTTLES, BREWER_MESSAGES_NEED_INGREDIENTS;

    @Override
    public void initialize(FancyBrewer plugin) {
        FileConfig languageFile = plugin.getFile("language");
        BREWER_COMMAND_MESSAGES_HELP = languageFile.getStringList("brewer-command-messages.help")
                .toArray(new String[0]);
        BREWER_MESSAGES_CANT_ADD_MATERIALS = languageFile.getStringList("brewer-messages.cant-add-materials")
                .toArray(new String[0]);
        BREWER_COMMAND_MESSAGES_GIVE_ALL = languageFile.getString("brewer-command-messages.give.all");
        BREWER_COMMAND_MESSAGES_GIVE_PLAYER = languageFile.getString("brewer-command-messages.give.player");
        BREWER_COMMAND_MESSAGES_RECEIVED_PLAYER = languageFile.getString("brewer-command-messages.received.player");
        BREWER_MESSAGES_PLACED = languageFile.getString("brewer-messages.placed");
        BREWER_MESSAGES_REMOVED = languageFile.getString("brewer-messages.removed");
        BREWER_MESSAGES_CANT_REMOVE_WORKING = languageFile.getString("brewer-messages.cant-remove.working");
        BREWER_MESSAGES_CANT_REMOVE_HAS_BOTTLES = languageFile.getString("brewer-messages.cant-remove.has-bottles");
        BREWER_MESSAGES_CANT_REMOVE_HAS_INGREDIENTS = languageFile.getString("brewer-messages.cant-remove.has-ingredients");
        BREWER_MESSAGES_FULL_BOTTLES = languageFile.getString("brewer-messages.full-bottles");
        BREWER_MESSAGES_NEED_BOTTLES = languageFile.getString("brewer-messages.need-bottles");
        BREWER_MESSAGES_NEED_INGREDIENTS = languageFile.getString("brewer-messages.need-ingredients");
    }
}
