package net.anware.minecraft.mods.colourscroller.ui.tile.button;

import net.anware.minecraft.mods.colourscroller.ui.tile.tiles.ButtonTile;
import net.minecraft.client.util.math.MatrixStack;

import static net.anware.minecraft.mods.colourscroller.ui.tile.Tile.STATE_ACTIVE;
import static net.anware.minecraft.mods.colourscroller.ui.tile.Tile.STATE_DISABLED;
import static net.anware.minecraft.mods.colourscroller.ui.tile.Tile.STATE_HOVER;
import static net.anware.minecraft.mods.colourscroller.ui.tile.Tile.STATE_IDLE;

public abstract class Button {
    public Button(ButtonTile parent, int x, int y, int w, int h) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    protected final ButtonTile parent;
    protected int x, y;
    protected boolean enabled = true;
    protected boolean active = false;
    protected boolean hover = false;
    protected final int w, h;

    public int getX() {
        return this.x + this.parent.get_x();
    }

    public int getY() {
        return this.y + this.parent.get_y();
    }
    
    public boolean enabled() {
        return this.enabled;
    }
    
    public boolean hovered() {
        return this.hover;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public void setHovered(boolean hover) {
        this.hover = hover;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean checkHover(int mouse_x, int mouse_y) {
        boolean x = mouse_x >= this.getX() && mouse_x <= this.getX() + this.w;
        boolean y = mouse_y >= this.getY() && mouse_y <= this.getY() + this.h;
        return x && y;
    }

    protected int getState() {
        if (!this.enabled) {
            return STATE_DISABLED;
        }
        if (this.active) {
            return STATE_ACTIVE;
        }
        if (this.hover) {
            return STATE_HOVER;
        }
        return STATE_IDLE;
    }

    public abstract void draw(MatrixStack matrices, int mouseX, int mouseY, float delta);
}
