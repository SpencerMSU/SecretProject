package com.example.examplemod.client.hud;

import com.example.examplemod.client.ClientConfig;
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
    }

    private static void fill(GuiGraphics g, int x, int y, int w, int h, int color) {
        g.fill(x, y, x + w, y + h, color);
    }

    private static void verticalGradient(GuiGraphics g, int x, int y, int w, int h, int topColor, int bottomColor) {
        if (h <= 0) return;
        g.fillGradient(x, y, x + w, y + h, topColor, bottomColor);
    }
}
