package com.example.examplemod.items;

import com.example.examplemod.ExampleMod;
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
    public static final DeferredItem<Item> FIRE_CHARM = ITEMS.registerSimpleItem(
        "fire_charm",
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<Item> FIRE_MASK = ITEMS.registerSimpleItem(
        "fire_mask",
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<Item> FIRE_HAT = ITEMS.registerSimpleItem(
        "fire_hat",
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<Item> FIRE_NECKLACE = ITEMS.registerSimpleItem(
        "fire_necklace",
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<Item> FIRE_CAPE = ITEMS.registerSimpleItem(
        "fire_cape",
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<Item> FIRE_BACK = ITEMS.registerSimpleItem(
        "fire_back",
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<Item> FIRE_WRIST = ITEMS.registerSimpleItem(
        "fire_wrist",
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<Item> FIRE_HAND_1 = ITEMS.registerSimpleItem(
        "fire_hand_1",
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<Item> FIRE_HAND_2 = ITEMS.registerSimpleItem(
        "fire_hand_2",
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<Item> FIRE_RING_1 = ITEMS.registerSimpleItem(
        "fire_ring_1",
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<Item> FIRE_RING_2 = ITEMS.registerSimpleItem(
        "fire_ring_2",
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<Item> FIRE_BELT = ITEMS.registerSimpleItem(
        "fire_belt",
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<Item> FIRE_ANKLET = ITEMS.registerSimpleItem(
        "fire_anklet",
        new Item.Properties().stacksTo(1)
    );

    public static final DeferredItem<Item> FIRE_SHOES = ITEMS.registerSimpleItem(
        "fire_shoes",
        new Item.Properties().stacksTo(1)
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}