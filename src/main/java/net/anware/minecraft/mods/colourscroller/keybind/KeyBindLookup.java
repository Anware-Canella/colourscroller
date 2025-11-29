package net.anware.minecraft.mods.colourscroller.keybind;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.anware.minecraft.mods.colourscroller.ui.screen.SettingScreen;
import net.anware.minecraft.mods.colourscroller.util.File;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.anware.minecraft.mods.colourscroller.util.GameUtil.CLIENT;

/*
    The keybind registry
 */

public class KeyBindLookup {
    
    public static final Map<String, KeyBind> REGISTERED_KEY_BIND = new HashMap<>();
    public static final Map<Integer, List<KeyBind>> KEYBIND_LOOKUP = new HashMap<>();
    
    public static void register(KeyBind keyBind) {
        if (REGISTERED_KEY_BIND.containsKey(keyBind.id)) return;
        REGISTERED_KEY_BIND.put(keyBind.id, keyBind);
    }
    
    public static KeyBind get(String id) {
        return REGISTERED_KEY_BIND.get(id);
    }
    
    public static void updateKeybindLookup(@NotNull KeyBind keyBind, @Nullable KeySequence oldSeq) {
        // remove old key->keybinds
        if (oldSeq != null) {
            for (int k : oldSeq) {
                KEYBIND_LOOKUP.computeIfPresent(k, (key, list) -> {
                    list.remove(keyBind);
                    return list.isEmpty() ? null : list;
                });
            }
        }
        
        // add new key->keybinds
        for (int k : keyBind.keySequence) {
            KEYBIND_LOOKUP.computeIfAbsent(k, l -> new ArrayList<>()).add(keyBind);
        }
        
        // save changes
        save();
    }
    
    public static void onKeyEvent(long window, int key, int scancode, int action, int mods) {
        List<KeyBind> keyBinds = KEYBIND_LOOKUP.get(key);
        if (keyBinds != null && !keyBinds.isEmpty()) {
            for (KeyBind k : keyBinds) k.onKeyEvent(key, action);
        }
    }
    
    public static void load() {
	    List<File> list = KEYBIND_CONFIG_FILE.getList("keys");
        for (File entry : list) {
            JsonObject e0 = entry.root;
            
            String id = e0.get("id").getAsString();
            KeyBind bind = REGISTERED_KEY_BIND.get(id);
            if (bind == null) continue;
            
            JsonArray arr = e0.getAsJsonArray("key");
            List<Integer> keys = new ArrayList<>();
            for (JsonElement e1 : arr) {
                keys.add(e1.getAsInt());
            }
            
            bind.setKeySeq(new KeySequence(keys));
        }
    }
    
    public static void save() {
        List<Object> list = new ArrayList<>();
        for (KeyBind bind : REGISTERED_KEY_BIND.values()) {
            list.add(bind.serialize());
        }
        KEYBIND_CONFIG_FILE.put("keys", list);
    }
    
    // the keys
    protected static final Path KEYBIND_CONFIG_PATH = File.CONFIG_PATH.resolve("keybind.json");
    protected static final File KEYBIND_CONFIG_FILE = new File(KEYBIND_CONFIG_PATH);
    
    public static final KeyBind OPEN_CONFIG = new KeyBind("open config", new KeySequence(GLFW.GLFW_KEY_O)) {
        @Override
        public void onPress() {
            if (CLIENT.currentScreen == null) CLIENT.openScreen(new SettingScreen());
        }
    };
    public static final KeyBind SCROLL_SINGLE = new KeyBind("scroll single", new KeySequence(GLFW.GLFW_KEY_LEFT_CONTROL, GLFW.GLFW_KEY_G));
    public static final KeyBind SCROLL_HOTBAR = new KeyBind("scroll hotbar", new KeySequence(GLFW.GLFW_KEY_LEFT_CONTROL, GLFW.GLFW_KEY_F));
}