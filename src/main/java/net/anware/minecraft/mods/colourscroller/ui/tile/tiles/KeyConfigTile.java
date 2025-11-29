package net.anware.minecraft.mods.colourscroller.ui.tile.tiles;

import net.anware.minecraft.mods.colourscroller.keybind.KeyBind;
import net.anware.minecraft.mods.colourscroller.keybind.KeySequence;
import net.anware.minecraft.mods.colourscroller.ui.screen.TileScreen;
import net.anware.minecraft.mods.colourscroller.ui.tile.button.Button;
import net.anware.minecraft.mods.colourscroller.ui.tile.button.TextButton;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

import java.util.ArrayList;

import static net.minecraft.client.gui.DrawableHelper.drawTextWithShadow;

public class KeyConfigTile extends ButtonTile {
    public KeyConfigTile(TileScreen screen, int x, int paddingTop, int paddingBottom, int height, KeyBind keyBind) {
        super(screen, x, paddingTop, paddingBottom);
        this.keyBind = keyBind;
        this.height = height;

        this.keyButton = new TextButton(this, 100, 0, 100, this.height, new LiteralText(this.keyBind.toString()));
        this.setButton = new TextButton(this, 205, 0, 40, this.height, new LiteralText("SET"));
        this.resetButton = new TextButton(this, 250, 0, 40, this.height, new LiteralText("RESET"));
        this.buttons.add(keyButton);
        this.buttons.add(setButton);
        this.buttons.add(resetButton);
    }

    protected final ArrayList<Integer> bufKey = new ArrayList<>(4);
    protected final KeyBind keyBind;
    protected final int height;
    protected final TextButton keyButton, setButton, resetButton;

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
    protected void clicked(int mouse_x, int mouse_y, Button bt) {
        if (bt == this.setButton) {
            if (!this.bufKey.isEmpty()) {
                this.keyBind.setKeySeq(new KeySequence(this.bufKey));
                this.screen.setActiveTile(null);
            }
        } else if (bt == this.resetButton) {
            this.keyBind.resetKeySeq();
            this.screen.setActiveTile(null);
        } else if (bt == this.keyButton) {
            this.bufKey.clear();
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
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!this.isActive()) {
            return false;
        }
        if (keyCode < 0) {
            return false;
        }
        if (this.bufKey.contains(keyCode)) {
            return false;
        }
        if (this.bufKey.size() >= 4) {
            return false;
        }
        this.bufKey.add(keyCode);
        return true;
    }
}