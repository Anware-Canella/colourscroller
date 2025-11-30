package net.anware.minecraft.mods.colourscroller.ui.tile.tiles;

import net.anware.minecraft.mods.colourscroller.ui.screen.TileScreen;
import net.anware.minecraft.mods.colourscroller.ui.tile.Tile;
import net.minecraft.client.util.math.MatrixStack;

public class TabTile extends Tile {
	public TabTile(TileScreen screen, int x, int paddingTop, int paddingBottom, int height) {
		super(screen, x, paddingTop, paddingBottom);
		this.height = height;
	}
	
	protected final int height;
	
	@Override
	protected void draw(MatrixStack matrices, int mouse_x, int mouse_y, float delta) {
		
	}
	
	@Override
	public int getContentHeight() {
		return this.height;
	}
}
