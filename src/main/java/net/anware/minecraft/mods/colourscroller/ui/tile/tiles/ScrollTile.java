package net.anware.minecraft.mods.colourscroller.ui.tile.tiles;

import net.anware.minecraft.mods.colourscroller.scroll.Scroll;
import net.anware.minecraft.mods.colourscroller.scroll.ScrollLookup;
import net.anware.minecraft.mods.colourscroller.ui.screen.TileScreen;
import net.anware.minecraft.mods.colourscroller.ui.tile.Tile;
import net.anware.minecraft.mods.colourscroller.ui.tile.components.button.Clickable;
import net.anware.minecraft.mods.colourscroller.util.GameUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

public class ScrollTile extends Tile {
    public ScrollTile(TileScreen screen, int x, int paddingTop, int paddingBottom, Scroll scroll, int width, int lineHeight) {
        super(screen, x, paddingTop, paddingBottom);
        this.scroll = scroll;
	    this.width = width;
	    this.lineHeight = lineHeight;
        this.removeButton = new Clickable<>(this, this.width - TITLE_HEIGHT, 0, TITLE_HEIGHT, TITLE_HEIGHT) {
            @Override
            protected boolean clicked() {
                ScrollLookup.deleteScroll(this.parent.scroll);
                this.parent.getScreen().removeTiles(this.parent);
                this.parent.getScreen().reloadChildren();
                return true;
            }
            
            @Override
            public void draw(MatrixStack matrices, int mouse_x, int mouse_y, float delta) {
                this.hover = this.checkHover(mouse_x, mouse_y);
                int x = this.x(), y = this.y();
                if (hover) {
                    this.parent.drawBox(matrices, x, x + this.w, y, y + this.h, 0xEEFF2020);
                }
                this.parent.drawCenteredLine(matrices, x + this.w / 2, y + this.h / 2, 6, 0xDDFFFFFF);
            }
        };
    }

    public static final int
        ID_BUTTON       = 0xF000;
    public static final int
        TITLE_HEIGHT    = 20;
    protected final Scroll scroll;
    protected final int width, lineHeight;
    protected int activeButton = -1;
    protected final Clickable<ScrollTile> removeButton;
    
    @Override
    public int getContentHeight() {
        return this.scroll.size() * this.lineHeight + TITLE_HEIGHT;
    }

    @Override
    public void draw(MatrixStack matrices, int mouse_x, int mouse_y, float delta) {
        this.drawBox(matrices, this.x, this.x + this.width, this.y, this.y + this.getContentHeight(), 0xAA202020, 0xFF000000);
        this.drawBox(matrices, this.x, this.x + this.width, this.y, this.y + TITLE_HEIGHT, 0xEE202020);
        this.drawText(matrices, new LiteralText("Name:"), this.x + 4, this.y + TITLE_HEIGHT - 8, this.activeButton == ID_BUTTON ? 0xFF707070 : 0xFFFFFFFF);
        
        if (this.activeButton != -1) {
            if (this.activeButton == ID_BUTTON) {
                this.drawBox(matrices, this.x + 2, this.x + 100, this.y + 2, this.y + TITLE_HEIGHT - 2, 0xFFFFFFFF);
            } else {
                int base_y = this.y + TITLE_HEIGHT + this.activeButton * this.lineHeight;
                this.drawBox(matrices, this.x, this.x + this.width, base_y, base_y + this.lineHeight, 0xEEFFFFFF);
            }
        }
        
        int hoveredButton = this.getItemIndexAt(mouse_x, mouse_y);
        if (hoveredButton != -1) {
            if (this.x + this.width - mouse_x > this.lineHeight) {
                int base_y = this.y + TITLE_HEIGHT + hoveredButton * this.lineHeight;
                this.drawBox(matrices, this.x, this.x + this.width, base_y, base_y + this.lineHeight, 0xBBBBBBBB);
            } else {
                int base_y = this.y + TITLE_HEIGHT + hoveredButton * this.lineHeight;
                this.drawBox(matrices, this.x + this.width - this.lineHeight, this.x + this.width, base_y, base_y + this.lineHeight, 0xEEFF2020);
            }
        }
        
        for (int i = 0; i < this.scroll.size(); i++) {
            Item item = this.scroll.getItem(i);
            int base_y = this.y + TITLE_HEIGHT + i * this.lineHeight;
            this.drawCenteredItem(matrices, item, this.x + this.lineHeight / 2 + 3, base_y + this.lineHeight / 2, 1.0f);
            this.drawText(matrices, new LiteralText(GameUtil.getName(item).toString()), this.x + this.lineHeight + 6, base_y + 10, this.activeButton == i ? 0xFF707070 : 0xFFFFFFFF);
            this.drawCenteredLine(matrices, this.x + this.width - this.lineHeight / 2, this.y + TITLE_HEIGHT + i * this.lineHeight + this.lineHeight / 2, 6, 0xDDFFFFFF);
        }
    }
    
    @Override
    public void setActive(boolean active) {
        if (!active) {
            this.activeButton = -1;
        }
    }
    
    @Override
    protected boolean clicked(double mouse_x, double mouse_y) {
        // TODO : detect other stuff clicks
        
        
        int button = this.getItemIndexAt((int) mouse_x, (int) mouse_y);
	    if (button != -1) {
		    if (this.x + this.width - mouse_x > this.lineHeight) {
			    this.activeButton = button;
                playButtonSound();
                return true;
		    } else {
                this.activeButton = -1;
                playButtonSound();
                this.scroll.deleteItem(button);
                this.getScreen().arrangeChildren();
                return true;
            }
	    }
        return false;
    }
    
    @Override
    protected boolean onKey(int key) {
        if (this.activeButton != -1 && this.activeButton != ID_BUTTON) {
            if (key == GLFW.GLFW_KEY_UP && this.activeButton > 0) {
                this.scroll.shiftItem(this.activeButton, -1);
                this.activeButton -= 1;
            } else if (key == GLFW.GLFW_KEY_DOWN && this.activeButton < this.scroll.size() - 1) {
                this.scroll.shiftItem(this.activeButton, 1);
                this.activeButton += 1;
            } else {
                return false;
            }
            return true;
        }
        return false;
    }
    
    @Override
    protected boolean typed(char c) {
        return false;
    }
    
    protected int getItemIndexAt(int x, int y) {
        if (x < this.x || x > this.x + this.width) {
            return -1;
        }
        int i = Math.floorDiv(y - TITLE_HEIGHT - this.y, this.lineHeight);
        if (i < 0 || i >= this.scroll.size()) {
            return -1;
        }
        return i;
    }
}