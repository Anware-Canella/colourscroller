package net.anware.minecraft.mods.colourscroller.ui.screen;

import net.anware.minecraft.mods.colourscroller.scroll.Scroll;
import net.anware.minecraft.mods.colourscroller.scroll.ScrollLookup;
import net.anware.minecraft.mods.colourscroller.ui.tile.Tile;
import net.anware.minecraft.mods.colourscroller.ui.tile.tiles.PlainTextTile;
import net.anware.minecraft.mods.colourscroller.ui.tile.tiles.ScrollTile;
import net.minecraft.text.LiteralText;

import java.util.ArrayList;
import java.util.List;

public class ScrollConfigScreen extends TileScreen {
	public ScrollConfigScreen() {
		super(new LiteralText("Scrolls"));
		
		List<Tile> tiles = new ArrayList<>();
		tiles.add(new PlainTextTile(this, 20, 20, 20, new LiteralText("Colour Scroller Configs"), 0xFFFFFFFF));
		for (Scroll scroll : ScrollLookup.SCROLL_REGISTRY.values()) {
			tiles.add(new ScrollTile(this, 30, 10, 0, scroll, 250, 20));
		}
		this.addTiles(tiles);
	}
}