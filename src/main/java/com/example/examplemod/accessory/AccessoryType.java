package com.example.examplemod.accessory;

/**
 * Типы аксессуаров и их слоты в Curios
 */
public enum AccessoryType {
    RING("ring1", "Кольцо", "Ring"),
    NECKLACE("necklace", "Ожерелье", "Necklace"),
    BRACELET("bracelet", "Браслет", "Bracelet"),
    BELT("belt", "Пояс", "Belt"),
    CHARM("charm", "Амулет", "Charm"),
    CLOAK("back", "Плащ", "Cloak");

    private final String curiosSlot;
    private final String russianName;
    private final String englishName;

    AccessoryType(String curiosSlot, String russianName, String englishName) {
        this.curiosSlot = curiosSlot;
        this.russianName = russianName;
        this.englishName = englishName;
    }

    public String getCuriosSlot() {
        return curiosSlot;
    }

    public String getRussianName() {
        return russianName;
    }

    public String getEnglishName() {
        return englishName;
    }
}
