package com.example.examplemod.items;

import com.example.examplemod.accessory.BaseAccessoryItem;
import com.example.examplemod.effects.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;

import java.util.Map;

public class FireAccessories {
    
    // Амулет - магический предмет, дает силу и регенерацию маны
    public static class FireCharmItem extends BaseAccessoryItem {
        public FireCharmItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.FIRE_RESISTANCE, 0,
                MobEffects.DAMAGE_BOOST, 0,
                BuiltInRegistries.MOB_EFFECT.wrapAsHolder(ModEffects.MANA_REGENERATION.get()), 0
            ));
        }
    }
    
    // Маска - защищает голову, дает зрение и дыхание
    public static class FireMaskItem extends BaseAccessoryItem {
        public FireMaskItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.FIRE_RESISTANCE, 0,
                MobEffects.NIGHT_VISION, 0,
                MobEffects.WATER_BREATHING, 0
            ));
        }
    }
    
    // Шляпа - защищает голову, дает регенерацию и поглощение
    public static class FireHatItem extends BaseAccessoryItem {
        public FireHatItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.FIRE_RESISTANCE, 0,
                MobEffects.REGENERATION, 0,
                MobEffects.ABSORPTION, 0
            ));
        }
    }
    
    // Ожерелье - защищает шею, дает здоровье и защиту
    public static class FireNecklaceItem extends BaseAccessoryItem {
        public FireNecklaceItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.FIRE_RESISTANCE, 0,
                MobEffects.HEALTH_BOOST, 1,
                MobEffects.DAMAGE_RESISTANCE, 0
            ));
        }
    }
    
    // Плащ - защищает спину, дает плавное падение и защиту
    public static class FireCapeItem extends BaseAccessoryItem {
        public FireCapeItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.FIRE_RESISTANCE, 0,
                MobEffects.SLOW_FALLING, 0,
                MobEffects.DAMAGE_RESISTANCE, 0
            ));
        }
    }
    
    // Рюкзак - защищает спину, дает защиту и поглощение
    public static class FireBackItem extends BaseAccessoryItem {
        public FireBackItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.FIRE_RESISTANCE, 0,
                MobEffects.DAMAGE_RESISTANCE, 1,
                MobEffects.ABSORPTION, 1
            ));
        }
    }
    
    // Браслет - защищает запястье, дает скорость работы и силу
    public static class FireWristItem extends BaseAccessoryItem {
        public FireWristItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.FIRE_RESISTANCE, 0,
                MobEffects.DIG_SPEED, 1,
                MobEffects.DAMAGE_BOOST, 0
            ));
        }
    }
    
    // Перчатки - защищают руки, дают силу и скорость работы
    public static class FireHandItem extends BaseAccessoryItem {
        public FireHandItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.FIRE_RESISTANCE, 0,
                MobEffects.DAMAGE_BOOST, 1,
                MobEffects.DIG_SPEED, 0
            ));
        }
    }
    
    // Кольца - магические предметы, дают удачу и регенерацию маны
    public static class FireRingItem extends BaseAccessoryItem {
        public FireRingItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.FIRE_RESISTANCE, 0,
                MobEffects.LUCK, 1,
                BuiltInRegistries.MOB_EFFECT.wrapAsHolder(ModEffects.MANA_REGENERATION.get()), 0
            ));
        }
    }
    
    // Пояс - защищает талию, дает поглощение и защиту
    public static class FireBeltItem extends BaseAccessoryItem {
        public FireBeltItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.FIRE_RESISTANCE, 0,
                MobEffects.ABSORPTION, 2,
                MobEffects.DAMAGE_RESISTANCE, 0
            ));
        }
    }
    
    // Ножной браслет - защищает ноги, дает прыгучесть и скорость
    public static class FireAnkletItem extends BaseAccessoryItem {
        public FireAnkletItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.FIRE_RESISTANCE, 0,
                MobEffects.JUMP, 1,
                MobEffects.MOVEMENT_SPEED, 0
            ));
        }
    }
    
    // Ботинки - защищают ноги, дают скорость и прыгучесть
    public static class FireShoesItem extends BaseAccessoryItem {
        public FireShoesItem(Properties properties) {
            super(properties, Map.<Holder<MobEffect>, Integer>of(
                MobEffects.FIRE_RESISTANCE, 0,
                MobEffects.MOVEMENT_SPEED, 1,
                MobEffects.JUMP, 0
            ));
        }
    }
}