package com.example.examplemod.accessory;

/**
 * Стихия аксессуара - определяет тип баффов
 */
public enum AccessoryElement {
    FIRE("fire", 0xFFFF6600, "Огонь", "Fire"),
    WATER("water", 0xFF0099FF, "Вода", "Water");

    private final String id;
    private final int color;
    private final String russianName;
    private final String englishName;

    AccessoryElement(String id, int color, String russianName, String englishName) {
        this.id = id;
        this.color = color;
        this.russianName = russianName;
        this.englishName = englishName;
    }

    public String getId() {
        return id;
    }

    public int getColor() {
        return color;
    }

    public String getRussianName() {
        return russianName;
    }

    public String getEnglishName() {
        return englishName;
    }
}
