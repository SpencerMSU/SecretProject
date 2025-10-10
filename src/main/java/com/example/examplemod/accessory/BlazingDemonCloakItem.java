package com.example.examplemod.accessory;

import net.minecraft.world.item.Item;

/**
 * Плащ Пылающего Демона - адский огненный плащ
 * Создан из пламени самого ада и дарует демоническую силу
 */
public class BlazingDemonCloakItem extends BaseAccessoryItem {
    public BlazingDemonCloakItem(Properties properties) {
        super(AccessoryType.CLOAK, AccessoryElement.FIRE, properties);
    }
}
