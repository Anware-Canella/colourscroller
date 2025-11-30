package net.anware.minecraft.mods.colourscroller.ui.screen;

import net.anware.minecraft.mods.colourscroller.keybind.KeyBindLookup;
import net.anware.minecraft.mods.colourscroller.ui.tile.tiles.KeyConfigTile;
import net.anware.minecraft.mods.colourscroller.ui.tile.tiles.PlainTextTile;
import net.anware.minecraft.mods.colourscroller.ui.tile.tiles.TabTile;
import net.anware.minecraft.mods.colourscroller.ui.tile.tiles.TabTile.Tab;
import net.minecraft.text.LiteralText;

import java.util.List;

public class ConfigScreen extends TileScreen {
    public ConfigScreen() {
        super(new LiteralText("Colour Scroller Config"));
        
        this.addTiles(
            new PlainTextTile(this, 20, 0, 20, new LiteralText("Colour Scroller Configs"), 0xFFFFFFFF),
            getTabTile(this),
            new KeyConfigTile(this, 30, 0, 5, 20, KeyBindLookup.OPEN_CONFIG),
            new KeyConfigTile(this, 30, 0, 5, 20, KeyBindLookup.SCROLL_HOTBAR),
            new KeyConfigTile(this, 30, 0, 5, 20, KeyBindLookup.SCROLL_SINGLE)
        );
    }
    
    protected static TabTile getTabTile(TileScreen screen) {
        return new TabTile(screen, 20, 0, 10, 20, List.of(
            new Tab(new LiteralText("Config"), ConfigScreen::new),
            new Tab(new LiteralText("Scrolls"), ScrollConfigScreen::new)
        ));
    }
}