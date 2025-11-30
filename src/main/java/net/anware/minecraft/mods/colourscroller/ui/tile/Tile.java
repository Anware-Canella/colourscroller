package net.anware.minecraft.mods.colourscroller.ui.tile;

import com.mojang.blaze3d.systems.RenderSystem;
import net.anware.minecraft.mods.colourscroller.ui.screen.TileScreen;
import net.anware.minecraft.mods.colourscroller.ui.tile.components.Component;
import net.anware.minecraft.mods.colourscroller.util.GameUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public abstract class Tile implements Drawable, Element, Selectable {
    public Tile(TileScreen screen, int x, int paddingTop, int paddingBottom) {
        this.screen = screen;
        this.paddingTop = paddingTop;
        this.paddingBottom = paddingBottom;
        this.x = x;
    }

    protected boolean active = false;
    protected int x, y = 0;
    protected final TileScreen screen;
    protected final int paddingTop, paddingBottom;
    protected final List<Component<?>> components = new ArrayList<>();
    protected Component<?> activeComponent = null;
    
    protected void setActiveComponent(Component<?> component) {
        if (component == this.activeComponent) {
            return;
        }
        if (this.activeComponent != null) {
            this.activeComponent.setActive(false);
        }
        if (component != null) {
            component.setActive(true);
        }
        this.activeComponent = component;
    }
    
    public static void playButtonSound() {
        GameUtil.CLIENT.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.2f));
    }
    
    public void addComponent(Component<?> component) {
        this.components.add(component);
    }
    
    @Override
    public void render(MatrixStack matrices, int mouse_x, int mouse_y, float delta) {
        this.draw(matrices, mouse_x, mouse_y, delta);
        for (Component<?> c : this.components) c.draw(matrices, mouse_x, mouse_y, delta);
    }
    
    @Override
    public final boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.activeComponent != null) this.activeComponent.onKey(keyCode);
        return this.onKey(keyCode);
    }
    
    @Override
    public final boolean charTyped(char chr, int modifiers) {
        if (this.activeComponent != null) this.activeComponent.typed(chr);
        return this.typed(chr);
    }
    
    @Override
    public final boolean mouseClicked(double mouse_x, double mouse_y, int button) {
        boolean componentClick = false;
        for (Component<?> c : this.components) {
            boolean clicked = c.clicked(mouse_x, mouse_y);
            componentClick |= clicked;
            if (clicked) {
                this.setActiveComponent(c);
                this.getScreen().setActiveTile(this);
            }
        }
        if (!componentClick) {
            this.setActiveComponent(null);
            if (this.getScreen().getActiveTile() == this) {
                this.getScreen().setActiveTile(null);
            }
        }
        return this.clicked(mouse_x, mouse_y) || componentClick;
    }
    
    protected abstract void draw(MatrixStack matrices, int mouse_x, int mouse_y, float delta);
    
    protected boolean onKey(int key) {
        return false;
    }
    
    protected boolean typed(char c) {
        return false;
    }
    
    protected boolean clicked(double mouse_x, double mouse_y) {
        return false;
    }
    
    // -------------------- GETTER / SETTER -------------------------
    
    public TileScreen getScreen() {
        return this.screen;
    }
    
    public boolean isActive() {
        return this.active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
        if (!active && this.activeComponent != null) {
            this.activeComponent.setActive(false);
        }
    }
    
    public void set_y(int y) {
        this.y = y;
    }
    
    public int get_x() {
        return this.x;
    }
    
    public int get_y() {
        return this.y;
    }
    
    public int getPaddingTop() {
        return this.paddingTop;
    }
    
    public int getPaddingBottom() {
        return this.paddingBottom;
    }
    
    public void appendNarrations(NarrationMessageBuilder builder) {}
    
    public SelectionType getType() {
        return SelectionType.NONE;
    }
    
    public abstract int getContentHeight();
    
    public int getPageHeight() {
        return this.getPaddingTop() + this.getPaddingBottom() + this.getContentHeight();
    }
    
    // -------------------- HELPER -------------------------
    
    public static final int
        ALIGN_NONE      = 0,
        ALIGN_CENTER    = 1,
        ALIGN_LEFT      = 2;
    public static final int
        STATE_DISABLED  = 0,
        STATE_IDLE      = 1,
        STATE_HOVER     = 2,
        STATE_ACTIVE    = 3;
    public static final ItemRenderer ITEM_RENDERER = MinecraftClient.getInstance().getItemRenderer();
    public static final int ITEM_SIZE = 16;
    
    public final void drawText(MatrixStack mxs, Text text, float x, float y, int color) {
        this.drawText(mxs, text, x, y, color, ALIGN_LEFT);
    }
    
    public final void drawText(MatrixStack mxs, Text text, float x, float y, int colour, int alignment) {
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
    
    public final void drawBox(MatrixStack mxs, int x, int x1, int y, int y1, int fillColour) {
        DrawableHelper.fill(mxs, x, y, x1, y1, fillColour);
    }
    
    public final void drawBox(MatrixStack mxs, int x, int x1, int y, int y1, int fillColour, int outlineColour) {
        DrawableHelper.fill(mxs, x, y, x1, y1, fillColour);
        drawHoriLine(mxs, x, x1, y, outlineColour);
        drawHoriLine(mxs, x, x1, y1, outlineColour);
        drawVertLine(mxs, x, y, y1, outlineColour);
        drawVertLine(mxs, x1, y, y1, outlineColour);
    }
    
    public final void drawVertLine(MatrixStack mxs, int x, int y, int y1, int colour) {
        if (y1 < y) {
            int i = y;
            y = y1;
            y1 = i;
        }
        DrawableHelper.fill(mxs, x, y + 1, x + 1, y1, colour);
    }
    
    public final void drawCenteredLine(MatrixStack mxs, int x, int y, int len, int colour) {
        drawHoriLine(mxs, x - len / 2, x + len / 2, y, colour);
    }
    
    public final void drawHoriLine(MatrixStack mxs, int x, int x1, int y, int colour) {
        if (x1 < x) {
            int i = x;
            x = x1;
            x1 = i;
        }
        DrawableHelper.fill(mxs, x, y, x1 + 1, y + 1, colour);
    }
    
    public final void drawLine() {
    
    }
    
    public final void drawItem(ItemStack stack, int x, int y, float scale) {
        MatrixStack mxs = RenderSystem.getModelViewStack();
        mxs.push();
        mxs.translate(x, y, 0);
        mxs.scale(scale, scale, scale);
        ITEM_RENDERER.renderInGuiWithOverrides(stack, 0, 0);
        mxs.pop();
        RenderSystem.applyModelViewMatrix();
    }
    
    public final void drawItem(Item item, int x, int y, float scale) {
        this.drawItem(new ItemStack(item), x, y, scale);
    }
    
    public final void drawCenteredItem(Item item, int x, int y, float scale) {
        this.drawItem(item, Math.round(x - 8 * scale), Math.round(y - 8 * scale), scale);
    }
}
