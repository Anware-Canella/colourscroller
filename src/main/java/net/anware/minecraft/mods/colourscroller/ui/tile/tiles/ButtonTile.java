package net.anware.minecraft.mods.colourscroller.ui.tile.tiles;

import net.anware.minecraft.mods.colourscroller.ui.screen.TileScreen;
import net.anware.minecraft.mods.colourscroller.ui.tile.ATile;
import net.anware.minecraft.mods.colourscroller.ui.tile.button.Button;
import net.anware.minecraft.mods.colourscroller.util.GameUtil;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvents;

import java.util.ArrayList;
import java.util.List;

public abstract class ButtonTile extends ATile {
    public ButtonTile(TileScreen screen, int x, int paddingTop, int paddingBottom) {
        super(screen, x, paddingTop, paddingBottom);
    }

    protected Button activeButton = null;
    protected final List<Button> buttons = new ArrayList<>();

    @Override
    public final void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        for (Button b : this.buttons) {
            b.setHovered(b.checkHover(mouseX, mouseY));
            b.draw(matrices, mouseX, mouseY, delta);
        }
        this.draw(matrices, mouseX, mouseY, delta);
    }

    @Override
    public final boolean mouseClicked(double mouseX, double mouseY, int button) {
        Button bt = null;
        for (Button b : this.buttons) {
            if (b.enabled() && b.checkHover((int) mouseX, (int) mouseY)) {
                bt = b;
                break;
            }
        }
        this.setActiveButton(bt);
        boolean res = bt != null;
        if (res) {
            this.screen.setActiveTile(this);
            GameUtil.CLIENT.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.2f));
        } else if (this.screen.getActiveTile() == this) {
            this.screen.setActiveTile(null);
        }
        this.clicked((int) mouseX, (int) mouseY, bt);
        return res;
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        if (!active) {
            if (this.activeButton != null) {
                this.activeButton.setActive(false);
            }
        }
    }

    protected void setActiveButton(Button bt) {
        if (bt == this.activeButton) {
            return;
        }
        if (this.activeButton != null) {
            this.activeButton.setActive(false);
        }
        if (bt != null) {
            bt.setActive(true);
        }
        this.activeButton = bt;
    }

    protected abstract void draw(MatrixStack mxs, int mouse_x, int mouse_y, float delta);

    protected abstract void clicked(int mouse_x, int mouse_y, Button bt);
}
