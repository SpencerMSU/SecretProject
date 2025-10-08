package com.example.examplemod.accessory;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.items.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

import java.util.Random;

/**
 * Обработчик выпадения аксессуаров с мобов
 */
@EventBusSubscriber(modid = ExampleMod.MODID)
public class AccessoryLootHandler {
    
    private static final Random RANDOM = new Random();
    
    // Шансы выпадения (в процентах)
    private static final double ACCESSORY_DROP_CHANCE = 5.0; // 5% шанс выпадения аксессуара
    private static final double ENCHANTMENT_STONE_DROP_CHANCE = 10.0; // 10% шанс выпадения камня
    
    @SubscribeEvent
    public static void onMobDrop(LivingDropsEvent event) {
        Entity entity = event.getEntity();
        
        // Только с монстров
        if (!(entity instanceof Monster)) {
            return;
        }
        
        // Дроп камня заточки
        if (RANDOM.nextDouble() * 100 < ENCHANTMENT_STONE_DROP_CHANCE) {
            int amount = 1 + RANDOM.nextInt(2); // 1-2 камня
            ItemStack stone = new ItemStack(ModItems.ENCHANTMENT_STONE.get(), amount);
            entity.spawnAtLocation(stone);
        }
        
        // Дроп аксессуара
        if (RANDOM.nextDouble() * 100 < ACCESSORY_DROP_CHANCE) {
            ItemStack accessory = createRandomAccessory();
            if (!accessory.isEmpty()) {
                entity.spawnAtLocation(accessory);
            }
        }
    }
    
    /**
     * Создает случайный аксессуар с случайной редкостью и стихией
     */
    private static ItemStack createRandomAccessory() {
        // Выбираем случайную стихию (50/50 огонь или вода)
        AccessoryElement element = RANDOM.nextBoolean() ? AccessoryElement.FIRE : AccessoryElement.WATER;
        
        // Выбираем случайный тип аксессуара
        AccessoryType[] types = AccessoryType.values();
        AccessoryType type = types[RANDOM.nextInt(types.length)];
        
        // Выбираем редкость на основе весов
        AccessoryRarity rarity = selectRandomRarity();
        
        // Создаем предмет
        ItemStack stack = createAccessoryStack(element, type);
        
        if (!stack.isEmpty()) {
            // Устанавливаем редкость и начальный уровень
            BaseAccessoryItem.setRarity(stack, rarity);
            BaseAccessoryItem.setLevel(stack, 1);
        }
        
        return stack;
    }
    
    /**
     * Выбирает редкость на основе весов выпадения
     */
    private static AccessoryRarity selectRandomRarity() {
        int totalWeight = 0;
        for (AccessoryRarity rarity : AccessoryRarity.values()) {
            totalWeight += rarity.getDropWeight();
        }
        
        int randomValue = RANDOM.nextInt(totalWeight);
        int currentWeight = 0;
        
        for (AccessoryRarity rarity : AccessoryRarity.values()) {
            currentWeight += rarity.getDropWeight();
            if (randomValue < currentWeight) {
                return rarity;
            }
        }
        
        return AccessoryRarity.COMMON; // Fallback
    }
    
    /**
     * Создает ItemStack для конкретного аксессуара
     */
    private static ItemStack createAccessoryStack(AccessoryElement element, AccessoryType type) {
        if (element == AccessoryElement.FIRE) {
            return switch (type) {
                case RING -> new ItemStack(ModItems.FIRE_RING.get());
                case NECKLACE -> new ItemStack(ModItems.FIRE_NECKLACE.get());
                case BRACELET -> new ItemStack(ModItems.FIRE_BRACELET.get());
                case BELT -> new ItemStack(ModItems.FIRE_BELT.get());
                case CHARM -> new ItemStack(ModItems.FIRE_CHARM.get());
                case CLOAK -> new ItemStack(ModItems.FIRE_CLOAK.get());
            };
        } else { // WATER
            return switch (type) {
                case RING -> new ItemStack(ModItems.WATER_RING.get());
                case NECKLACE -> new ItemStack(ModItems.WATER_NECKLACE.get());
                case BRACELET -> new ItemStack(ModItems.WATER_BRACELET.get());
                case BELT -> new ItemStack(ModItems.WATER_BELT.get());
                case CHARM -> new ItemStack(ModItems.WATER_CHARM.get());
                case CLOAK -> new ItemStack(ModItems.WATER_CLOAK.get());
            };
        }
    }
}
