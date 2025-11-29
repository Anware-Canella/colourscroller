package net.anware.minecraft.mods.colourscroller.ui.screen;

import net.anware.minecraft.mods.colourscroller.ui.tile.Tile;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class TileScreen extends Screen {
    public TileScreen(Text title) {
        super(title);
    }

    protected final List<Tile> tiles = new ArrayList<>();
    protected Tile activeTile = null;

    protected void addTiles(Tile... tiles) {
        for (Tile t : tiles) {
            if (this.tiles.contains(t)) {
                continue;
            }
            this.tiles.add(t);
        }
        this.reloadChildren();
    }

    protected void removeTiles(Tile... tiles) {
        for (Tile t : tiles) {
            this.tiles.remove(t);
        }
        this.reloadChildren();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
    }

    public Tile getActiveTile() {
        return this.activeTile;
    }

    public void setActiveTile(Tile activeTile) {
        if (activeTile == this.activeTile) {
            return;
        }
        if (this.activeTile != null) {
            this.activeTile.setActive(false);
        }
        if (activeTile != null) {
            activeTile.setActive(true);
        }
        this.setFocused(activeTile);
        this.activeTile = activeTile;
    }
    
    public void reloadChildren() {
        this.clearChildren();
        this.loadChildren();
    }

    public void loadChildren() {
        int y = 0;
        for (Tile tile : this.tiles) {
            tile.set_y(y + tile.getPaddingTop());
            this.addDrawableChild(tile);
            y += tile.getPageHeight();
        }
    }

    @Override
    protected void init() {
        this.reloadChildren();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean res = super.keyPressed(keyCode, scanCode, modifiers);
        if (keyCode == GLFW.GLFW_KEY_ESCAPE && this.getActiveTile() != null) {
            this.setActiveTile(null);
        }
        return res;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return this.getActiveTile() == null;
    }

    public TextRenderer getTextRenderer() {
        return this.textRenderer;
    }
}
