package net.anware.minecraft.mods.colourscroller;

import net.anware.minecraft.mods.colourscroller.keybind.KeyBindLookup;
import net.anware.minecraft.mods.colourscroller.keybind.KeyWrapper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

public class Client implements ClientModInitializer {

    public static final String MOD_ID = "colourscroller";
    
    @Override
    public void onInitializeClient() {
        KeyBindLookup.load();
        ClientLifecycleEvents.CLIENT_STARTED.register(KeyWrapper.INSTANCE::wrap);
    }
}
