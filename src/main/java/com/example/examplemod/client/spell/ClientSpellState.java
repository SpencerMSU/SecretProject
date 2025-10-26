package com.example.examplemod.client.spell;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.spell.SpellClass;
import com.example.examplemod.spell.SpellEntry;
import com.example.examplemod.spell.SpellRarity;
import com.example.examplemod.spell.VisualEffect;
import net.minecraft.resources.ResourceLocation;
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
    private static int scrollOffset = 0;

    private ClientSpellState() {}

    public static void ensureInitialized() {
        if (initialized) return;
        initialized = true;

        seedClassSpells();
        // Do not prefill hotbar by default; start with empty slots
    }
    
    /**
     * Загрузить данные игрока из сохранения
     */
    public static void loadPlayerData(int scrollOffset, List<String> hotbarSpells, String selectedClass) {
        ClientSpellState.scrollOffset = scrollOffset;
        ClientSpellState.selectedClass = SpellClass.valueOf(selectedClass);
        
        // Загружаем заклинания в хотбар
        for (int i = 0; i < Math.min(hotbarSpells.size(), HOTBAR_SLOTS); i++) {
            String spellId = hotbarSpells.get(i);
            if (!spellId.isEmpty()) {
                // Находим заклинание по ID
                SpellEntry entry = findSpellById(spellId);
                if (entry != null) {
                    HOTBAR[i] = entry;
                }
            }
        }
    }
    

    public static int getScrollOffset() {
        return scrollOffset;
    }
    
    public static void setScrollOffset(int scrollOffset) {
        ClientSpellState.scrollOffset = scrollOffset;
    }
    
    public static List<String> getHotbarSpellIds() {
        List<String> spellIds = new ArrayList<>();
        for (SpellEntry entry : HOTBAR) {
            if (entry != null) {
                spellIds.add(entry.id());
            } else {
                spellIds.add("");
            }
        }
        return spellIds;
    }
    
    public static String getSelectedClassString() {
        return selectedClass.name();
    }
    
    private static SpellEntry findSpellById(String spellId) {
        // Ищем в огненных заклинаниях
        for (SpellEntry entry : FIRE_SPELLS) {
            if (entry.id().equals(spellId)) {
                return entry;
            }
        }
        // Ищем в водных заклинаниях
        for (SpellEntry entry : WATER_SPELLS) {
            if (entry.id().equals(spellId)) {
                return entry;
            }
        }
        return null;
    }

    private static void seedClassSpells() {
        // 10 fire spells sorted by rarity with beautiful visual effects
        if (FIRE_SPELLS.isEmpty()) {
            // COMMON (2 spells)
            FIRE_SPELLS.add(new SpellEntry(
                "spark", 
                Component.translatable("spell.examplemod.spark"),
                new ItemStack(Items.FLINT_AND_STEEL),
                ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "textures/spell_icons/fire/spark.png"),
                SpellRarity.COMMON,
                8,
                15,
                new VisualEffect("flame", 0xFFFF9933, 0xFFFFCC66, "single", "block.fire.ambient", 0.8f, 5),
                Component.translatable("spell.examplemod.spark.desc")
            ));
            
            FIRE_SPELLS.add(new SpellEntry(
                "ember", 
                Component.translatable("spell.examplemod.ember"),
                new ItemStack(Items.TORCH),
                ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "textures/spell_icons/fire/ember.png"),
                SpellRarity.COMMON,
                12,
                20,
                new VisualEffect("flame", 0xFFFF6600, 0xFFFF8833, "float", "block.campfire.crackle", 1.0f, 8),
                Component.translatable("spell.examplemod.ember.desc")
            ));
            
            // UNCOMMON (2 spells)
            FIRE_SPELLS.add(new SpellEntry(
                "fire_bolt", 
                Component.translatable("spell.examplemod.fire_bolt"),
                new ItemStack(Items.BLAZE_POWDER),
                ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "textures/spell_icons/fire/fire_bolt.png"),
                SpellRarity.UNCOMMON,
                25,
                35,
                new VisualEffect("flame", 0xFFFF3300, 0xFFFFAA00, "projectile", "entity.blaze.shoot", 1.2f, 15),
                Component.translatable("spell.examplemod.fire_bolt.desc")
            ));
            
            FIRE_SPELLS.add(new SpellEntry(
                "flame_burst", 
                Component.translatable("spell.examplemod.flame_burst"),
                new ItemStack(Items.FIRE_CHARGE),
                ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "textures/spell_icons/fire/flame_burst.png"),
                SpellRarity.UNCOMMON,
                30,
                40,
                new VisualEffect("flame", 0xFFFF4400, 0xFFFFDD00, "burst", "entity.generic.explode", 1.5f, 25),
                Component.translatable("spell.examplemod.flame_burst.desc")
            ));
            
            // RARE (2 spells)
            FIRE_SPELLS.add(new SpellEntry(
                "magma_spike", 
                Component.translatable("spell.examplemod.magma_spike"),
                new ItemStack(Items.MAGMA_BLOCK),
                ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "textures/spell_icons/fire/magma_spike.png"),
                SpellRarity.RARE,
                55,
                60,
                new VisualEffect("lava", 0xFFFF2200, 0xFFDD4400, "spike", "block.lava.pop", 2.0f, 35),
                Component.translatable("spell.examplemod.magma_spike.desc")
            ));
            
            FIRE_SPELLS.add(new SpellEntry(
                "blaze_rush", 
                Component.translatable("spell.examplemod.blaze_rush"),
                new ItemStack(Items.BLAZE_ROD),
                ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "textures/spell_icons/fire/blaze_rush.png"),
                SpellRarity.RARE,
                60,
                70,
                new VisualEffect("flame", 0xFFFFAA00, 0xFFFFEE55, "vortex", "entity.blaze.ambient", 1.8f, 50),
                Component.translatable("spell.examplemod.blaze_rush.desc")
            ));
            
            // EPIC (2 spells)
            FIRE_SPELLS.add(new SpellEntry(
                "inferno", 
                Component.translatable("spell.examplemod.inferno"),
                new ItemStack(Items.LAVA_BUCKET),
                ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "textures/spell_icons/fire/inferno.png"),
                SpellRarity.EPIC,
                95,
                100,
                new VisualEffect("lava+flame", 0xFFFF0000, 0xFFFFAA00, "pillar", "block.lava.extinguish", 2.5f, 80),
                Component.translatable("spell.examplemod.inferno.desc")
            ));
            
            FIRE_SPELLS.add(new SpellEntry(
                "hellfire", 
                Component.translatable("spell.examplemod.hellfire"),
                new ItemStack(Items.NETHERRACK),
                ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "textures/spell_icons/fire/hellfire.png"),
                SpellRarity.EPIC,
                100,
                110,
                new VisualEffect("soul_flame", 0xFF3344FF, 0xFFFF4400, "rain", "entity.wither.shoot", 2.2f, 100),
                Component.translatable("spell.examplemod.hellfire.desc")
            ));
            
            // LEGENDARY (1 spell)
            FIRE_SPELLS.add(new SpellEntry(
                "phoenix_rebirth", 
                Component.translatable("spell.examplemod.phoenix_rebirth"),
                new ItemStack(Items.BLAZE_POWDER),
                ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "textures/spell_icons/fire/phoenix_rebirth.png"),
                SpellRarity.LEGENDARY,
                150,
                150,
                new VisualEffect("flame", 0xFFFFDD00, 0xFFFF2200, "phoenix_wings", "entity.ender_dragon.flap", 3.0f, 150),
                Component.translatable("spell.examplemod.phoenix_rebirth.desc")
            ));
            
            // MYTHIC (1 spell)
            FIRE_SPELLS.add(new SpellEntry(
                "solar_eclipse", 
                Component.translatable("spell.examplemod.solar_eclipse"),
                new ItemStack(Items.NETHER_STAR),
                ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "textures/spell_icons/fire/solar_eclipse.png"),
                SpellRarity.MYTHIC,
                250,
                200,
                new VisualEffect("solar_flare", 0xFFFFFFFF, 0xFFFF0000, "supernova", "entity.wither.death", 4.0f, 300),
                Component.translatable("spell.examplemod.solar_eclipse.desc")
            ));
            
            // NECROMANCER FIRE (2 spells) - Уникальные некромантские заклинания огня
            FIRE_SPELLS.add(new SpellEntry(
                "soul_fire_ritual", 
                Component.translatable("spell.examplemod.soul_fire_ritual"),
                new ItemStack(Items.WITHER_SKELETON_SKULL),
                ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "textures/spell_icons/fire/soul_fire_ritual.png"),
                SpellRarity.NECROMANCER_FIRE,
                180,
                120,
                new VisualEffect("soul_flames", 0xFF8B0000, 0xFF4B0000, "soul_ritual", "entity.wither.ambient", 2.8f, 200),
                Component.translatable("spell.examplemod.soul_fire_ritual.desc")
            ));
            
            FIRE_SPELLS.add(new SpellEntry(
                "phoenix_necromancy", 
                Component.translatable("spell.examplemod.phoenix_necromancy"),
                new ItemStack(Items.PHANTOM_MEMBRANE),
                ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "textures/spell_icons/fire/phoenix_necromancy.png"),
                SpellRarity.NECROMANCER_FIRE,
                220,
                150,
                new VisualEffect("undead_flames", 0xFF8B0000, 0xFF2F0000, "phoenix_rise", "entity.phantom.ambient", 3.2f, 250),
                Component.translatable("spell.examplemod.phoenix_necromancy.desc")
            ));
        }
        if (WATER_SPELLS.isEmpty()) {
            // COMMON (2 spells)
            WATER_SPELLS.add(new SpellEntry(
                "water_splash", 
                Component.translatable("spell.examplemod.water_splash"),
                new ItemStack(Items.WATER_BUCKET),
                SpellRarity.COMMON,
                7,
                15,
                new VisualEffect("water", 0xFF3399FF, 0xFF66CCFF, "splash", "entity.player.splash", 0.8f, 5),
                Component.translatable("spell.examplemod.water_splash.desc")
            ));
            
            WATER_SPELLS.add(new SpellEntry(
                "ice_shard", 
                Component.translatable("spell.examplemod.ice_shard"),
                new ItemStack(Items.ICE),
                SpellRarity.COMMON,
                10,
                18,
                new VisualEffect("snowflake", 0xFFCCEEFF, 0xFF99DDFF, "single", "block.glass.break", 0.9f, 8),
                Component.translatable("spell.examplemod.ice_shard.desc")
            ));
            
            // UNCOMMON (2 spells)
            WATER_SPELLS.add(new SpellEntry(
                "aqua_jet", 
                Component.translatable("spell.examplemod.aqua_jet"),
                new ItemStack(Items.BLUE_DYE),
                SpellRarity.UNCOMMON,
                22,
                32,
                new VisualEffect("water", 0xFF0066FF, 0xFF33AAFF, "stream", "entity.player.swim", 1.1f, 15),
                Component.translatable("spell.examplemod.aqua_jet.desc")
            ));
            
            WATER_SPELLS.add(new SpellEntry(
                "frost_nova", 
                Component.translatable("spell.examplemod.frost_nova"),
                new ItemStack(Items.PACKED_ICE),
                SpellRarity.UNCOMMON,
                28,
                38,
                new VisualEffect("snowflake", 0xFF99EEFF, 0xFFDDFFFF, "burst", "block.powder_snow.break", 1.4f, 25),
                Component.translatable("spell.examplemod.frost_nova.desc")
            ));
            
            // RARE (2 spells)
            WATER_SPELLS.add(new SpellEntry(
                "tidal_wave", 
                Component.translatable("spell.examplemod.tidal_wave"),
                new ItemStack(Items.PRISMARINE),
                SpellRarity.RARE,
                50,
                55,
                new VisualEffect("water", 0xFF0055DD, 0xFF3388FF, "wave", "entity.player.splash.high_speed", 1.9f, 35),
                Component.translatable("spell.examplemod.tidal_wave.desc")
            ));
            
            WATER_SPELLS.add(new SpellEntry(
                "blizzard", 
                Component.translatable("spell.examplemod.blizzard"),
                new ItemStack(Items.BLUE_ICE),
                SpellRarity.RARE,
                58,
                65,
                new VisualEffect("snowflake", 0xFFAAFFFF, 0xFFFFFFFF, "vortex", "entity.snow_golem.hurt", 1.7f, 50),
                Component.translatable("spell.examplemod.blizzard.desc")
            ));
            
            WATER_SPELLS.add(new SpellEntry(
                "crystal_wave", 
                Component.translatable("spell.examplemod.crystal_wave"),
                new ItemStack(Items.AMETHYST_SHARD),
                SpellRarity.RARE,
                65,
                70,
                new VisualEffect("crystal", 0xFF9966FF, 0xFFCC99FF, "crystal_wave", "block.amethyst_block.break", 2.0f, 60),
                Component.translatable("spell.examplemod.crystal_wave.desc")
            ));
            
            // EPIC (3 spells)
            WATER_SPELLS.add(new SpellEntry(
                "poseidon_wrath", 
                Component.translatable("spell.examplemod.poseidon_wrath"),
                new ItemStack(Items.TRIDENT),
                SpellRarity.EPIC,
                90,
                95,
                new VisualEffect("water+bubble", 0xFF0044BB, 0xFF00AAFF, "trident", "item.trident.thunder", 2.3f, 80),
                Component.translatable("spell.examplemod.poseidon_wrath.desc")
            ));
            
            WATER_SPELLS.add(new SpellEntry(
                "glacial_prison", 
                Component.translatable("spell.examplemod.glacial_prison"),
                new ItemStack(Items.BLUE_ICE),
                SpellRarity.EPIC,
                95,
                105,
                new VisualEffect("ice_crystal", 0xFF88DDFF, 0xFFCCFFFF, "cage", "block.glass.place", 2.1f, 100),
                Component.translatable("spell.examplemod.glacial_prison.desc")
            ));
            
            WATER_SPELLS.add(new SpellEntry(
                "deep_freeze", 
                Component.translatable("spell.examplemod.deep_freeze"),
                new ItemStack(Items.POWDER_SNOW_BUCKET),
                SpellRarity.EPIC,
                110,
                120,
                new VisualEffect("ice_storm", 0xFF66CCFF, 0xFFAAFFFF, "freeze_blast", "block.powder_snow.break", 2.5f, 120),
                Component.translatable("spell.examplemod.deep_freeze.desc")
            ));
            
            // LEGENDARY (1 spell)
            WATER_SPELLS.add(new SpellEntry(
                "ocean_guardian", 
                Component.translatable("spell.examplemod.ocean_guardian"),
                new ItemStack(Items.HEART_OF_THE_SEA),
                SpellRarity.LEGENDARY,
                140,
                145,
                new VisualEffect("water", 0xFF0088FF, 0xFF00FFFF, "guardian", "entity.elder_guardian.ambient", 2.8f, 150),
                Component.translatable("spell.examplemod.ocean_guardian.desc")
            ));
            
            // MYTHIC (1 spell)
            WATER_SPELLS.add(new SpellEntry(
                "absolute_zero", 
                Component.translatable("spell.examplemod.absolute_zero"),
                new ItemStack(Items.NETHER_STAR),
                SpellRarity.MYTHIC,
                240,
                200,
                new VisualEffect("ice_storm", 0xFFFFFFFF, 0xFF0099FF, "freeze_time", "entity.wither.death", 3.5f, 300),
                Component.translatable("spell.examplemod.absolute_zero.desc")
            ));
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
