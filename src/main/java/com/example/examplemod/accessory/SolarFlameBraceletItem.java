package com.example.examplemod.accessory;

import net.minecraft.world.item.Item;

/**
 * Браслет Солнечного Пламени - божественный огненный браслет
 * Содержит частицу солнечной энергии и дарует священную силу огня
 */
public class SolarFlameBraceletItem extends BaseAccessoryItem {
    public SolarFlameBraceletItem(Properties properties) {
        super(AccessoryType.BRACELET, AccessoryElement.FIRE, properties);
    }
}
