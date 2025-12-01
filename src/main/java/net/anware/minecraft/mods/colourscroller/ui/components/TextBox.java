package net.anware.minecraft.mods.colourscroller.ui.components;

import net.anware.minecraft.mods.colourscroller.ui.Component;
import net.anware.minecraft.mods.colourscroller.ui.Tile;
import net.minecraft.client.util.math.MatrixStack;

public class TextBox<T extends Tile> extends Component<T> {
	public TextBox(T parent, int x, int y, int w, int h, String text) {
		super(parent, x, y, w, h);
		this.text = new StringBuilder(text);
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
		this.parent.drawText(matrices, this.text.toString(), this.x(), this.y() + this.h, 0xFFFFFFFF, net.anware.minecraft.mods.colourscroller.gui.Tile.ALIGN_LEFT);
	}
	
	@Override
	public boolean clicked(double mouse_x, double mouse_y) {
		return true;
	}
	
	@Override
	public boolean onTyped(char c) {
		return true;
	}
}
