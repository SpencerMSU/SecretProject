package com.example.examplemod.accessory;

import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.Map;

public class BaseAccessoryItem extends Item implements Accessory {
    
    private final Map<Holder<MobEffect>, Integer> baseEffects;
    
    public BaseAccessoryItem(Properties properties, Map<Holder<MobEffect>, Integer> baseEffects) {
        super(properties);
        this.baseEffects = baseEffects;
    }
    
    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        LivingEntity entity = reference.entity();
        
        // Применяем эффекты каждые 5 секунд (100 тиков)
        if (entity.level().getGameTime() % 100 != 0) {
            return;
        }
        
        AccessoryData data = stack.getOrDefault(ModDataComponents.ACCESSORY_DATA.get(), AccessoryData.createDefault());
        double multiplier = data.getStatMultiplier();
        
        for (Map.Entry<Holder<MobEffect>, Integer> entry : baseEffects.entrySet()) {
            int amplifier = (int) Math.floor(entry.getValue() * multiplier);
            // Увеличиваем длительность до 10 секунд (200 тиков)
            entity.addEffect(new MobEffectInstance(
                entry.getKey(),
                200,
                amplifier,
                true,
                false
            ));
        }
    }
    
    @Override
    public void onEquip(ItemStack stack, SlotReference reference) {
        AccessoryData data = stack.getOrDefault(ModDataComponents.ACCESSORY_DATA.get(), AccessoryData.createDefault());
        
        if (reference.entity().level().isClientSide) {
            reference.entity().sendSystemMessage(
                Component.literal("✨ Надет аксессуар: ")
                    .append(Component.translatable(stack.getDescriptionId()))
                    .append(" [")
                    .append(Component.translatable("rarity." + data.rarity().getName())
                        .withStyle(data.rarity().getColor()))
                    .append("] Ур. " + data.level())
                    .withStyle(ChatFormatting.GOLD)
            );
        }
    }
    
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        
        AccessoryData data = stack.getOrDefault(ModDataComponents.ACCESSORY_DATA.get(), AccessoryData.createDefault());
        
        // Разделитель
        tooltipComponents.add(Component.literal("━━━━━━━━━━━━━━━━━━━━").withStyle(ChatFormatting.DARK_GRAY));
        
        // Редкость с красивым форматированием
        tooltipComponents.add(
            Component.literal("⬥ ")
                .withStyle(data.rarity().getColor())
                .append(Component.translatable("rarity." + data.rarity().getName())
                    .withStyle(data.rarity().getColor(), ChatFormatting.BOLD))
        );
        
        // Уровень с прогресс-баром
        String progressBar = getProgressBar(data.level(), AccessoryData.MAX_LEVEL);
        tooltipComponents.add(
            Component.literal("⬆ Уровень: ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal(data.level() + "/" + AccessoryData.MAX_LEVEL)
                    .withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD))
        );
        tooltipComponents.add(
            Component.literal("  " + progressBar)
                .withStyle(ChatFormatting.YELLOW)
        );
        
        // Множитель силы
        tooltipComponents.add(
            Component.literal("⚡ Множитель: ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal(String.format("×%.2f", data.getStatMultiplier()))
                    .withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD))
        );
        
        tooltipComponents.add(Component.literal("━━━━━━━━━━━━━━━━━━━━").withStyle(ChatFormatting.DARK_GRAY));
        
        // Заголовок эффектов
        tooltipComponents.add(
            Component.literal("✦ ЭФФЕКТЫ:")
                .withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD)
        );
        
        // Эффекты с реальными значениями
        for (Map.Entry<Holder<MobEffect>, Integer> entry : baseEffects.entrySet()) {
            int baseAmplifier = entry.getValue();
            int actualAmplifier = (int) Math.floor(baseAmplifier * data.getStatMultiplier());
            
            String effectName = Component.translatable(entry.getKey().value().getDescriptionId()).getString();
            
            // Разные символы для разных эффектов
            String icon = getEffectIcon(entry.getKey().value().getDescriptionId());
            
            tooltipComponents.add(
                Component.literal("  " + icon + " ")
                    .withStyle(ChatFormatting.WHITE)
                    .append(Component.literal(effectName)
                        .withStyle(ChatFormatting.GREEN))
                    .append(Component.literal(" " + getRomanNumeral(actualAmplifier + 1))
                        .withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD))
            );
        }
        
        tooltipComponents.add(Component.literal("━━━━━━━━━━━━━━━━━━━━").withStyle(ChatFormatting.DARK_GRAY));
    }
    
    private String getProgressBar(int current, int max) {
        int filled = (int) ((double) current / max * 10);
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < 10; i++) {
            bar.append(i < filled ? "█" : "░");
        }
        bar.append("]");
        return bar.toString();
    }
    
    private String getEffectIcon(String effectId) {
        if (effectId.contains("fire_resistance")) return "🔥";
        if (effectId.contains("damage_boost") || effectId.contains("strength")) return "⚔";
        if (effectId.contains("regeneration")) return "❤";
        if (effectId.contains("speed")) return "➤";
        if (effectId.contains("health_boost")) return "♥";
        if (effectId.contains("resistance")) return "🛡";
        if (effectId.contains("night_vision")) return "👁";
        if (effectId.contains("haste") || effectId.contains("dig_speed")) return "⛏";
        if (effectId.contains("luck")) return "☘";
        if (effectId.contains("absorption")) return "◈";
        if (effectId.contains("jump")) return "↑";
        if (effectId.contains("slow_falling")) return "🪶";
        if (effectId.contains("water_breathing")) return "💧";
        if (effectId.contains("mana") || effectId.contains("magic") || effectId.contains("mana_regeneration")) return "✨";
        return "•";
    }
    
    private String getRomanNumeral(int number) {
        if (number <= 0) return "0";
        if (number == 1) return "I";
        if (number == 2) return "II";
        if (number == 3) return "III";
        if (number == 4) return "IV";
        if (number == 5) return "V";
        if (number == 6) return "VI";
        if (number == 7) return "VII";
        if (number == 8) return "VIII";
        if (number == 9) return "IX";
        if (number == 10) return "X";
        return String.valueOf(number);
    }
}

