package com.example.examplemod.accessory;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.items.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@EventBusSubscriber(modid = ExampleMod.MODID)
public class AccessoryDropHandler {
    
    private static final Random RANDOM = new Random();
    private static List<Item> fireAccessories = null;
    private static List<Item> waterAccessories = null;
    
    private static List<Item> getFireAccessories() {
        if (fireAccessories == null) {
            fireAccessories = List.of(
                ModItems.FIRE_CHARM.get(),
                ModItems.FIRE_MASK.get(),
                ModItems.FIRE_HAT.get(),
                ModItems.FIRE_NECKLACE.get(),
                ModItems.FIRE_CAPE.get(),
                ModItems.FIRE_BACK.get(),
                ModItems.FIRE_WRIST.get(),
                ModItems.FIRE_HAND_1.get(),
                ModItems.FIRE_HAND_2.get(),
                ModItems.FIRE_RING_1.get(),
                ModItems.FIRE_RING_2.get(),
                ModItems.FIRE_BELT.get(),
                ModItems.FIRE_ANKLET.get(),
                ModItems.FIRE_SHOES.get()
            );
        }
        return fireAccessories;
    }
    
    private static List<Item> getWaterAccessories() {
        if (waterAccessories == null) {
            waterAccessories = List.of(
                ModItems.WATER_CHARM.get(),
                ModItems.WATER_MASK.get(),
                ModItems.WATER_HAT.get(),
                ModItems.WATER_NECKLACE.get(),
                ModItems.WATER_CAPE.get(),
                ModItems.WATER_BACK.get(),
                ModItems.WATER_WRIST.get(),
                ModItems.WATER_HAND_1.get(),
                ModItems.WATER_HAND_2.get(),
                ModItems.WATER_RING_1.get(),
                ModItems.WATER_RING_2.get(),
                ModItems.WATER_BELT.get(),
                ModItems.WATER_ANKLET.get(),
                ModItems.WATER_SHOES.get()
            );
        }
        return waterAccessories;
    }
    
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        
        if (!(entity instanceof Monster)) {
            return;
        }
        
        if (entity.level().isClientSide) {
            return;
        }
        
        ResourceLocation entityId = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        String entityIdString = entityId.toString();
        
        double dropChance = AccessoryConfig.NORMAL_MOB_DROP_CHANCE.get();
        
        if (AccessoryConfig.NETHER_MOBS.get().contains(entityIdString)) {
            dropChance = AccessoryConfig.NETHER_MOB_DROP_CHANCE.get();
        }
        
        if (RANDOM.nextDouble() < dropChance) {
            List<Item> accessories = null;
            
            // Определяем тип аксессуаров в зависимости от моба
            if (AccessoryConfig.FIRE_ACCESSORY_MOBS.get().contains(entityIdString) || 
                AccessoryConfig.NETHER_MOBS.get().contains(entityIdString)) {
                accessories = getFireAccessories();
            } else if (isWaterMob(entityIdString)) {
                accessories = getWaterAccessories();
            }
            
            if (accessories != null) {
                Item accessory = accessories.get(RANDOM.nextInt(accessories.size()));
                ItemStack stack = new ItemStack(accessory);
                
                AccessoryRarity rarity = rollRarity();
                int level = 1;
                
                stack.set(ModDataComponents.ACCESSORY_DATA.get(), new AccessoryData(level, rarity));
                
                event.getDrops().add(entity.spawnAtLocation(stack));
            }
        }
    }
    
    private static boolean isWaterMob(String entityIdString) {
        // Водные мобы, которые дропают водные аксессуары
        return entityIdString.equals("minecraft:drowned") ||
               entityIdString.equals("minecraft:guardian") ||
               entityIdString.equals("minecraft:elder_guardian") ||
               entityIdString.equals("minecraft:cod") ||
               entityIdString.equals("minecraft:salmon") ||
               entityIdString.equals("minecraft:tropical_fish") ||
               entityIdString.equals("minecraft:pufferfish") ||
               entityIdString.equals("minecraft:squid") ||
               entityIdString.equals("minecraft:glow_squid");
    }
    
    private static AccessoryRarity rollRarity() {
        double totalWeight = 
            AccessoryConfig.COMMON_CHANCE.get() +
            AccessoryConfig.UNCOMMON_CHANCE.get() +
            AccessoryConfig.RARE_CHANCE.get() +
            AccessoryConfig.EPIC_CHANCE.get() +
            AccessoryConfig.LEGENDARY_CHANCE.get() +
            AccessoryConfig.MYTHICAL_CHANCE.get();
        
        double roll = RANDOM.nextDouble() * totalWeight;
        double current = 0;
        
        current += AccessoryConfig.COMMON_CHANCE.get();
        if (roll < current) return AccessoryRarity.COMMON;
        
        current += AccessoryConfig.UNCOMMON_CHANCE.get();
        if (roll < current) return AccessoryRarity.UNCOMMON;
        
        current += AccessoryConfig.RARE_CHANCE.get();
        if (roll < current) return AccessoryRarity.RARE;
        
        current += AccessoryConfig.EPIC_CHANCE.get();
        if (roll < current) return AccessoryRarity.EPIC;
        
        current += AccessoryConfig.LEGENDARY_CHANCE.get();
        if (roll < current) return AccessoryRarity.LEGENDARY;
        
        return AccessoryRarity.MYTHICAL;
    }
}

