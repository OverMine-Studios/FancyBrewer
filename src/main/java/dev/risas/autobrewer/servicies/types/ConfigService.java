package dev.risas.autobrewer.servicies.types;

import com.cryptomorin.xseries.XMaterial;
import dev.risas.autobrewer.servicies.Service;
import dev.risas.autobrewer.utilities.file.FileConfig;
import dev.risas.autobrewer.utilities.item.ItemBuilder;
import dev.risas.autobrewer.utilities.plugin.AutoBrewer;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ConfigService extends Service {

    public static String LICENSE;
    public static int PLUGIN_ID;
    public static ItemStack BREWING_ITEM;
    public static String BREWING_MENU_TITLE;
    public static int BREWING_MENU_ROWS;
    public static ItemStack BREWING_MENU_BUTTONS_START_BREWING, BREWING_MENU_BUTTONS_START_NOT_BREWING;
    public static List<String> BREWING_MENU_BUTTONS_START_BREWING_STAGE_POTION_TYPES;
    public static int BREWING_MENU_BUTTONS_START_SLOT;
    public static ItemStack BREWING_MENU_BUTTONS_STORAGE;
    public static int BREWING_MENU_BUTTONS_STORAGE_SLOT, BREWING_MENU_BUTTONS_DISPLAY_SLOT;
    public static ItemStack BREWING_MENU_BUTTONS_TRANSFER;
    public static int BREWING_MENU_BUTTONS_TRANSFER_SLOT;
    public static int BREWING_MENU_BUTTONS_BOTTLE_1_SLOT, BREWING_MENU_BUTTONS_BOTTLE_2_SLOT, BREWING_MENU_BUTTONS_BOTTLE_3_SLOT;
    public static int BREWING_MENU_BUTTONS_INGREDIENT_1_SLOT, BREWING_MENU_BUTTONS_INGREDIENT_2_SLOT, BREWING_MENU_BUTTONS_INGREDIENT_3_SLOT, BREWING_MENU_BUTTONS_INGREDIENT_4_SLOT;

    @Override
    public void initialize(AutoBrewer plugin) {
        FileConfig configFile = plugin.getFile("config");
        LICENSE = configFile.getString("license");
        PLUGIN_ID = configFile.getInt("plugin-id");
        BREWING_ITEM = new ItemBuilder(XMaterial.BREWING_STAND.parseMaterial())
                .setName(configFile.getString("brewer-item.name"))
                .setLore(configFile.getStringList("brewer-item.description"))
                .setEnchanted(true)
                .build();
        BREWING_MENU_TITLE = configFile.getString("brewer-menu.title");
        BREWING_MENU_ROWS = configFile.getInt("brewer-menu.rows");
        BREWING_MENU_BUTTONS_START_BREWING = new ItemBuilder(configFile.getString("brewer-menu.buttons.start.brewing.material"))
                .setName(configFile.getString("brewer-menu.buttons.start.brewing.name"))
                .setLore(configFile.getStringList("brewer-menu.buttons.start.brewing.description"))
                .setData(configFile.getInt("brewer-menu.buttons.start.brewing.data"))
                .build();
        BREWING_MENU_BUTTONS_START_NOT_BREWING = new ItemBuilder(configFile.getString("brewer-menu.buttons.start.not-brewing.material"))
                .setName(configFile.getString("brewer-menu.buttons.start.not-brewing.name"))
                .setLore(configFile.getStringList("brewer-menu.buttons.start.not-brewing.description"))
                .setData(configFile.getInt("brewer-menu.buttons.start.not-brewing.data"))
                .build();
        BREWING_MENU_BUTTONS_START_BREWING_STAGE_POTION_TYPES = configFile.getStringList("brewer-menu.buttons.start.brewing.stage-potion-types");
        BREWING_MENU_BUTTONS_START_SLOT = configFile.getInt("brewer-menu.buttons.start.slot");
        BREWING_MENU_BUTTONS_STORAGE = new ItemBuilder(configFile.getString("brewer-menu.buttons.storage.material"))
                .setName(configFile.getString("brewer-menu.buttons.storage.name"))
                .setLore(configFile.getStringList("brewer-menu.buttons.storage.description"))
                .setData(configFile.getInt("brewer-menu.buttons.storage.data"))
                .build();
        BREWING_MENU_BUTTONS_STORAGE_SLOT = configFile.getInt("brewer-menu.buttons.storage.slot");
        BREWING_MENU_BUTTONS_DISPLAY_SLOT = configFile.getInt("brewer-menu.buttons.display.slot");
        BREWING_MENU_BUTTONS_TRANSFER = new ItemBuilder(configFile.getString("brewer-menu.buttons.transfer.material"))
                .setName(configFile.getString("brewer-menu.buttons.transfer.name"))
                .setLore(configFile.getStringList("brewer-menu.buttons.transfer.description"))
                .setData(configFile.getInt("brewer-menu.buttons.transfer.data"))
                .build();
        BREWING_MENU_BUTTONS_TRANSFER_SLOT = configFile.getInt("brewer-menu.buttons.transfer.slot");
        BREWING_MENU_BUTTONS_BOTTLE_1_SLOT = configFile.getInt("brewer-menu.buttons.bottle-1.slot");
        BREWING_MENU_BUTTONS_BOTTLE_2_SLOT = configFile.getInt("brewer-menu.buttons.bottle-2.slot");
        BREWING_MENU_BUTTONS_BOTTLE_3_SLOT = configFile.getInt("brewer-menu.buttons.bottle-3.slot");
        BREWING_MENU_BUTTONS_INGREDIENT_1_SLOT = configFile.getInt("brewer-menu.buttons.ingredient-1.slot");
        BREWING_MENU_BUTTONS_INGREDIENT_2_SLOT = configFile.getInt("brewer-menu.buttons.ingredient-2.slot");
        BREWING_MENU_BUTTONS_INGREDIENT_3_SLOT = configFile.getInt("brewer-menu.buttons.ingredient-3.slot");
        BREWING_MENU_BUTTONS_INGREDIENT_4_SLOT = configFile.getInt("brewer-menu.buttons.ingredient-4.slot");
    }
}
