package com.example.examplemod.accessory;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class AccessoryConfig {
    
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;
    
    // Шансы дропа
    public static final ModConfigSpec.DoubleValue NORMAL_MOB_DROP_CHANCE;
    public static final ModConfigSpec.DoubleValue NETHER_MOB_DROP_CHANCE;
    
    // Шансы редкости
    public static final ModConfigSpec.DoubleValue COMMON_CHANCE;
    public static final ModConfigSpec.DoubleValue UNCOMMON_CHANCE;
    public static final ModConfigSpec.DoubleValue RARE_CHANCE;
    public static final ModConfigSpec.DoubleValue EPIC_CHANCE;
    public static final ModConfigSpec.DoubleValue LEGENDARY_CHANCE;
    public static final ModConfigSpec.DoubleValue MYTHICAL_CHANCE;
    
    // Списки мобов
    public static final ModConfigSpec.ConfigValue<List<? extends String>> FIRE_ACCESSORY_MOBS;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> NETHER_MOBS;
    
    static {
        BUILDER.push("drop_chances");
        
        NORMAL_MOB_DROP_CHANCE = BUILDER
            .comment("Шанс выпадения аксессуара с обычного моба (0.02 = 2%)")
            .defineInRange("normal_mob_chance", 0.02, 0.0, 1.0);
        
        NETHER_MOB_DROP_CHANCE = BUILDER
            .comment("Шанс выпадения аксессуара с адского моба (0.05 = 5%)")
            .defineInRange("nether_mob_chance", 0.05, 0.0, 1.0);
        
        BUILDER.pop();
        BUILDER.push("rarity_chances");
        
        COMMON_CHANCE = BUILDER
            .comment("Относительный шанс выпадения обычной редкости")
            .defineInRange("common", 1.0, 0.0, 100.0);
        
        UNCOMMON_CHANCE = BUILDER
            .comment("Относительный шанс выпадения необычной редкости")
            .defineInRange("uncommon", 0.5, 0.0, 100.0);
        
        RARE_CHANCE = BUILDER
            .comment("Относительный шанс выпадения редкой редкости")
            .defineInRange("rare", 0.25, 0.0, 100.0);
        
        EPIC_CHANCE = BUILDER
            .comment("Относительный шанс выпадения эпической редкости")
            .defineInRange("epic", 0.1, 0.0, 100.0);
        
        LEGENDARY_CHANCE = BUILDER
            .comment("Относительный шанс выпадения легендарной редкости")
            .defineInRange("legendary", 0.05, 0.0, 100.0);
        
        MYTHICAL_CHANCE = BUILDER
            .comment("Относительный шанс выпадения мифической редкости")
            .defineInRange("mythical", 0.01, 0.0, 100.0);
        
        BUILDER.pop();
        BUILDER.push("mob_lists");
        
        FIRE_ACCESSORY_MOBS = BUILDER
            .comment("Список мобов, с которых могут выпасть огненные аксессуары")
            .defineList("fire_mobs", List.of(
                "minecraft:blaze",
                "minecraft:magma_cube",
                "minecraft:ghast",
                "minecraft:wither_skeleton",
                "minecraft:zombie_piglin",
                "minecraft:piglin_brute"
            ), obj -> obj instanceof String);
        
        NETHER_MOBS = BUILDER
            .comment("Список адских мобов (увеличенный шанс дропа)")
            .defineList("nether_mobs", List.of(
                "minecraft:blaze",
                "minecraft:magma_cube",
                "minecraft:ghast",
                "minecraft:wither_skeleton",
                "minecraft:zombie_piglin",
                "minecraft:piglin",
                "minecraft:piglin_brute",
                "minecraft:hoglin",
                "minecraft:zoglin",
                "minecraft:strider"
            ), obj -> obj instanceof String);
        
        BUILDER.pop();
        
        SPEC = BUILDER.build();
    }
}

