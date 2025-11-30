package net.anware.minecraft.mods.colourscroller.ui.tile.components;

import net.anware.minecraft.mods.colourscroller.ui.tile.Tile;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class TextBox<T extends Tile> extends Clickable<T> {
	public TextBox(T parent, int x, int y, int w, int h, String initialString) {
		super(parent, x, y, w, h);
		this.text = new StringBuilder(initialString);
	}
	
	protected StringBuilder text;
	
	@Override
	public void draw(MatrixStack matrices, int mouse_x, int mouse_y, float delta) {
		int fill = 0x00000000;
		if (this.active) {
			fill = 0xFF000000;
		} else if (this.hover) {
			fill = 0xBB000000;
		}
		this.parent.drawBox(matrices, this.x(), this.x() + this.w, this.y(), this.y() + this.h, fill, 0xFF404040);
		this.parent.drawText(matrices, new LiteralText(this.text.toString()), this.x(), this.y() + this.h, 0xFFFFFFFF, Tile.ALIGN_LEFT);
	}
	
	@Override
	protected boolean clicked() {
		return true;
	}
	
	@Override
	public void typed(char c) {
		super.typed(c);
	}
}
