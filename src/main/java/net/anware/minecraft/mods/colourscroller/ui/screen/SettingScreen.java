package net.anware.minecraft.mods.colourscroller.ui.screen;

import net.anware.minecraft.mods.colourscroller.keybind.KeyBindLookup;
import net.anware.minecraft.mods.colourscroller.scroll.ScrollLookup;
import net.anware.minecraft.mods.colourscroller.ui.tile.tiles.ItemListTile;
import net.anware.minecraft.mods.colourscroller.ui.tile.tiles.KeyConfigTile;
import net.anware.minecraft.mods.colourscroller.ui.tile.tiles.PlainTextTile;
import net.minecraft.text.LiteralText;

public class SettingScreen extends TileScreen {
    public SettingScreen() {
        super(new LiteralText("Colour Scroller Config"));
        
        this.addTiles(
            new PlainTextTile(this, 20, 20, 20, new LiteralText("Colour Scroller Configs"), 0xFFFFFFFF),
            new KeyConfigTile(this, 30, 0, 5, 20, KeyBindLookup.OPEN_CONFIG),
            new KeyConfigTile(this, 30, 0, 5, 20, KeyBindLookup.SCROLL_HOTBAR),
            new KeyConfigTile(this, 30, 0, 5, 20, KeyBindLookup.SCROLL_SINGLE),
            new ItemListTile(this, 30, 0, 5, ScrollLookup.find("concrete"), 250, 20)
        );
    }
}