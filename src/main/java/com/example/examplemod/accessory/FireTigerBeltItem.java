package com.example.examplemod.accessory;

import net.minecraft.world.item.Item;

/**
 * Пояс Огненного Тигра - дикий огненный пояс
 * Воплощает ярость и скорость огненного тигра
 */
public class FireTigerBeltItem extends BaseAccessoryItem {
    public FireTigerBeltItem(Properties properties) {
        super(AccessoryType.BELT, AccessoryElement.FIRE, properties);
    }
}
