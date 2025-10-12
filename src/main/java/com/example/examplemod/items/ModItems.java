package com.example.examplemod.items;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.items.FireAccessories.*;
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

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}