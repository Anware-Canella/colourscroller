package net.anware.minecraft.mods.colourscroller.ui.tile.components;

import net.anware.minecraft.mods.colourscroller.ui.tile.Tile;
import net.minecraft.client.util.math.MatrixStack;

public abstract class Component<T extends Tile> {
	public Component(T parent, int x, int y, int w, int h) {
		this.parent = parent;
		parent.addComponent(this);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	protected final T parent;
	protected boolean active = false;
	protected int x, y, w, h;
	protected boolean hover = false;
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean checkHover(int mouse_x, int mouse_y) {
		boolean x = mouse_x >= this.x() && mouse_x <= this.x() + this.w;
		boolean y = mouse_y >= this.y() && mouse_y <= this.y() + this.h;
		return x && y;
	}
	
	public int x() {
		return this.x + this.parent.get_x();
	}
	
	public int y() {
		return this.y + this.parent.get_y();
	}
	
	public void draw(MatrixStack matrices, int mouse_x, int mouse_y, float delta) {}
	
	public boolean clicked(double mouse_x, double mouse_y) {
		return false;
	}
	
	public void onKey(int key) {}
	
	public void typed(char c) {}
}