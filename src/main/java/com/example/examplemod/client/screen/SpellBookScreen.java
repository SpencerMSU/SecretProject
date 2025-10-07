package com.example.examplemod.client.screen;

import com.example.examplemod.client.spell.ClientSpellState;
import com.example.examplemod.spell.SpellClass;
import com.example.examplemod.spell.SpellEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(net.neoforged.api.distmarker.Dist.CLIENT)
public class SpellBookScreen extends Screen {
    private static final int PADDING = 8;
    private static final int SLOT_SIZE = 20;
    private static final int SLOT_GAP = 4;

    private SpellEntry dragging;

    private Button tabFire;
    private Button tabWater;

    public SpellBookScreen() {
        super(Component.literal("Spell Book"));
    }

    @Override
    protected void init() {
        ClientSpellState.ensureInitialized();

        int left = PADDING;
        int top = PADDING;
        tabFire = Button.builder(Component.literal("Fire"), b -> {
            ClientSpellState.setSelectedClass(SpellClass.FIRE);
        }).bounds(left, top, 60, 20).build();
        tabWater = Button.builder(Component.literal("Water"), b -> {
            ClientSpellState.setSelectedClass(SpellClass.WATER);
        }).bounds(left + 64, top, 60, 20).build();
        addRenderableWidget(tabFire);
        addRenderableWidget(tabWater);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        renderBackground(g);
        super.render(g, mouseX, mouseY, partialTick);

        int w = this.width;
        int h = this.height;
        int midX = w / 2;

        // Title
        g.drawString(this.font, this.title, PADDING, PADDING - 10, 0xFFFFFF);

        // Left panel: spells of selected class
        int panelPadding = 10;
        int leftPanelLeft = PADDING;
        int leftPanelTop = PADDING + 28;
        int leftPanelRight = midX - panelPadding;
        int leftPanelBottom = h - PADDING;

        fillPanel(g, leftPanelLeft, leftPanelTop, leftPanelRight, leftPanelBottom);
        drawClassSpells(g, leftPanelLeft + 8, leftPanelTop + 8, leftPanelRight - leftPanelLeft - 16, leftPanelBottom - leftPanelTop - 16);

        // Right panel: active hotbar slots vertically
        int rightPanelLeft = midX + panelPadding;
        int rightPanelTop = leftPanelTop;
        int rightPanelRight = w - PADDING;
        int rightPanelBottom = leftPanelBottom;
        fillPanel(g, rightPanelLeft, rightPanelTop, rightPanelRight, rightPanelBottom);
        drawHotbarSlots(g, rightPanelLeft + 8, rightPanelTop + 8, rightPanelRight - rightPanelLeft - 16, rightPanelBottom - rightPanelTop - 16);

        // Tooltip for dragging
        if (dragging != null) {
            g.renderItem(dragging.icon(), mouseX - 8, mouseY - 8);
        }
    }

    private void fillPanel(GuiGraphics g, int left, int top, int right, int bottom) {
        g.fill(left, top, right, bottom, 0xCC0B0F14);
        g.fill(left, top, right, top + 1, 0xFF374151);
        g.fill(left, bottom - 1, right, bottom, 0xFF374151);
        g.fill(left, top, left + 1, bottom, 0xFF374151);
        g.fill(right - 1, top, right, bottom, 0xFF374151);
    }

    private void drawClassSpells(GuiGraphics g, int left, int top, int width, int height) {
        List<SpellEntry> list = ClientSpellState.getSpellsForSelectedClass();

        int cols = Math.max(1, width / (SLOT_SIZE + SLOT_GAP));
        for (int i = 0; i < list.size(); i++) {
            int col = i % cols;
            int row = i / cols;
            int sx = left + col * (SLOT_SIZE + SLOT_GAP);
            int sy = top + row * (SLOT_SIZE + SLOT_GAP);
            drawSlot(g, sx, sy, list.get(i));
        }
    }

    private void drawHotbarSlots(GuiGraphics g, int left, int top, int width, int height) {
        int slots = ClientSpellState.getHotbarSize();
        int totalHeight = slots * SLOT_SIZE + (slots - 1) * SLOT_GAP;
        int sy = top + (height - totalHeight) / 2;
        for (int i = 0; i < slots; i++) {
            int sx = left + (width - SLOT_SIZE) / 2;
            drawSlot(g, sx, sy + i * (SLOT_SIZE + SLOT_GAP), ClientSpellState.getHotbarEntry(i));
        }
    }

    private void drawSlot(GuiGraphics g, int x, int y, SpellEntry entry) {
        g.fill(x - 2, y - 2, x + SLOT_SIZE + 2, y + SLOT_SIZE + 2, 0xAA000000);
        g.fill(x - 1, y - 1, x + SLOT_SIZE + 1, y + SLOT_SIZE + 1, 0xFF1F2937);
        if (entry != null) {
            g.renderItem(entry.icon(), x + 2, y + 2);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) return super.mouseClicked(mouseX, mouseY, button);
        SpellEntry over = getEntryUnderMouse((int) mouseX, (int) mouseY);
        if (over != null) {
            dragging = over;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0 && dragging != null) {
            int slot = getHotbarSlotUnderMouse((int) mouseX, (int) mouseY);
            if (slot >= 0) {
                ClientSpellState.setHotbarEntry(slot, dragging);
            }
            dragging = null;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        return dragging != null || super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    private SpellEntry getEntryUnderMouse(int mouseX, int mouseY) {
        int w = this.width;
        int h = this.height;
        int midX = w / 2;
        int leftPanelLeft = PADDING;
        int leftPanelTop = PADDING + 28;
        int leftPanelRight = midX - 10;
        int leftPanelBottom = h - PADDING;
        int left = leftPanelLeft + 8;
        int top = leftPanelTop + 8;
        int width = leftPanelRight - leftPanelLeft - 16;
        int cols = Math.max(1, width / (SLOT_SIZE + SLOT_GAP));
        List<SpellEntry> list = ClientSpellState.getSpellsForSelectedClass();
        for (int i = 0; i < list.size(); i++) {
            int col = i % cols;
            int row = i / cols;
            int sx = left + col * (SLOT_SIZE + SLOT_GAP);
            int sy = top + row * (SLOT_SIZE + SLOT_GAP);
            if (mouseX >= sx && mouseX <= sx + SLOT_SIZE && mouseY >= sy && mouseY <= sy + SLOT_SIZE) {
                return list.get(i);
            }
        }
        return null;
    }

    private int getHotbarSlotUnderMouse(int mouseX, int mouseY) {
        int w = this.width;
        int h = this.height;
        int midX = w / 2;
        int rightPanelLeft = midX + 10;
        int rightPanelTop = PADDING + 28;
        int rightPanelRight = w - PADDING;
        int rightPanelBottom = h - PADDING;
        int left = rightPanelLeft + 8;
        int top = rightPanelTop + 8;
        int width = rightPanelRight - rightPanelLeft - 16;
        int height = rightPanelBottom - rightPanelTop - 16;

        int slots = ClientSpellState.getHotbarSize();
        int totalHeight = slots * SLOT_SIZE + (slots - 1) * SLOT_GAP;
        int sy = top + (height - totalHeight) / 2;
        int sx = left + (width - SLOT_SIZE) / 2;
        for (int i = 0; i < slots; i++) {
            int cy = sy + i * (SLOT_SIZE + SLOT_GAP);
            if (mouseX >= sx && mouseX <= sx + SLOT_SIZE && mouseY >= cy && mouseY <= cy + SLOT_SIZE) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Close with inventory/Escape to match UX expectations
        if (this.minecraft != null && this.minecraft.options.keyInventory.matches(keyCode, scanCode)) {
            onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
