package net.anware.minecraft.mods.colourscroller.ui.tile.components.button;

import net.anware.minecraft.mods.colourscroller.ui.tile.Tile;
import net.anware.minecraft.mods.colourscroller.ui.tile.components.Component;
import net.minecraft.client.util.math.MatrixStack;

public abstract class Clickable<T extends Tile> extends Component<T> {
    public Clickable(T parent, int x, int y, int w, int h) {
	    super(parent);
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    protected int x, y, w, h;
    protected boolean enabled = true;
    protected boolean active = false;
    protected boolean hover = false;
    
    public abstract void draw(MatrixStack matrices, int mouse_x, int mouse_y, float delta);
    
    public boolean checkHover(int mouse_x, int mouse_y) {
        boolean x = mouse_x >= this.x() && mouse_x <= this.x() + this.w;
        boolean y = mouse_y >= this.y() && mouse_y <= this.y() + this.h;
        return x && y;
    }
    
    @Override
    public boolean clicked(double mouse_x, double mouse_y) {
        if (!this.enabled()) {
            return false;
        }
        if (!this.checkHover((int) mouse_x, (int) mouse_y)) {
            return false;
        }
        Tile.playButtonSound();
        return this.clicked();
    }
    
    protected boolean clicked() {
        return false;
    }
    
    // -------------------- GETTER / SETTER -------------------------

    public int x() {
        return this.x + this.parent.get_x();
    }

    public int y() {
        return this.y + this.parent.get_y();
    }
    
    public boolean enabled() {
        return this.enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
}
