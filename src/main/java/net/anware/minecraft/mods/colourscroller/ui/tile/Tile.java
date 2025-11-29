package net.anware.minecraft.mods.colourscroller.ui.tile;

import com.mojang.blaze3d.systems.RenderSystem;
import net.anware.minecraft.mods.colourscroller.ui.screen.TileScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;

public interface Tile extends Drawable, Element, Selectable {

    TileScreen getScreen();

    boolean isActive();

    void setActive(boolean active);

    void set_y(int y);

    int get_x();

    int get_y();

    int getPaddingTop();

    int getPaddingBottom();

    int getContentHeight();

    default int getPageHeight() {
        return this.getPaddingTop() + this.getPaddingBottom() + this.getContentHeight();
    }

    int ALIGN_NONE = 0, ALIGN_CENTER = 1, ALIGN_LEFT = 2;

    default void drawText(MatrixStack mxs, Text text, float x, float y, int color) {
        this.getScreen().getTextRenderer().draw(mxs, text, x, y, color);
    }

    default void drawText(MatrixStack mxs, Text text, float x, float y, int colour, int alignment) {
        TextRenderer textRenderer = this.getScreen().getTextRenderer();
        if (textRenderer == null || text == null) return;
        OrderedText orderedText = text.asOrderedText();
        if (alignment == ALIGN_CENTER) {
            x = x - (float) textRenderer.getWidth(orderedText) / 2;
        }
        if (alignment == ALIGN_LEFT || alignment == ALIGN_CENTER) {
            y = y - 4;
        }
        textRenderer.drawWithShadow(mxs, orderedText, x, y, colour);
    }

    default void drawBox(MatrixStack mxs, int x, int x1, int y, int y1, int fillColour) {
        DrawableHelper.fill(mxs, x, y, x1, y1, fillColour);
    }

    default void drawBox(MatrixStack mxs, int x, int x1, int y, int y1, int fillColour, int outlineColour) {
        DrawableHelper.fill(mxs, x, y, x1, y1, fillColour);
        drawHoriLine(mxs, x, x1, y, outlineColour);
        drawHoriLine(mxs, x, x1, y1, outlineColour);
        drawVertLine(mxs, x, y, y1, outlineColour);
        drawVertLine(mxs, x1, y, y1, outlineColour);
    }

    int STATE_DISABLED = 0, STATE_IDLE = 1, STATE_HOVER = 2, STATE_ACTIVE = 3;

    default void drawBox(MatrixStack mxs, Text text, int x, int x1, int y, int y1, int state) {
        TextRenderer textRenderer = this.getScreen().getTextRenderer();
        int outlineColour = switch (state) {
            case STATE_HOVER -> 0xFF606060;
            case STATE_ACTIVE -> 0xFFFFFFFF;
            default -> 0xFF202020;
        };
        int textColour = switch (state) {
            case STATE_DISABLED -> 0xFF303030;
            default -> 0xFFFFFFFF;
        };
        drawBox(mxs, x, x1, y, y1, 0xFF000000, outlineColour);
        if (textRenderer != null && text != null) {
            this.drawText(mxs, text, (float) (x + x1) / 2, (float) (y + y1) / 2, textColour, ALIGN_CENTER);
        }
    }

    default void drawVertLine(MatrixStack mxs, int x, int y, int y1, int colour) {
        if (y1 < y) {
            int i = y;
            y = y1;
            y1 = i;
        }
        DrawableHelper.fill(mxs, x, y + 1, x + 1, y1, colour);
    }

    default void drawHoriLine(MatrixStack mxs, int x, int x1, int y, int colour) {
        if (x1 < x) {
            int i = x;
            x = x1;
            x1 = i;
        }
        DrawableHelper.fill(mxs, x, y, x1 + 1, y + 1, colour);
    }

    ItemRenderer ITEM_RENDERER = MinecraftClient.getInstance().getItemRenderer();
    int ITEM_SIZE = 16;

    default void drawItem(ItemStack stack, int x, int y, float scale) {
        MatrixStack mxs = RenderSystem.getModelViewStack();
        mxs.push();
        mxs.translate(x, y, 0);
        mxs.scale(scale, scale, scale);
        ITEM_RENDERER.renderInGuiWithOverrides(stack, 0, 0);
        mxs.pop();
        RenderSystem.applyModelViewMatrix();
    }
}
