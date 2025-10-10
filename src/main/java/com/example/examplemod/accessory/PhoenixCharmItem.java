package com.example.examplemod.accessory;

import net.minecraft.world.item.Item;

/**
 * Амулет Феникса - мистический огненный амулет
 * Содержит душу легендарного феникса и дарует способность к возрождению
 */
public class PhoenixCharmItem extends BaseAccessoryItem {
    public PhoenixCharmItem(Properties properties) {
        super(AccessoryType.CHARM, AccessoryElement.FIRE, properties);
    }
}
