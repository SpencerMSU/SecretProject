package com.example.examplemod.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class RarityUpgradeStoneItem extends Item {
    
    public RarityUpgradeStoneItem(Properties properties) {
        super(properties);
    }
    
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.literal(""));
        tooltipComponents.add(
            Component.literal("Используйте на наковальне:")
                .withStyle(ChatFormatting.GRAY)
        );
        tooltipComponents.add(
            Component.literal("  Аксессуар + Камень = Повышение редкости")
                .withStyle(ChatFormatting.LIGHT_PURPLE)
        );
        tooltipComponents.add(Component.literal(""));
        tooltipComponents.add(
            Component.literal("Крафтится из 9 камней заточки")
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC)
        );
    }
    
    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}

