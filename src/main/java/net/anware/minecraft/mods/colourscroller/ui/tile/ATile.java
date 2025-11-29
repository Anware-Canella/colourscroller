package net.anware.minecraft.mods.colourscroller.ui.tile;

import net.anware.minecraft.mods.colourscroller.ui.screen.TileScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;

public abstract class ATile implements Tile {
    public ATile(TileScreen screen, int x, int paddingTop, int paddingBottom) {
        this.screen = screen;
        this.paddingTop = paddingTop;
        this.paddingBottom = paddingBottom;
        this.x = x;
    }

    protected boolean active = false;
    protected int x, y = 0;
    protected final TileScreen screen;
    protected final int paddingTop, paddingBottom;

    @Override
    public TileScreen getScreen() {
        return this.screen;
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void set_y(int y) {
        this.y = y;
    }

    @Override
    public int get_x() {
        return this.x;
    }

    @Override
    public int get_y() {
        return this.y;
    }

    @Override
    public int getPaddingTop() {
        return this.paddingTop;
    }

    @Override
    public int getPaddingBottom() {
        return this.paddingBottom;
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {}

    @Override
    public SelectionType getType() {
        return SelectionType.NONE;
    }
}
