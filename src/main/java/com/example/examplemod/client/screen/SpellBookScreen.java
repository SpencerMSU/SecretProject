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

import java.util.ArrayList;
import java.util.List;

@OnlyIn(net.neoforged.api.distmarker.Dist.CLIENT)
public class SpellBookScreen extends Screen {
    private static final int PADDING = 8;
    private static final int SLOT_SIZE = 20; // Уменьшил размер слотов для оконного режима
    private static final int SLOT_GAP = 2;   // Уменьшил промежутки для компактности

    private SpellEntry dragging;
    private int scrollOffset = 0;

    private Button tabFire;
    private Button tabWater;

    public SpellBookScreen() {
        super(Component.translatable("screen.examplemod.spell_book"));
    }

    @Override
    protected void init() {
        ClientSpellState.ensureInitialized();

        int btnWidth = Math.min(80, (width - PADDING * 3) / 2);
        int left = PADDING;
        int top = PADDING;
        tabFire = Button.builder(Component.translatable("screen.examplemod.spell_book.tab.fire"), b -> {
            ClientSpellState.setSelectedClass(SpellClass.FIRE);
        }).bounds(left, top, btnWidth, 20).build();
        tabWater = Button.builder(Component.translatable("screen.examplemod.spell_book.tab.water"), b -> {
            ClientSpellState.setSelectedClass(SpellClass.WATER);
        }).bounds(left + btnWidth + 4, top, btnWidth, 20).build();
        addRenderableWidget(tabFire);
        addRenderableWidget(tabWater);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        renderBackground(g, mouseX, mouseY, partialTick);
        super.render(g, mouseX, mouseY, partialTick);

        int w = this.width;
        int h = this.height;
        
        // Responsive panel sizing based on window size
        int panelPadding = Math.max(5, Math.min(10, w / 80));
        int midX = w / 2;

        // Title
        g.drawString(this.font, this.title, PADDING, PADDING - 10, 0xFFFFFF);
        
        // Instructions
        String instructions = "Держите палочку и нажмите ПКМ для кастования";
        g.drawString(this.font, instructions, PADDING, PADDING + 10, 0xAAAAAA);

        // Left panel: spells of selected class (wider on larger screens)
        int leftPanelLeft = PADDING;
        int leftPanelTop = PADDING + 28;
        int leftPanelRight = midX - panelPadding;
        int leftPanelBottom = h - PADDING;

        fillPanel(g, leftPanelLeft, leftPanelTop, leftPanelRight, leftPanelBottom);
        int innerPadding = Math.max(4, Math.min(8, w / 100));
        drawClassSpells(g, leftPanelLeft + innerPadding, leftPanelTop + innerPadding, 
                       leftPanelRight - leftPanelLeft - innerPadding * 2, 
                       leftPanelBottom - leftPanelTop - innerPadding * 2);

        // Right panel: active hotbar slots vertically (smaller slots)
        int rightPanelLeft = midX + panelPadding;
        int rightPanelTop = leftPanelTop;
        int rightPanelRight = w - PADDING;
        int rightPanelBottom = leftPanelBottom;
        fillPanel(g, rightPanelLeft, rightPanelTop, rightPanelRight, rightPanelBottom);
        drawHotbarSlots(g, rightPanelLeft + innerPadding, rightPanelTop + innerPadding, 
                       rightPanelRight - rightPanelLeft - innerPadding * 2, 
                       rightPanelBottom - rightPanelTop - innerPadding * 2);

        // Tooltip for dragging
        if (dragging != null) {
            g.renderItem(dragging.icon(), mouseX - 8, mouseY - 8);
        }
        
        // Render spell tooltip on hover
        SpellEntry hovered = getEntryUnderMouse(mouseX, mouseY);
        if (hovered != null && dragging == null) {
            renderSpellTooltip(g, hovered, mouseX, mouseY);
        }
    }
    
    private void renderSpellTooltip(GuiGraphics g, SpellEntry spell, int mouseX, int mouseY) {
        List<Component> lines = new ArrayList<>();
        
        // Spell name with rarity color
        Component nameWithRarity = Component.literal("")
            .append(Component.literal(spell.displayName().getString()).withStyle(style -> 
                style.withColor(spell.rarity().getColor())));
        lines.add(nameWithRarity);
        
        // Rarity
        lines.add(Component.translatable("screen.examplemod.spell_book.rarity")
            .append(": " + spell.rarity().getDisplayName())
            .withStyle(style -> style.withColor(0xFFAAAAAA)));
        
        // Damage and Mana
        lines.add(Component.literal("⚔ ")
            .append(Component.translatable("screen.examplemod.spell_book.damage"))
            .append(": " + spell.damage())
            .withStyle(style -> style.withColor(0xFFFF5555)));
        lines.add(Component.literal("✦ ")
            .append(Component.translatable("screen.examplemod.spell_book.mana_cost"))
            .append(": " + spell.manaCost())
            .withStyle(style -> style.withColor(0xFF5555FF)));
        
        // Description
        lines.add(Component.literal(""));
        lines.add(spell.description().copy().withStyle(style -> style.withColor(0xFFDDDDDD)));
        
        // Visual effect info
        lines.add(Component.literal(""));
        lines.add(Component.translatable("screen.examplemod.spell_book.visual")
            .append(": " + spell.visualEffect().getAnimation())
            .withStyle(style -> style.withColor(0xFF55FFFF)));
        
        // Convert to FormattedCharSequence
        g.renderTooltip(this.font, 
            lines.stream()
                .map(c -> c.getVisualOrderText())
                .toList(), 
            mouseX, mouseY);
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

        // Fixed grid layout: 3 columns, 4 rows (12 slots total)
        int cols = 3;
        int maxRows = 4;
        
        // Calculate slot size to fill the entire area
        int availableWidth = width - SLOT_GAP * 2; // Leave some padding
        int availableHeight = height - SLOT_GAP * 2;
        
        int slotWidth = (availableWidth - (cols - 1) * SLOT_GAP) / cols;
        int slotHeight = (availableHeight - (maxRows - 1) * SLOT_GAP) / maxRows;
        
        // Use the smaller dimension to keep slots square
        int slotSize = Math.min(slotWidth, slotHeight);
        
        // Center the grid
        int gridWidth = cols * slotSize + (cols - 1) * SLOT_GAP;
        int gridHeight = maxRows * slotSize + (maxRows - 1) * SLOT_GAP;
        int startX = left + (width - gridWidth) / 2;
        int startY = top + (height - gridHeight) / 2;
        
        for (int i = 0; i < list.size() && i < cols * maxRows; i++) {
            int col = i % cols;
            int row = i / cols;
            
            int sx = startX + col * (slotSize + SLOT_GAP);
            int sy = startY + row * (slotSize + SLOT_GAP);
            drawSlot(g, sx, sy, slotSize, list.get(i));
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
    

    private void drawSlot(GuiGraphics g, int x, int y, int slotSize, SpellEntry entry) {
        // Draw slot with rarity-colored border
        int borderColor = entry != null ? entry.rarity().getColor() : 0xAA000000;
        g.fill(x - 2, y - 2, x + slotSize + 2, y + slotSize + 2, borderColor);
        g.fill(x - 1, y - 1, x + slotSize + 1, y + slotSize + 1, 0xFF1F2937);
        
        if (entry != null) {
            // Center the icon within the slot
            int iconOffset = (slotSize - 16) / 2; // Center 16x16 icon in slot
            g.renderItem(entry.icon(), x + iconOffset, y + iconOffset);
            
            // Draw damage and mana cost as text
            String damageText = String.valueOf(entry.damage());
            String manaText = String.valueOf(entry.manaCost());
            
            // Damage (red) in bottom-left
            g.drawString(this.font, damageText, x + 2, y + slotSize - 10, 0xFFFF5555);
            
            // Mana cost (blue) in bottom-right
            int manaWidth = this.font.width(manaText);
            g.drawString(this.font, manaText, x + slotSize - manaWidth - 2, y + slotSize - 10, 0xFF5555FF);
            
            // Draw spell name at the top (if there's space)
            if (slotSize >= 24) {
                String name = entry.displayName().getString();
                if (name.length() > 8) {
                    name = name.substring(0, 8) + "...";
                }
                int nameWidth = this.font.width(name);
                int nameX = x + (slotSize - nameWidth) / 2;
                g.drawString(this.font, name, nameX, y + 1, 0xFFFFFF);
            }
        }
    }
    
    // Overload for backward compatibility with hotbar slots
    private void drawSlot(GuiGraphics g, int x, int y, SpellEntry entry) {
        drawSlot(g, x, y, SLOT_SIZE, entry);
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
                // Сохраняем изменения в хотбаре
                savePlayerData();
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
        int panelPadding = Math.max(5, Math.min(10, w / 80));
        int midX = w / 2;
        int leftPanelLeft = PADDING;
        int leftPanelTop = PADDING + 28;
        int leftPanelRight = midX - panelPadding;
        int leftPanelBottom = h - PADDING;
        int innerPadding = Math.max(4, Math.min(8, w / 100));
        int left = leftPanelLeft + innerPadding;
        int top = leftPanelTop + innerPadding;
        int width = leftPanelRight - leftPanelLeft - innerPadding * 2;
        int height = leftPanelBottom - leftPanelTop - innerPadding * 2;
        
        // Use same logic as drawClassSpells
        int cols = 3;
        int maxRows = 4;
        
        // Calculate slot size to fill the entire area
        int availableWidth = width - SLOT_GAP * 2;
        int availableHeight = height - SLOT_GAP * 2;
        
        int slotWidth = (availableWidth - (cols - 1) * SLOT_GAP) / cols;
        int slotHeight = (availableHeight - (maxRows - 1) * SLOT_GAP) / maxRows;
        
        // Use the smaller dimension to keep slots square
        int slotSize = Math.min(slotWidth, slotHeight);
        
        // Center the grid
        int gridWidth = cols * slotSize + (cols - 1) * SLOT_GAP;
        int gridHeight = maxRows * slotSize + (maxRows - 1) * SLOT_GAP;
        int startX = left + (width - gridWidth) / 2;
        int startY = top + (height - gridHeight) / 2;
        
        List<SpellEntry> list = ClientSpellState.getSpellsForSelectedClass();
        for (int i = 0; i < list.size() && i < cols * maxRows; i++) {
            int col = i % cols;
            int row = i / cols;
            
            int sx = startX + col * (slotSize + SLOT_GAP);
            int sy = startY + row * (slotSize + SLOT_GAP);
            
            if (mouseX >= sx && mouseX <= sx + slotSize && mouseY >= sy && mouseY <= sy + slotSize) {
                return list.get(i);
            }
        }
        return null;
    }

    private int getHotbarSlotUnderMouse(int mouseX, int mouseY) {
        int w = this.width;
        int h = this.height;
        int panelPadding = Math.max(5, Math.min(10, w / 80));
        int midX = w / 2;
        int rightPanelLeft = midX + panelPadding;
        int rightPanelTop = PADDING + 28;
        int rightPanelRight = w - PADDING;
        int rightPanelBottom = h - PADDING;
        int innerPadding = Math.max(4, Math.min(8, w / 100));
        int left = rightPanelLeft + innerPadding;
        int top = rightPanelTop + innerPadding;
        int width = rightPanelRight - rightPanelLeft - innerPadding * 2;
        int height = rightPanelBottom - rightPanelTop - innerPadding * 2;

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
    public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
        // Handle scrolling in the left panel (spells area)
        int w = this.width;
        int h = this.height;
        int panelPadding = Math.max(5, Math.min(10, w / 80));
        int midX = w / 2;
        int leftPanelLeft = PADDING;
        int leftPanelTop = PADDING + 28;
        int leftPanelRight = midX - panelPadding;
        int leftPanelBottom = h - PADDING;
        
        if (mouseX >= leftPanelLeft && mouseX <= leftPanelRight && 
            mouseY >= leftPanelTop && mouseY <= leftPanelBottom) {
            scrollOffset -= (int) deltaY;
            // Сохраняем изменения позиции прокрутки
            savePlayerData();
            return true;
        }
        
        return super.mouseScrolled(mouseX, mouseY, deltaX, deltaY);
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
    
    /**
     * Сохранить данные игрока (позиция прокрутки, хотбар, выбранный класс)
     */
    private void savePlayerData() {
        // Обновляем scrollOffset в ClientSpellState
        ClientSpellState.setScrollOffset(scrollOffset);
        
        // Здесь можно добавить отправку данных на сервер для сохранения
        // Пока что данные сохраняются только локально
    }
}
