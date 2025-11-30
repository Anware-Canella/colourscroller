package net.anware.minecraft.mods.colourscroller.ui.tile.components.button;

import net.anware.minecraft.mods.colourscroller.ui.tile.Tile;
import net.anware.minecraft.mods.colourscroller.ui.tile.components.Component;

public class Clickable<T extends Tile> extends Component<T> {
    public Clickable(T parent, int x, int y, int w, int h) {
        super(parent, x, y, w, h);
    }
    
    protected boolean enabled = true;
    
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
    
    public boolean enabled() {
        return this.enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
