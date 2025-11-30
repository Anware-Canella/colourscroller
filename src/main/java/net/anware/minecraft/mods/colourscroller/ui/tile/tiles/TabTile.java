package net.anware.minecraft.mods.colourscroller.ui.tile.tiles;

import net.anware.minecraft.mods.colourscroller.ui.screen.TileScreen;
import net.anware.minecraft.mods.colourscroller.ui.tile.Tile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.List;

public class TabTile extends Tile {
	public TabTile(TileScreen screen, int x, int paddingTop, int paddingBottom, int height, List<Tab> tabs) {
		super(screen, x, paddingTop, paddingBottom);
		this.height = height;
		this.tabs = tabs;
	}
	
	protected final int height;
	protected final List<Tab> tabs;
	protected Tab hoveredTab = null;
	public static final int SPACING = 20;
	
	@Override
	protected void draw(MatrixStack matrices, int mouse_x, int mouse_y, float delta) {
		int x = this.x;
		boolean hovered = false;
		for (Tab tab : this.tabs) {
			int width = SPACING * 2 + tab.getWidth(this.getScreen());
			
			if (mouse_y >= this.y && mouse_y <= this.y + this.height) {
				if (mouse_x >= x + 2 && mouse_x < x + width - 2) {
					this.hoveredTab = tab;
					hovered = true;
				}
			}
			
			this.drawBox(matrices, x + 2, x + width - 2, this.y, this.y + this.height, this.hoveredTab == tab ? 0xCC202020 : 0xAA202020);
			this.drawText(matrices, tab.title, x + SPACING, this.y + (float) this.height / 2, 0xFFFFFFFF);
			x += width;
		}
		if (!hovered) this.hoveredTab = null;
	}
	
	@Override
	public int getContentHeight() {
		return this.height;
	}
	
	@Override
	protected boolean clicked(double mouse_x, double mouse_y) {
		if (this.hoveredTab == null) {
			return false;
		}
		playButtonSound();
		MinecraftClient.getInstance().openScreen(this.hoveredTab.factory.create());
		return true;
	}
	
	public static class Tab {
		public Tab(Text title, ScreenFactory factory) {
			this.title = title;
			this.factory = factory;
		}
		
		protected final Text title;
		protected int width = -0x808;
		protected final ScreenFactory factory;
		
		public int getWidth(TileScreen screen) {
			if (this.width == -0x808) {
				this.width = screen.getTextRenderer().getWidth(this.title);
			}
			return width;
		}
		
		@FunctionalInterface
			public interface ScreenFactory {
				TileScreen create();
			}
		}
}
