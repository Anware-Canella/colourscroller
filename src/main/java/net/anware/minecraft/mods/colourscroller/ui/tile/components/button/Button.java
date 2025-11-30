package net.anware.minecraft.mods.colourscroller.ui.tile.components.button;

import net.anware.minecraft.mods.colourscroller.ui.tile.Tile;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class Button<T extends Tile> extends Clickable<T> {
    public Button(T parent, int x, int y, int w, int h, Text text) {
        super(parent, x, y, w, h);
        this.text = text;
        parent.addComponent(this);
    }

    protected Text text;

    public void setText(Text text) {
        this.text = text;
    }
    
    @Override
    public void draw(MatrixStack matrices, int mouse_x, int mouse_y, float delta) {
        this.hover = this.checkHover(mouse_x, mouse_y);
        
        int x = this.x();
        int y = this.y();
        
        int outlineColour = 0xFF202020;
        if (this.active)
            outlineColour = 0xFFFFFFFF;
        else if (this.hover)
            outlineColour = 0xFF606060;
        this.parent.drawBox(matrices, x, x + this.w, y, y + this.h, 0xDD000000, outlineColour);
        
        TextRenderer textRenderer = this.parent.getScreen().getTextRenderer();
        int textColour = this.enabled ? 0xFFFFFFFF : 0xFF303030;
        if (textRenderer != null && text != null) {
            this.parent.drawText(matrices, this.text, x + (float) this.w / 2, y + (float) this.h / 2, textColour, Tile.ALIGN_CENTER);
        }
    }
}