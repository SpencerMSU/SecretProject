package com.example.examplemod.items;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.items.FireAccessories.*;
import com.example.examplemod.items.WaterAccessories.*;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ExampleMod.MODID);

    // Камень заточки (улучшение уровня)
    public static final DeferredItem<EnchantmentStoneItem> ENCHANTMENT_STONE = ITEMS.registerItem(
        "enchantment_stone",
        EnchantmentStoneItem::new,
        new Item.Properties()
    );
    
    // Камень улучшения редкости
    public static final DeferredItem<RarityUpgradeStoneItem> RARITY_UPGRADE_STONE = ITEMS.registerItem(
        "rarity_upgrade_stone",
        RarityUpgradeStoneItem::new,
        new Item.Properties()
    );

    // Огненные аксессуары
    public static final DeferredItem<FireCharmItem> FIRE_CHARM = ITEMS.registerItem(
        "fire_charm",
        FireCharmItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<FireMaskItem> FIRE_MASK = ITEMS.registerItem(
        "fire_mask",
        FireMaskItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<FireHatItem> FIRE_HAT = ITEMS.registerItem(
        "fire_hat",
        FireHatItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<FireNecklaceItem> FIRE_NECKLACE = ITEMS.registerItem(
        "fire_necklace",
        FireNecklaceItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<FireCapeItem> FIRE_CAPE = ITEMS.registerItem(
        "fire_cape",
        FireCapeItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<FireBackItem> FIRE_BACK = ITEMS.registerItem(
        "fire_back",
        FireBackItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<FireWristItem> FIRE_WRIST = ITEMS.registerItem(
        "fire_wrist",
        FireWristItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<FireHandItem> FIRE_HAND_1 = ITEMS.registerItem(
        "fire_hand_1",
        FireHandItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<FireHandItem> FIRE_HAND_2 = ITEMS.registerItem(
        "fire_hand_2",
        FireHandItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<FireRingItem> FIRE_RING_1 = ITEMS.registerItem(
        "fire_ring_1",
        FireRingItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<FireRingItem> FIRE_RING_2 = ITEMS.registerItem(
        "fire_ring_2",
        FireRingItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<FireBeltItem> FIRE_BELT = ITEMS.registerItem(
        "fire_belt",
        FireBeltItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<FireAnkletItem> FIRE_ANKLET = ITEMS.registerItem(
        "fire_anklet",
        FireAnkletItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<FireShoesItem> FIRE_SHOES = ITEMS.registerItem(
        "fire_shoes",
        FireShoesItem::new,
        new Item.Properties().stacksTo(1)
    );

    // Водные аксессуары
    public static final DeferredItem<WaterCharmItem> WATER_CHARM = ITEMS.registerItem(
        "water_charm",
        WaterCharmItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<WaterMaskItem> WATER_MASK = ITEMS.registerItem(
        "water_mask",
        WaterMaskItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<WaterHatItem> WATER_HAT = ITEMS.registerItem(
        "water_hat",
        WaterHatItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<WaterNecklaceItem> WATER_NECKLACE = ITEMS.registerItem(
        "water_necklace",
        WaterNecklaceItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<WaterCapeItem> WATER_CAPE = ITEMS.registerItem(
        "water_cape",
        WaterCapeItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<WaterBackItem> WATER_BACK = ITEMS.registerItem(
        "water_back",
        WaterBackItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<WaterWristItem> WATER_WRIST = ITEMS.registerItem(
        "water_wrist",
        WaterWristItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<WaterHandItem> WATER_HAND_1 = ITEMS.registerItem(
        "water_hand_1",
        WaterHandItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<WaterHandItem> WATER_HAND_2 = ITEMS.registerItem(
        "water_hand_2",
        WaterHandItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<WaterRingItem> WATER_RING_1 = ITEMS.registerItem(
        "water_ring_1",
        WaterRingItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<WaterRingItem> WATER_RING_2 = ITEMS.registerItem(
        "water_ring_2",
        WaterRingItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<WaterBeltItem> WATER_BELT = ITEMS.registerItem(
        "water_belt",
        WaterBeltItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<WaterAnkletItem> WATER_ANKLET = ITEMS.registerItem(
        "water_anklet",
        WaterAnkletItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<WaterShoesItem> WATER_SHOES = ITEMS.registerItem(
        "water_shoes",
        WaterShoesItem::new,
        new Item.Properties().stacksTo(1)
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}