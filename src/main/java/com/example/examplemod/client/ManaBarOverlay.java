package com.example.examplemod.client;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.mana.IManaData;
import com.example.examplemod.mana.ModAttachments;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

/**
 * Client-side overlay for rendering the mana bar on the right side of the screen.
 * The bar adapts to screen resolution and displays current/max mana with a smooth visual effect.
 */
public class ManaBarOverlay implements LayeredDraw.Layer {
    
    private static final int BAR_WIDTH = 80;
    private static final int BAR_HEIGHT = 8;
    private static final int MARGIN_RIGHT = 10;
    private static final int MARGIN_TOP = 50;
    private static final int BORDER_SIZE = 1;
    
    @Override
    public void render(GuiGraphics graphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.options.hideGui) {
            return;
        }
        
        Player player = mc.player;
        IManaData manaData = player.getData(ModAttachments.PLAYER_MANA);
        
        int screenWidth = graphics.guiWidth();
        int screenHeight = graphics.guiHeight();
        
        // Position on the right side of the screen
        int x = screenWidth - BAR_WIDTH - MARGIN_RIGHT;
        int y = MARGIN_TOP;
        
        // Calculate mana percentage
        float manaPercentage = manaData.getMaxMana() > 0 
            ? manaData.getCurrentMana() / manaData.getMaxMana() 
            : 0;
        int filledWidth = (int) (BAR_WIDTH * manaPercentage);
        
        // Get mana color
        int manaColor = manaData.getColor();
        
        // Draw background (dark semi-transparent)
        graphics.fill(x - BORDER_SIZE, y - BORDER_SIZE, 
                     x + BAR_WIDTH + BORDER_SIZE, y + BAR_HEIGHT + BORDER_SIZE, 
                     0xFF000000);
        
        // Draw empty bar background (darker version of mana color)
        int darkerColor = darkenColor(manaColor, 0.3f);
        graphics.fill(x, y, x + BAR_WIDTH, y + BAR_HEIGHT, darkerColor);
        
        // Draw filled mana bar
        if (filledWidth > 0) {
            graphics.fill(x, y, x + filledWidth, y + BAR_HEIGHT, manaColor);
        }
        
        // Draw border
        graphics.fill(x - BORDER_SIZE, y - BORDER_SIZE, x + BAR_WIDTH + BORDER_SIZE, y, 0xFFFFFFFF); // Top
        graphics.fill(x - BORDER_SIZE, y + BAR_HEIGHT, x + BAR_WIDTH + BORDER_SIZE, y + BAR_HEIGHT + BORDER_SIZE, 0xFFFFFFFF); // Bottom
        graphics.fill(x - BORDER_SIZE, y, x, y + BAR_HEIGHT, 0xFFFFFFFF); // Left
        graphics.fill(x + BAR_WIDTH, y, x + BAR_WIDTH + BORDER_SIZE, y + BAR_HEIGHT, 0xFFFFFFFF); // Right
        
        // Draw mana text (current/max)
        String manaText = String.format("%.0f/%.0f", manaData.getCurrentMana(), manaData.getMaxMana());
        int textWidth = mc.font.width(manaText);
        int textX = x + (BAR_WIDTH - textWidth) / 2;
        int textY = y + BAR_HEIGHT + 4;
        
        // Draw text shadow for better visibility
        graphics.drawString(mc.font, manaText, textX + 1, textY + 1, 0xFF000000, false);
        graphics.drawString(mc.font, manaText, textX, textY, 0xFFFFFFFF, false);
        
        // Draw "Mana" label above the bar
        String label = "Mana";
        int labelWidth = mc.font.width(label);
        int labelX = x + (BAR_WIDTH - labelWidth) / 2;
        int labelY = y - 10;
        graphics.drawString(mc.font, label, labelX + 1, labelY + 1, 0xFF000000, false);
        graphics.drawString(mc.font, label, labelX, labelY, 0xFFFFFFFF, false);
    }
    
    /**
     * Darkens a color by a given factor
     */
    private int darkenColor(int color, float factor) {
        int a = (color >> 24) & 0xFF;
        int r = (int)(((color >> 16) & 0xFF) * factor);
        int g = (int)(((color >> 8) & 0xFF) * factor);
        int b = (int)((color & 0xFF) * factor);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
