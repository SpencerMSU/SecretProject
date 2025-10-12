package com.example.examplemod.accessory;

import net.minecraft.ChatFormatting;

public enum AccessoryRarity {
    COMMON("common", ChatFormatting.WHITE, 1.0),
    UNCOMMON("uncommon", ChatFormatting.GREEN, 0.5),
    RARE("rare", ChatFormatting.BLUE, 0.25),
    EPIC("epic", ChatFormatting.DARK_PURPLE, 0.1),
    LEGENDARY("legendary", ChatFormatting.GOLD, 0.05),
    MYTHICAL("mythical", ChatFormatting.RED, 0.01),
    ABSOLUTE("absolute", ChatFormatting.DARK_RED, 0.0); // Не выпадает с мобов

    private final String name;
    private final ChatFormatting color;
    private final double dropChance;

    AccessoryRarity(String name, ChatFormatting color, double dropChance) {
        this.name = name;
        this.color = color;
        this.dropChance = dropChance;
    }

    public String getName() {
        return name;
    }

    public ChatFormatting getColor() {
        return color;
    }

    public double getDropChance() {
        return dropChance;
    }
}

