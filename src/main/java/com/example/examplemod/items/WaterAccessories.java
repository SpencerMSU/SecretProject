package com.example.examplemod.items;

import com.example.examplemod.accessory.BaseAccessoryItem;
import com.example.examplemod.effects.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;

import java.util.Map;

public class WaterAccessories {
    
    // Амулет - магический предмет, дает исцеление и регенерацию маны
    public static class WaterCharmItem extends BaseAccessoryItem {
        public WaterCharmItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.WATER_BREATHING, 0,
                MobEffects.REGENERATION, 0,
                BuiltInRegistries.MOB_EFFECT.wrapAsHolder(ModEffects.MANA_REGENERATION.get()), 0
            ));
        }
    }
    
    // Маска - защищает голову, дает ночное зрение и подводное дыхание
    public static class WaterMaskItem extends BaseAccessoryItem {
        public WaterMaskItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.WATER_BREATHING, 0,
                MobEffects.NIGHT_VISION, 0,
                MobEffects.DOLPHINS_GRACE, 0
            ));
        }
    }
    
    // Шляпа - защищает голову, дает регенерацию и поглощение
    public static class WaterHatItem extends BaseAccessoryItem {
        public WaterHatItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.WATER_BREATHING, 0,
                MobEffects.REGENERATION, 0,
                MobEffects.ABSORPTION, 0
            ));
        }
    }
    
    // Ожерелье - защищает шею, дает здоровье и защиту
    public static class WaterNecklaceItem extends BaseAccessoryItem {
        public WaterNecklaceItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.WATER_BREATHING, 0,
                MobEffects.HEALTH_BOOST, 1,
                MobEffects.DAMAGE_RESISTANCE, 0
            ));
        }
    }
    
    // Плащ - защищает спину, дает плавное падение и защиту
    public static class WaterCapeItem extends BaseAccessoryItem {
        public WaterCapeItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.WATER_BREATHING, 0,
                MobEffects.SLOW_FALLING, 0,
                MobEffects.DAMAGE_RESISTANCE, 0
            ));
        }
    }
    
    // Рюкзак - защищает спину, дает защиту и поглощение
    public static class WaterBackItem extends BaseAccessoryItem {
        public WaterBackItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.WATER_BREATHING, 0,
                MobEffects.DAMAGE_RESISTANCE, 1,
                MobEffects.ABSORPTION, 1
            ));
        }
    }
    
    // Браслет - защищает запястье, дает скорость работы и исцеление
    public static class WaterWristItem extends BaseAccessoryItem {
        public WaterWristItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.WATER_BREATHING, 0,
                MobEffects.DIG_SPEED, 1,
                MobEffects.REGENERATION, 0
            ));
        }
    }
    
    // Перчатки - защищают руки, дают исцеление и скорость работы
    public static class WaterHandItem extends BaseAccessoryItem {
        public WaterHandItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.WATER_BREATHING, 0,
                MobEffects.REGENERATION, 1,
                MobEffects.DIG_SPEED, 0
            ));
        }
    }
    
    // Кольца - магические предметы, дают удачу и регенерацию маны
    public static class WaterRingItem extends BaseAccessoryItem {
        public WaterRingItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.WATER_BREATHING, 0,
                MobEffects.LUCK, 1,
                BuiltInRegistries.MOB_EFFECT.wrapAsHolder(ModEffects.MANA_REGENERATION.get()), 0
            ));
        }
    }
    
    // Пояс - защищает талию, дает поглощение и защиту
    public static class WaterBeltItem extends BaseAccessoryItem {
        public WaterBeltItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.WATER_BREATHING, 0,
                MobEffects.ABSORPTION, 2,
                MobEffects.DAMAGE_RESISTANCE, 0
            ));
        }
    }
    
    // Ножной браслет - защищает ноги, дает прыгучесть и скорость
    public static class WaterAnkletItem extends BaseAccessoryItem {
        public WaterAnkletItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.WATER_BREATHING, 0,
                MobEffects.JUMP, 1,
                MobEffects.MOVEMENT_SPEED, 0
            ));
        }
    }
    
    // Ботинки - защищают ноги, дают скорость и прыгучесть
    public static class WaterShoesItem extends BaseAccessoryItem {
        public WaterShoesItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.WATER_BREATHING, 0,
                MobEffects.MOVEMENT_SPEED, 1,
                MobEffects.JUMP, 0
            ));
        }
    }
}
