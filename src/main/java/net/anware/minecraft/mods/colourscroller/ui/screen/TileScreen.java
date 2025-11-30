package net.anware.minecraft.mods.colourscroller.ui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.anware.minecraft.mods.colourscroller.ui.tile.Tile;
import net.anware.minecraft.mods.colourscroller.util.Numpy;
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
    protected int pageHeight = 0;
    protected int scroll = 0;
    protected double animScroll = 0;
    public static final int PADDING = 20;
    
    public void addTiles(List<Tile> tiles) {
        addTiles(tiles.toArray(new Tile[0]));
    }

    public void addTiles(Tile... tiles) {
        for (Tile t : tiles) {
            if (this.tiles.contains(t)) continue;
            this.tiles.add(t);
        }
        this.reloadChildren();
    }

    public void removeTiles(Tile... tiles) {
        for (Tile t : tiles) {
            this.tiles.remove(t);
        }
        this.reloadChildren();
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
    
    public void arrangeChildren() {
        int y = PADDING;
        for (Tile tile : this.tiles) {
            tile.set_y(y + tile.getPaddingTop());
            y += tile.getPageHeight();
        }
        this.pageHeight = y + PADDING;
    }
    
    public void reloadChildren() {
        this.clearChildren();
        this.loadChildren();
    }

    public void loadChildren() {
        for (Tile tile : this.tiles) {
            this.addDrawableChild(tile);
        }
        this.arrangeChildren();
    }
    
    public double getScroll() {
        return this.animScroll;
    }

    @Override
    protected void init() {
        this.reloadChildren();
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        
        matrices.push();
        matrices.translate(0, -this.getScroll(), 0);
        this.animScroll = this.scroll * 0.1 + this.animScroll * 0.9;
        if (Math.abs(this.animScroll) < 0.001) this.animScroll = 0.0;
        
        super.render(matrices, mouseX, mouseY, delta);
    }
    
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        boolean res = super.mouseScrolled(mouseX, mouseY, amount);
        if (this.height < this.pageHeight) {
            this.scroll = Numpy.clamp((int) Math.round(this.scroll - amount * 50), 0, this.pageHeight - this.height);
            res = true;
        }
        return res;
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
