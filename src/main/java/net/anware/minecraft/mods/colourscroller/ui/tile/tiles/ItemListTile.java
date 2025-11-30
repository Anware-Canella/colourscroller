package net.anware.minecraft.mods.colourscroller.ui.tile.tiles;

import net.anware.minecraft.mods.colourscroller.scroll.Scroll;
import net.anware.minecraft.mods.colourscroller.ui.screen.TileScreen;
import net.anware.minecraft.mods.colourscroller.ui.tile.Tile;
import net.anware.minecraft.mods.colourscroller.util.GameUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.text.LiteralText;

public class ItemListTile extends Tile {
    public ItemListTile(TileScreen screen, int x, int paddingTop, int paddingBottom, Scroll scroll, int width, int lineHeight) {
        super(screen, x, paddingTop, paddingBottom);
        this.scroll = scroll;
	    this.width = width;
	    this.lineHeight = lineHeight;
    }

    public static final int
        CROSS_BUTTON    = 1000,
        ID_BUTTON       = 1001,
        ITEMS_BUTTON    = 10;
    public static final int TITLE_HEIGHT = 20;
    protected final Scroll scroll;
    protected final int width, lineHeight;
    protected int activeButton = -1;
    
    @Override
    public int getContentHeight() {
        return this.scroll.size() * this.lineHeight + TITLE_HEIGHT;
    }

    @Override
    public void draw(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.drawBox(matrices, this.x, this.x + this.width, this.y, this.y + this.getContentHeight(), 0xAA202020, 0xFF000000);
        this.drawBox(matrices, this.x, this.x + this.width, this.y, this.y + TITLE_HEIGHT, 0xEE202020);
        
        if (this.activeButton != -1) {
            if (this.activeButton == ID_BUTTON) {
                this.drawBox(matrices, this.x + 2, this.x + 100, this.y + 2, this.y + TITLE_HEIGHT - 2, 0xFFFFFFFF);
            } else {
                int base_y = this.y + TITLE_HEIGHT + (this.activeButton - ITEMS_BUTTON) * this.lineHeight;
                this.drawBox(matrices, this.x, this.x + this.width, base_y, base_y + this.lineHeight, 0xEEFFFFFF);
            }
        }
        
        this.drawText(matrices, new LiteralText("Name:"), this.x + 4, this.y + TITLE_HEIGHT - 8, this.activeButton == ID_BUTTON ? 0xFF707070 : 0xFFFFFFFF);
        
        for (int i = 0; i < this.scroll.size(); i++) {
            Item item = this.scroll.getItem(i);
            int base_y = this.y + TITLE_HEIGHT + i * this.lineHeight;
            this.drawCenteredItem(item, this.x + 10, base_y + this.lineHeight / 2, 1.0f);
            this.drawText(matrices, new LiteralText(GameUtil.getName(item).toString()), this.x + 30, base_y + 10, this.activeButton == i + ITEMS_BUTTON ? 0xFF707070 : 0xFFFFFFFF);
        }
    }
    
    @Override
    protected boolean clicked(double mouse_x, double mouse_y) {
        int button = this.getPointedButtonIndex((int) mouse_x, (int) mouse_y);
        if (button == -1) {
            return false;
        }
        this.activeButton = button;
        playButtonSound();
        if (button == CROSS_BUTTON) {
            // TODO: write delete list logic
        }
        return true;
    }
    
    protected int getPointedButtonIndex(int x, int y) {
        if (x < this.x || x > this.x + this.width) {
            return -1;
        }
        int i = (y - TITLE_HEIGHT - this.y) / this.lineHeight;
        if (i < 0 || i >= this.scroll.size()) {
            return -1;
        }
        return i + 10;
    }
}