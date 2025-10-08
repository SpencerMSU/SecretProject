package com.example.examplemod.accessory;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.common.util.Lazy;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

/**
 * Базовый класс для всех аксессуаров
 * Поддерживает: уровни прокачки (1-10), редкость, NBT данные
 */
public abstract class BaseAccessoryItem extends Item implements ICurioItem {
    protected final AccessoryType accessoryType;
    protected final AccessoryElement element;
    protected final Lazy<AccessoryStats> baseStats;

    // NBT ключи
    public static final String NBT_LEVEL = "AccessoryLevel";
    public static final String NBT_RARITY = "AccessoryRarity";

    public BaseAccessoryItem(AccessoryType type, AccessoryElement element, Properties properties) {
        super(properties.stacksTo(1)); // Аксессуары не стакаются
        this.accessoryType = type;
        this.element = element;
        
        // Ленивая инициализация базовых статов
        this.baseStats = Lazy.of(() -> {
            if (element == AccessoryElement.FIRE) {
                return AccessoryStats.createFireStats(type);
            } else {
                return AccessoryStats.createWaterStats(type);
            }
        });
    }

    public AccessoryType getAccessoryType() {
        return accessoryType;
    }

    public AccessoryElement getElement() {
        return element;
    }

    /**
     * Получить уровень аксессуара (1-10)
     */
    public static int getLevel(ItemStack stack) {
        if (stack.isEmpty()) return 1;
        
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        int level = customData.copyTag().getInt(NBT_LEVEL);
        return level > 0 ? level : 1; // По умолчанию 1 если не установлен
    }

    /**
     * Установить уровень аксессуара
     */
    public static void setLevel(ItemStack stack, int level) {
        int finalLevel = Math.clamp(level, 1, 10);
        stack.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY, data -> 
            data.update(tag -> tag.putInt(NBT_LEVEL, finalLevel))
        );
    }

    /**
     * Получить редкость аксессуара
     */
    public static AccessoryRarity getRarity(ItemStack stack) {
        if (stack.isEmpty()) return AccessoryRarity.COMMON;
        
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        int ordinal = customData.copyTag().getInt(NBT_RARITY);
        
        if (ordinal >= 0 && ordinal < AccessoryRarity.values().length) {
            return AccessoryRarity.values()[ordinal];
        }
        return AccessoryRarity.COMMON;
    }

    /**
     * Установить редкость аксессуара
     */
    public static void setRarity(ItemStack stack, AccessoryRarity rarity) {
        stack.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY, data -> 
            data.update(tag -> tag.putInt(NBT_RARITY, rarity.ordinal()))
        );
    }

    /**
     * Получить финальные характеристики с учетом уровня и редкости
     */
    public AccessoryStats getStats(ItemStack stack) {
        int level = getLevel(stack);
        AccessoryRarity rarity = getRarity(stack);
        return baseStats.get().calculate(level, rarity);
    }

    /**
     * Можно ли улучшить этот аксессуар
     */
    public static boolean canUpgrade(ItemStack stack) {
        return getLevel(stack) < 10;
    }

    /**
     * Улучшить аксессуар на 1 уровень
     */
    public static boolean upgrade(ItemStack stack) {
        if (!canUpgrade(stack)) {
            return false;
        }
        setLevel(stack, getLevel(stack) + 1);
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        
        int level = getLevel(stack);
        AccessoryRarity rarity = getRarity(stack);
        AccessoryStats stats = getStats(stack);

        // Уровень и редкость
        tooltipComponents.add(Component.literal("━━━━━━━━━━━━━━━━━━━━").withStyle(ChatFormatting.DARK_GRAY));
        
        tooltipComponents.add(Component.literal("Уровень: ")
            .withStyle(ChatFormatting.GRAY)
            .append(Component.literal(level + "/10")
                .withStyle(level == 10 ? ChatFormatting.GOLD : ChatFormatting.WHITE)));
        
        tooltipComponents.add(Component.literal("Редкость: ")
            .withStyle(ChatFormatting.GRAY)
            .append(Component.literal(rarity.getRussianName())
                .withStyle(style -> style.withColor(rarity.getColor()))));

        tooltipComponents.add(Component.literal("Стихия: ")
            .withStyle(ChatFormatting.GRAY)
            .append(Component.literal(element.getRussianName())
                .withStyle(style -> style.withColor(element.getColor()))));

        // Характеристики
        tooltipComponents.add(Component.literal("━━━━━━━━━━━━━━━━━━━━").withStyle(ChatFormatting.DARK_GRAY));
        
        if (stats.getHealth() > 0) {
            tooltipComponents.add(Component.literal("❤ Здоровье: ")
                .withStyle(ChatFormatting.RED)
                .append(Component.literal("+" + String.format("%.1f", stats.getHealth()))
                    .withStyle(ChatFormatting.WHITE)));
        }
        
        if (stats.getMana() > 0) {
            tooltipComponents.add(Component.literal("✦ Мана: ")
                .withStyle(ChatFormatting.AQUA)
                .append(Component.literal("+" + String.format("%.1f", stats.getMana()))
                    .withStyle(ChatFormatting.WHITE)));
        }
        
        if (stats.getManaRegen() > 0) {
            tooltipComponents.add(Component.literal("↻ Реген маны: ")
                .withStyle(ChatFormatting.BLUE)
                .append(Component.literal("+" + String.format("%.1f", stats.getManaRegen()) + "/сек")
                    .withStyle(ChatFormatting.WHITE)));
        }
        
        if (stats.getDamage() > 0) {
            tooltipComponents.add(Component.literal("⚔ Урон: ")
                .withStyle(ChatFormatting.RED)
                .append(Component.literal("+" + String.format("%.1f", stats.getDamage()))
                    .withStyle(ChatFormatting.WHITE)));
        }
        
        if (stats.getDefense() > 0) {
            tooltipComponents.add(Component.literal("🛡 Защита: ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal("+" + String.format("%.1f", stats.getDefense()))
                    .withStyle(ChatFormatting.WHITE)));
        }
        
        if (stats.getSpeed() > 0) {
            tooltipComponents.add(Component.literal("➤ Скорость: ")
                .withStyle(ChatFormatting.WHITE)
                .append(Component.literal("+" + String.format("%.3f", stats.getSpeed()))
                    .withStyle(ChatFormatting.WHITE)));
        }
        
        if (stats.getElementalPower() > 0) {
            tooltipComponents.add(Component.literal("✨ Сила стихии: ")
                .withStyle(style -> style.withColor(element.getColor()))
                .append(Component.literal("+" + String.format("%.1f", stats.getElementalPower()))
                    .withStyle(ChatFormatting.WHITE)));
        }
        
        if (stats.getElementalResistance() > 0) {
            tooltipComponents.add(Component.literal("⛨ Сопротивление: ")
                .withStyle(style -> style.withColor(element.getColor()))
                .append(Component.literal("+" + String.format("%.1f", stats.getElementalResistance()))
                    .withStyle(ChatFormatting.WHITE)));
        }

        // Подсказки
        tooltipComponents.add(Component.literal("━━━━━━━━━━━━━━━━━━━━").withStyle(ChatFormatting.DARK_GRAY));
        
        if (canUpgrade(stack)) {
            tooltipComponents.add(Component.literal("💎 Можно улучшить камнем заточки")
                .withStyle(ChatFormatting.GREEN));
        } else {
            tooltipComponents.add(Component.literal("⭐ Максимальный уровень!")
                .withStyle(ChatFormatting.GOLD));
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        AccessoryRarity rarity = getRarity(stack);
        int level = getLevel(stack);
        
        String prefix = level > 0 ? "[+" + level + "] " : "";
        
        return Component.literal(prefix)
            .append(super.getName(stack))
            .withStyle(style -> style.withColor(rarity.getColor()));
    }

    // Curios API методы
    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}
