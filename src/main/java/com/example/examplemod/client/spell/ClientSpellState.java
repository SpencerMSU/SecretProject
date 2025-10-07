package com.example.examplemod.client.spell;

import com.example.examplemod.spell.SpellClass;
import com.example.examplemod.spell.SpellEntry;
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

    private static final SpellEntry[] HOTBAR = new SpellEntry[HOTBAR_SLOTS];
    public static final int HOTBAR_SLOTS = 5;

    private static int activeIndex = 0; // 0..4
    private static SpellClass selectedClass = SpellClass.FIRE;

    private ClientSpellState() {}

    public static void ensureInitialized() {
        if (initialized) return;
        initialized = true;

        seedClassSpells();
        seedHotbarWithRandomEntries();
    }

    private static void seedClassSpells() {
        // 10 placeholder spells per class with representative icons
        if (FIRE_SPELLS.isEmpty()) {
            FIRE_SPELLS.add(new SpellEntry("fire_1", Component.literal("Fire Bolt"), new ItemStack(Items.BLAZE_POWDER)));
            FIRE_SPELLS.add(new SpellEntry("fire_2", Component.literal("Flame Burst"), new ItemStack(Items.FIRE_CHARGE)));
            FIRE_SPELLS.add(new SpellEntry("fire_3", Component.literal("Ember"), new ItemStack(Items.TORCH)));
            FIRE_SPELLS.add(new SpellEntry("fire_4", Component.literal("Magma Spike"), new ItemStack(Items.MAGMA_BLOCK)));
            FIRE_SPELLS.add(new SpellEntry("fire_5", Component.literal("Inferno"), new ItemStack(Items.LAVA_BUCKET)));
            FIRE_SPELLS.add(new SpellEntry("fire_6", Component.literal("Blaze Rush"), new ItemStack(Items.BLAZE_ROD)));
            FIRE_SPELLS.add(new SpellEntry("fire_7", Component.literal("Hellfire"), new ItemStack(Items.NETHERRACK)));
            FIRE_SPELLS.add(new SpellEntry("fire_8", Component.literal("Campfire Aura"), new ItemStack(Items.CAMPFIRE)));
            FIRE_SPELLS.add(new SpellEntry("fire_9", Component.literal("Spark"), new ItemStack(Items.FLINT_AND_STEEL)));
            FIRE_SPELLS.add(new SpellEntry("fire_10", Component.literal("Crimson Light"), new ItemStack(Items.REDSTONE_TORCH)));
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

    private static void seedHotbarWithRandomEntries() {
        List<SpellEntry> pool = new ArrayList<>();
        pool.addAll(FIRE_SPELLS);
        pool.addAll(WATER_SPELLS);
        Collections.shuffle(pool, new Random());
        for (int i = 0; i < HOTBAR_SLOTS; i++) {
            HOTBAR[i] = pool.get(i % pool.size());
        }
    }

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
