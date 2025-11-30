package net.anware.minecraft.mods.colourscroller.ui.tile.components;

import net.anware.minecraft.mods.colourscroller.ui.tile.Tile;
import net.minecraft.client.util.math.MatrixStack;

public abstract class Component<T extends Tile> {
	public Component(T parent) {
		this.parent = parent;
		parent.addComponent(this);
	}
	
	protected final T parent;
	protected boolean active = false;
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void draw(MatrixStack matrices, int mouse_x, int mouse_y, float delta) {}
	
	public boolean clicked(double mouse_x, double mouse_y) {
		return false;
	}
	
	public void onKey(int key) {}
	
	public void typed(char c) {}
}