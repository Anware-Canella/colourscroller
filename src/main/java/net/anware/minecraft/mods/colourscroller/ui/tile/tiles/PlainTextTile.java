package net.anware.minecraft.mods.colourscroller.ui.tile.tiles;

import net.anware.minecraft.mods.colourscroller.ui.screen.TileScreen;
import net.anware.minecraft.mods.colourscroller.ui.tile.Tile;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class PlainTextTile extends Tile {
    public PlainTextTile(TileScreen screen, int x, int paddingTop, int paddingBottom, Text text, int colour) {
        super(screen, x, paddingTop, paddingBottom);
        this.text = text;
        this.colour = colour;
    }

    protected final Text text;
    protected final int colour;

    @Override
    public int getContentHeight() {
        return 8;
    }

    @Override
    public void draw(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.drawText(matrices, this.text, this.x, this.y, this.colour);
    }
}
