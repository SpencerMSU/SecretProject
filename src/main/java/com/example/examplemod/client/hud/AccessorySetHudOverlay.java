package com.example.examplemod.client.hud;

import com.example.examplemod.accessory.AccessoryElement;
import com.example.examplemod.accessory.AccessorySetManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.world.entity.player.Player;

/**
 * HUD для отображения информации о надетых наборах аксессуаров
 */
public class AccessorySetHudOverlay implements LayeredDraw.Layer {
    
    private static final int COLOR_FIRE = 0xFFFF6600;
    private static final int COLOR_WATER = 0xFF0099FF;
    private static final int COLOR_GOLD = 0xFFFFAA00;
    
    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;
        
        Player player = minecraft.player;
        AccessorySetManager.EquippedSetInfo setInfo = AccessorySetManager.getEquippedSetInfo(player);
        
        // Позиция HUD (справа внизу, над хотбаром)
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();
        int x = screenWidth - 150;
        int y = screenHeight - 80;
        
        RenderSystem.enableBlend();
        
        // Отображаем огненный набор
        int fireCount = setInfo.typesByElement.get(AccessoryElement.FIRE).size();
        if (fireCount > 0) {
            String fireText = "🔥 Fire: " + fireCount + "/6";
            guiGraphics.drawString(minecraft.font, fireText, x, y, COLOR_FIRE);
            
            if (setInfo.hasFullFireSet) {
                guiGraphics.drawString(minecraft.font, "  [FULL SET]", x + 80, y, COLOR_GOLD);
            }
            if (setInfo.hasMaxFireSet) {
                guiGraphics.drawString(minecraft.font, "  [MAX]", x + 80, y + 10, COLOR_GOLD);
            }
            
            y += 20;
        }
        
        // Отображаем водный набор
        int waterCount = setInfo.typesByElement.get(AccessoryElement.WATER).size();
        if (waterCount > 0) {
            String waterText = "💧 Water: " + waterCount + "/6";
            guiGraphics.drawString(minecraft.font, waterText, x, y, COLOR_WATER);
            
            if (setInfo.hasFullWaterSet) {
                guiGraphics.drawString(minecraft.font, "  [FULL SET]", x + 80, y, COLOR_GOLD);
            }
            if (setInfo.hasMaxWaterSet) {
                guiGraphics.drawString(minecraft.font, "  [MAX]", x + 80, y + 10, COLOR_GOLD);
            }
        }
        
        RenderSystem.disableBlend();
    }
}
