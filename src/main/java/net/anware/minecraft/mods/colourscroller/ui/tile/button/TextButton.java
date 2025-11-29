package net.anware.minecraft.mods.colourscroller.ui.tile.button;

import net.anware.minecraft.mods.colourscroller.ui.tile.tiles.ButtonTile;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class TextButton extends Button {
    public TextButton(ButtonTile parent, int x, int y, int w, int h, Text text) {
        super(parent, x, y, w, h);
        this.text = text;
    }

    protected Text text;

    public void setText(Text text) {
        this.text = text;
    }

    @Override
    public void draw(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.parent.drawBox(matrices, this.text, this.getX(), this.getX() + this.w, this.getY(), this.getY() + this.h, this.getState());
    }
}