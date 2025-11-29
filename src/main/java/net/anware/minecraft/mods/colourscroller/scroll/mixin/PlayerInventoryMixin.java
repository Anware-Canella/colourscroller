package net.anware.minecraft.mods.colourscroller.scroll.mixin;

import net.anware.minecraft.mods.colourscroller.keybind.KeyBindLookup;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

    @Inject(method = "scrollInHotbar", at = @At("HEAD"), cancellable = true)
    private void wrapScrollColour(double scrollAmount, CallbackInfo ci) {
        if (this.scrollColour()) ci.cancel();
    }

    @Unique
    private boolean scrollColour() {
        if (KeyBindLookup.SCROLL_SINGLE.isPressed()) {

            return true;
        } else if (KeyBindLookup.SCROLL_HOTBAR.isPressed()) {

            return true;
        }
        return false;
    }
}
