package net.anware.minecraft.mods.colourscroller.ui.tile.tiles;

import net.anware.minecraft.mods.colourscroller.keybind.KeyBind;
import net.anware.minecraft.mods.colourscroller.keybind.KeyBindLookup;
import net.anware.minecraft.mods.colourscroller.keybind.KeySequence;
import net.anware.minecraft.mods.colourscroller.ui.screen.TileScreen;
import net.anware.minecraft.mods.colourscroller.ui.tile.Tile;
import net.anware.minecraft.mods.colourscroller.ui.tile.components.button.Button;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.util.ArrayList;

import static net.minecraft.client.gui.DrawableHelper.drawTextWithShadow;

public class KeyConfigTile extends Tile {
    public KeyConfigTile(TileScreen screen, int x, int paddingTop, int paddingBottom, int height, KeyBind keyBind) {
        super(screen, x, paddingTop, paddingBottom);
        this.keyBind = keyBind;
        this.height = height;
        
        this.keyButton = new Button<>(this, 100, 0, 100, this.height, new LiteralText(this.keyBind.toString())) {
	        @Override
	        protected boolean clicked() {
		        this.parent.bufKey.clear();
                return true;
	        }
        };
        this.setButton = new Button<>(this, 205, 0, 40, this.height, new LiteralText("SET")) {
            @Override
            protected boolean clicked() {
                if (!this.parent.bufKey.isEmpty()) {
                    this.parent.keyBind.setKeySeq(new KeySequence(this.parent.bufKey));
                    KeyBindLookup.save();
                    this.parent.getScreen().setActiveTile(null);
                }
                return false;
            }
        };
        this.resetButton = new Button<>(this, 250, 0, 40, this.height, new LiteralText("RESET")) {
            @Override
            protected boolean clicked() {
                this.parent.keyBind.resetKeySeq();
                this.parent.screen.setActiveTile(null);
                return false;
            }
        };
    }

    protected final ArrayList<Integer> bufKey = new ArrayList<>(4);
    protected final KeyBind keyBind;
    protected final int height;
    protected final Button<KeyConfigTile> keyButton, setButton, resetButton;

    @Override
    protected void draw(MatrixStack mxs, int mouse_x, int mouse_y, float delta) {
        drawTextWithShadow(mxs, this.screen.getTextRenderer(), new LiteralText(this.keyBind.id), this.x, this.y + this.getContentHeight() / 2 - 4, 0xFFFFFFFF);
        if (this.bufKey.isEmpty()) {
            this.setButton.setEnabled(false);
            this.keyButton.setText(new LiteralText(this.keyBind.toString()));
        } else {
            this.setButton.setEnabled(true);
            this.keyButton.setText(new LiteralText(KeySequence.formatKeySequence(this.bufKey)));
        }
    }
    
    @Override
    public int getContentHeight() {
        return this.height;
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        if (!active) {
            this.bufKey.clear();
        }
    }

    @Override
    public boolean onKey(int key) {
        if (!this.isActive()) {
            return false;
        }
        if (key < 0) {
            return false;
        }
        if (this.bufKey.contains(key)) {
            return false;
        }
        if (this.bufKey.size() >= 4) {
            return false;
        }
        this.bufKey.add(key);
        return true;
    }
}