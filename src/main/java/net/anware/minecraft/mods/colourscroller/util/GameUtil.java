package net.anware.minecraft.mods.colourscroller.util;

import net.anware.minecraft.mods.colourscroller.Client;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class GameUtil {

    public static final MinecraftClient CLIENT = MinecraftClient.getInstance();
    public static final Text EMPTY_TEXT = Text.of("");
    
    public static Identifier getID(Item item) {
        RegistryKey<Item> itemKey = Registry.ITEM.getKey(item).orElse(null);
        if (itemKey == null) return null;
        return itemKey.getValue();
    }
    
    public static String getName(Item item) {
        Identifier id = getID(item);
        if (id == null) return null;
        return id.getPath();
    }
    
    /**
     * Converts a string ID like "minecraft:diamond" to a Minecraft Item.
     * Returns null if the item does not exist.
     */
    public static Item getItem(String id) {
        if (id == null || id.isEmpty()) return null;
        try {
	        return Registry.ITEM.get(new Identifier(id));
        } catch (Exception e) {
            Client.printError("invalid item id: " + id);
            return null;
        }
    }
}