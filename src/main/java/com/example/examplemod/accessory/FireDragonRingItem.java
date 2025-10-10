package com.example.examplemod.accessory;

import net.minecraft.world.item.Item;

/**
 * Кольцо Пламенного Дракона - легендарное огненное кольцо
 * Дарует огромную силу огня и защиту от пламени
 */
public class FireDragonRingItem extends BaseAccessoryItem {
    public FireDragonRingItem(Properties properties) {
        super(AccessoryType.RING, AccessoryElement.FIRE, properties);
    }
}
