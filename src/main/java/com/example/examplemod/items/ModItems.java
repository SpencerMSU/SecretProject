package com.example.examplemod.items;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.accessory.*;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ExampleMod.MODID);

    // Камень заточки
    public static final DeferredItem<EnchantmentStoneItem> ENCHANTMENT_STONE = ITEMS.registerItem(
        "enchantment_stone",
        EnchantmentStoneItem::new,
        new Item.Properties()
    );

    // Огненные аксессуары
    public static final DeferredItem<FireRingItem> FIRE_RING = ITEMS.registerItem(
        "fire_ring",
        FireRingItem::new,
        new Item.Properties()
    );

    public static final DeferredItem<FireNecklaceItem> FIRE_NECKLACE = ITEMS.registerItem(
        "fire_necklace",
        FireNecklaceItem::new,
        new Item.Properties()
    );

    public static final DeferredItem<FireBraceletItem> FIRE_BRACELET = ITEMS.registerItem(
        "fire_bracelet",
        FireBraceletItem::new,
        new Item.Properties()
    );

    public static final DeferredItem<FireBeltItem> FIRE_BELT = ITEMS.registerItem(
        "fire_belt",
        FireBeltItem::new,
        new Item.Properties()
    );

    public static final DeferredItem<FireCharmItem> FIRE_CHARM = ITEMS.registerItem(
        "fire_charm",
        FireCharmItem::new,
        new Item.Properties()
    );

    public static final DeferredItem<FireCloakItem> FIRE_CLOAK = ITEMS.registerItem(
        "fire_cloak",
        FireCloakItem::new,
        new Item.Properties()
    );

    // Новые огненные аксессуары (легендарные)
    public static final DeferredItem<FireDragonRingItem> FIRE_DRAGON_RING = ITEMS.registerItem(
        "fire_dragon_ring",
        FireDragonRingItem::new,
        new Item.Properties()
    );

    public static final DeferredItem<VolcanicFuryNecklaceItem> VOLCANIC_FURY_NECKLACE = ITEMS.registerItem(
        "volcanic_fury_necklace",
        VolcanicFuryNecklaceItem::new,
        new Item.Properties()
    );

    public static final DeferredItem<SolarFlameBraceletItem> SOLAR_FLAME_BRACELET = ITEMS.registerItem(
        "solar_flame_bracelet",
        SolarFlameBraceletItem::new,
        new Item.Properties()
    );

    public static final DeferredItem<FireTigerBeltItem> FIRE_TIGER_BELT = ITEMS.registerItem(
        "fire_tiger_belt",
        FireTigerBeltItem::new,
        new Item.Properties()
    );

    public static final DeferredItem<PhoenixCharmItem> PHOENIX_CHARM = ITEMS.registerItem(
        "phoenix_charm",
        PhoenixCharmItem::new,
        new Item.Properties()
    );

    public static final DeferredItem<BlazingDemonCloakItem> BLAZING_DEMON_CLOAK = ITEMS.registerItem(
        "blazing_demon_cloak",
        BlazingDemonCloakItem::new,
        new Item.Properties()
    );

    // Водные аксессуары
    public static final DeferredItem<WaterRingItem> WATER_RING = ITEMS.registerItem(
        "water_ring",
        WaterRingItem::new,
        new Item.Properties()
    );

    public static final DeferredItem<WaterNecklaceItem> WATER_NECKLACE = ITEMS.registerItem(
        "water_necklace",
        WaterNecklaceItem::new,
        new Item.Properties()
    );

    public static final DeferredItem<WaterBraceletItem> WATER_BRACELET = ITEMS.registerItem(
        "water_bracelet",
        WaterBraceletItem::new,
        new Item.Properties()
    );

    public static final DeferredItem<WaterBeltItem> WATER_BELT = ITEMS.registerItem(
        "water_belt",
        WaterBeltItem::new,
        new Item.Properties()
    );

    public static final DeferredItem<WaterCharmItem> WATER_CHARM = ITEMS.registerItem(
        "water_charm",
        WaterCharmItem::new,
        new Item.Properties()
    );

    public static final DeferredItem<WaterCloakItem> WATER_CLOAK = ITEMS.registerItem(
        "water_cloak",
        WaterCloakItem::new,
        new Item.Properties()
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        TestCuriosItems.register(eventBus);
    }
}
