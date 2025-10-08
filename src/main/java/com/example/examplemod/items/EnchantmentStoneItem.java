package com.example.examplemod.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * Камень заточки - используется для улучшения аксессуаров на верстаке
 * 1 камень = +1 уровень аксессуара
 */
public class EnchantmentStoneItem extends Item {
    public EnchantmentStoneItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        
        tooltipComponents.add(Component.literal("━━━━━━━━━━━━━━━━━━━━").withStyle(ChatFormatting.DARK_GRAY));
        tooltipComponents.add(Component.literal("Магический камень для улучшения")
            .withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.literal("аксессуаров на верстаке")
            .withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.literal("━━━━━━━━━━━━━━━━━━━━").withStyle(ChatFormatting.DARK_GRAY));
        tooltipComponents.add(Component.literal("📖 Рецепт:")
            .withStyle(ChatFormatting.YELLOW));
        tooltipComponents.add(Component.literal("  • Аксессуар + Камень заточки")
            .withStyle(ChatFormatting.WHITE));
        tooltipComponents.add(Component.literal("  • Результат: Аксессуар +1 уровень")
            .withStyle(ChatFormatting.GREEN));
        tooltipComponents.add(Component.literal("━━━━━━━━━━━━━━━━━━━━").withStyle(ChatFormatting.DARK_GRAY));
        tooltipComponents.add(Component.literal("⚠ Редкость аксессуара сохраняется!")
            .withStyle(ChatFormatting.GOLD));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true; // Всегда с энчант-эффектом
    }
}
