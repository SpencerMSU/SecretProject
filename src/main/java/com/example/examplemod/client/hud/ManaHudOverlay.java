package com.example.examplemod.client.hud;

import com.example.examplemod.client.ClientConfig;
import com.example.examplemod.client.spell.ClientSpellState;
import com.example.examplemod.spell.SpellEntry;
import com.example.examplemod.mana.ManaProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

import java.awt.*;

@EventBusSubscriber(value = net.neoforged.api.distmarker.Dist.CLIENT)
public class ManaHudOverlay {

    // Defaults are overridden by client config values
    private static final int DEFAULT_BAR_WIDTH = 8; // thin vertical bar
    private static final int DEFAULT_BAR_HEIGHT = 80;
    private static final int DEFAULT_PADDING_RIGHT = 6;

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) return;

        if (!ClientConfig.SHOW_MANA.get()) return;

        var mana = ManaProvider.get(mc.player);
        int current = mana.getCurrentMana();
        int max = Math.max(mana.getMaxMana(), 1);

        GuiGraphics g = event.getGuiGraphics();
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        final int barWidth = ClientConfig.BAR_WIDTH.get();
        final int barHeight = ClientConfig.BAR_HEIGHT.get();
        final int paddingRight = ClientConfig.RIGHT_PADDING.get();

        int x = screenWidth - paddingRight - barWidth;
        int y = screenHeight / 2 - barHeight / 2;

        // Background with border (rounded look via inner inset)
        fill(g, x - 2, y - 2, barWidth + 4, barHeight + 4, 0xAA000000);
        fill(g, x - 1, y - 1, barWidth + 2, barHeight + 2, 0xFF1F2937); // dark slate

        float ratio = current / (float) max;
        int filledHeight = (int) (barHeight * ratio);

        int fillX = x;
        int fillY = y + (barHeight - filledHeight);

        // Gradient mana fill (blue to cyan)
        verticalGradient(g, fillX, fillY, barWidth, filledHeight, 0xFF3B82F6, 0xFF06B6D4);

        // Gloss highlight
        int glossHeight = Math.max(4, (int) (filledHeight * 0.2f));
        verticalGradient(g, fillX + 1, fillY, barWidth - 2, glossHeight, 0x40FFFFFF, 0x00000000);

        // Tick marks every 20%
        for (int i = 1; i < 5; i++) {
            int ty = y + (barHeight * i / 5);
            fill(g, x - 2, ty, barWidth + 4, 1, 0xFF111827);
        }

        // Text value
        String text = current + "/" + max;
        int tw = mc.font.width(text);
        g.drawString(mc.font, text, x - 6 - tw, y + barHeight - 8, 0xB3FFFFFF, true);

        // Render spell hotbar below the mana bar (horizontal 5 slots)
        renderSpellHotbar(g, x, y + barHeight + 10, barWidth);
    }

    private static void fill(GuiGraphics g, int x, int y, int w, int h, int color) {
        g.fill(x, y, x + w, y + h, color);
    }

    private static void verticalGradient(GuiGraphics g, int x, int y, int w, int h, int topColor, int bottomColor) {
        if (h <= 0) return;
        g.fillGradient(x, y, x + w, y + h, topColor, bottomColor);
    }

    private static void renderSpellHotbar(GuiGraphics g, int manaBarX, int top, int manaBarWidth) {
        Minecraft mc = Minecraft.getInstance();
        ClientSpellState.ensureInitialized();

        final int slots = ClientSpellState.getHotbarSize();
        final int slotSize = 20;
        final int gap = 4;
        // Center horizontally relative to mana bar
        final int left = manaBarX + (manaBarWidth - slotSize) / 2;

        int active = ClientSpellState.getActiveIndex();

        for (int i = 0; i < slots; i++) {
            int sx = left;
            int sy = top + i * (slotSize + gap);

            var entry = ClientSpellState.getHotbarEntry(i);
            
            // Slot background with rarity-colored border
            int rarityColor = entry != null ? entry.rarity().getColor() : 0xAA000000;
            g.fill(sx - 2, sy - 2, sx + slotSize + 2, sy + slotSize + 2, rarityColor);
            g.fill(sx - 1, sy - 1, sx + slotSize + 1, sy + slotSize + 1, 0xFF111827);

            // Active outline (brighter glow)
            if (i == active) {
                int glowColor = entry != null ? entry.rarity().getColor() | 0xFF000000 : 0xFF60A5FA;
                // Draw glowing border
                g.fill(sx - 3, sy - 3, sx + slotSize + 3, sy - 2, glowColor);
                g.fill(sx - 3, sy + slotSize + 2, sx + slotSize + 3, sy + slotSize + 3, glowColor);
                g.fill(sx - 3, sy - 3, sx - 2, sy + slotSize + 3, glowColor);
                g.fill(sx + slotSize + 2, sy - 3, sx + slotSize + 3, sy + slotSize + 3, glowColor);
            }

            if (entry != null) {
                var itemRenderer = mc.getItemRenderer();
                var pose = g.pose();
                pose.pushPose();
                g.renderItem(entry.icon(), sx + 2, sy + 2);
                pose.popPose();
            }
        }
        
        // Show active spell info to the right of the active slot
        var activeEntry = ClientSpellState.getHotbarEntry(active);
        if (activeEntry != null) {
            int infoX = left + slotSize + 8;
            int infoY = top + active * (slotSize + gap);
            
            // Spell name
            String name = activeEntry.displayName().getString();
            g.drawString(mc.font, name, infoX, infoY, activeEntry.rarity().getColor(), true);
            
            // Damage and mana stacked vertically
            String damageStr = "⚔" + activeEntry.damage();
            String manaStr = "✦" + activeEntry.manaCost();
            
            g.drawString(mc.font, damageStr, infoX, infoY + 10, 0xFFFF5555, true);
            g.drawString(mc.font, manaStr, infoX, infoY + 20, 0xFF5555FF, true);
        }
    }
}
