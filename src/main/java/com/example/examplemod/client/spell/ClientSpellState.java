package com.example.examplemod.client.spell;

import com.example.examplemod.spell.SpellClass;
import com.example.examplemod.spell.SpellEntry;
import com.example.examplemod.spell.SpellRarity;
import com.example.examplemod.spell.VisualEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class ClientSpellState {
    private static boolean initialized = false;

    private static final List<SpellEntry> FIRE_SPELLS = new ArrayList<>();
    private static final List<SpellEntry> WATER_SPELLS = new ArrayList<>();

    public static final int HOTBAR_SLOTS = 5;
    private static final SpellEntry[] HOTBAR = new SpellEntry[HOTBAR_SLOTS];

    private static int activeIndex = 0; // 0..4
    private static SpellClass selectedClass = SpellClass.FIRE;

    private ClientSpellState() {}

    public static void ensureInitialized() {
        if (initialized) return;
        initialized = true;

        seedClassSpells();
        // Do not prefill hotbar by default; start with empty slots
    }

    private static void seedClassSpells() {
        // 10 fire spells sorted by rarity with beautiful visual effects
        if (FIRE_SPELLS.isEmpty()) {
            // COMMON (2 spells)
            FIRE_SPELLS.add(new SpellEntry(
                "spark", 
                Component.literal("Spark"),
                new ItemStack(Items.FLINT_AND_STEEL),
                SpellRarity.COMMON,
                8,
                15,
                new VisualEffect("flame", 0xFFFF9933, 0xFFFFCC66, "single", "block.fire.ambient", 0.8f, 5),
                Component.literal("A tiny spark that ignites enemies")
            ));
            
            FIRE_SPELLS.add(new SpellEntry(
                "ember", 
                Component.literal("Ember"),
                new ItemStack(Items.TORCH),
                SpellRarity.COMMON,
                12,
                20,
                new VisualEffect("flame", 0xFFFF6600, 0xFFFF8833, "float", "block.campfire.crackle", 1.0f, 8),
                Component.literal("Floating embers that gently burn foes")
            ));
            
            // UNCOMMON (2 spells)
            FIRE_SPELLS.add(new SpellEntry(
                "fire_bolt", 
                Component.literal("Fire Bolt"),
                new ItemStack(Items.BLAZE_POWDER),
                SpellRarity.UNCOMMON,
                25,
                35,
                new VisualEffect("flame", 0xFFFF3300, 0xFFFFAA00, "projectile", "entity.blaze.shoot", 1.2f, 15),
                Component.literal("A bolt of concentrated fire")
            ));
            
            FIRE_SPELLS.add(new SpellEntry(
                "flame_burst", 
                Component.literal("Flame Burst"),
                new ItemStack(Items.FIRE_CHARGE),
                SpellRarity.UNCOMMON,
                30,
                40,
                new VisualEffect("flame", 0xFFFF4400, 0xFFFFDD00, "burst", "entity.generic.explode", 1.5f, 25),
                Component.literal("Explosive burst of flames in all directions")
            ));
            
            // RARE (2 spells)
            FIRE_SPELLS.add(new SpellEntry(
                "magma_spike", 
                Component.literal("Magma Spike"),
                new ItemStack(Items.MAGMA_BLOCK),
                SpellRarity.RARE,
                55,
                60,
                new VisualEffect("lava", 0xFFFF2200, 0xFFDD4400, "spike", "block.lava.pop", 2.0f, 35),
                Component.literal("Molten earth erupts as deadly spikes")
            ));
            
            FIRE_SPELLS.add(new SpellEntry(
                "blaze_rush", 
                Component.literal("Blaze Rush"),
                new ItemStack(Items.BLAZE_ROD),
                SpellRarity.RARE,
                60,
                70,
                new VisualEffect("flame", 0xFFFFAA00, 0xFFFFEE55, "vortex", "entity.blaze.ambient", 1.8f, 50),
                Component.literal("Channel the fury of a blaze in a spinning vortex")
            ));
            
            // EPIC (2 spells)
            FIRE_SPELLS.add(new SpellEntry(
                "inferno", 
                Component.literal("Inferno"),
                new ItemStack(Items.LAVA_BUCKET),
                SpellRarity.EPIC,
                95,
                100,
                new VisualEffect("lava+flame", 0xFFFF0000, 0xFFFFAA00, "pillar", "block.lava.extinguish", 2.5f, 80),
                Component.literal("Summon a towering pillar of pure inferno")
            ));
            
            FIRE_SPELLS.add(new SpellEntry(
                "hellfire", 
                Component.literal("Hellfire"),
                new ItemStack(Items.NETHERRACK),
                SpellRarity.EPIC,
                100,
                110,
                new VisualEffect("soul_flame", 0xFF3344FF, 0xFFFF4400, "rain", "entity.wither.shoot", 2.2f, 100),
                Component.literal("Unleash cursed flames from the nether itself")
            ));
            
            // LEGENDARY (1 spell)
            FIRE_SPELLS.add(new SpellEntry(
                "phoenix_rebirth", 
                Component.literal("Phoenix Rebirth"),
                new ItemStack(Items.BLAZE_POWDER),
                SpellRarity.LEGENDARY,
                150,
                150,
                new VisualEffect("flame", 0xFFFFDD00, 0xFFFF2200, "phoenix_wings", "entity.ender_dragon.flap", 3.0f, 150),
                Component.literal("Rise like a phoenix, wings of flame engulf all")
            ));
            
            // MYTHIC (1 spell)
            FIRE_SPELLS.add(new SpellEntry(
                "solar_eclipse", 
                Component.literal("Solar Eclipse"),
                new ItemStack(Items.NETHER_STAR),
                SpellRarity.MYTHIC,
                250,
                200,
                new VisualEffect("solar_flare", 0xFFFFFFFF, 0xFFFF0000, "supernova", "entity.wither.death", 4.0f, 300),
                Component.literal("Harness the power of a dying star - ultimate destruction")
            ));
        }
        if (WATER_SPELLS.isEmpty()) {
            WATER_SPELLS.add(new SpellEntry("water_1", Component.literal("Water Jet"), new ItemStack(Items.WATER_BUCKET)));
            WATER_SPELLS.add(new SpellEntry("water_2", Component.literal("Ice Shard"), new ItemStack(Items.ICE)));
            WATER_SPELLS.add(new SpellEntry("water_3", Component.literal("Tide"), new ItemStack(Items.BLUE_DYE)));
            WATER_SPELLS.add(new SpellEntry("water_4", Component.literal("Prism Beam"), new ItemStack(Items.PRISMARINE)));
            WATER_SPELLS.add(new SpellEntry("water_5", Component.literal("Trident Call"), new ItemStack(Items.TRIDENT)));
            WATER_SPELLS.add(new SpellEntry("water_6", Component.literal("Sea Heart"), new ItemStack(Items.HEART_OF_THE_SEA)));
            WATER_SPELLS.add(new SpellEntry("water_7", Component.literal("Turtle Shield"), new ItemStack(Items.TURTLE_HELMET)));
            WATER_SPELLS.add(new SpellEntry("water_8", Component.literal("Soak"), new ItemStack(Items.SPONGE)));
            WATER_SPELLS.add(new SpellEntry("water_9", Component.literal("Kelp Lash"), new ItemStack(Items.KELP)));
            WATER_SPELLS.add(new SpellEntry("water_10", Component.literal("Hook"), new ItemStack(Items.FISHING_ROD)));
        }
    }

    // Removed random seeding of hotbar; slots remain null until user assigns

    public static List<SpellEntry> getSpellsForSelectedClass() {
        ensureInitialized();
        return switch (selectedClass) {
            case FIRE -> FIRE_SPELLS;
            case WATER -> WATER_SPELLS;
        };
    }

    public static SpellClass getSelectedClass() {
        return selectedClass;
    }

    public static void setSelectedClass(SpellClass cls) {
        selectedClass = cls;
    }

    public static int getHotbarSize() {
        return HOTBAR_SLOTS;
    }

    public static SpellEntry getHotbarEntry(int index) {
        ensureInitialized();
        if (index < 0 || index >= HOTBAR_SLOTS) return null;
        return HOTBAR[index];
        
    }

    public static void setHotbarEntry(int index, SpellEntry entry) {
        ensureInitialized();
        if (index < 0 || index >= HOTBAR_SLOTS) return;
        HOTBAR[index] = entry;
    }

    public static int getActiveIndex() {
        return activeIndex;
    }

    public static void setActiveIndex(int index) {
        if (index < 0) index = 0;
        if (index >= HOTBAR_SLOTS) index = HOTBAR_SLOTS - 1;
        activeIndex = index;
    }

    public static void cycleActiveIndex(int delta) {
        int next = activeIndex + delta;
        while (next < 0) next += HOTBAR_SLOTS;
        while (next >= HOTBAR_SLOTS) next -= HOTBAR_SLOTS;
        activeIndex = next;
    }

    public static boolean isHoldingWand(Minecraft mc) {
        if (mc.player == null) return false;
        return mc.player.getMainHandItem().is(Items.STICK);
    }
}
