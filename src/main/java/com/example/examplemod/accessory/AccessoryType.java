package com.example.examplemod.accessory;

/**
 * Типы аксессуаров и их слоты в Accessories
 */
public enum AccessoryType {
    RING("ring", "Кольцо", "Ring"),
    NECKLACE("necklace", "Ожерелье", "Necklace"),
    BRACELET("bracelet", "Браслет", "Bracelet"),
    BELT("belt", "Пояс", "Belt"),
    CHARM("charm", "Амулет", "Charm"),
    CLOAK("back", "Плащ", "Cloak");

    private final String slotName;
    private final String russianName;
    private final String englishName;

    AccessoryType(String slotName, String russianName, String englishName) {
        this.slotName = slotName;
        this.russianName = russianName;
        this.englishName = englishName;
    }

    public String getSlotName() {
        return slotName;
    }

    public String getRussianName() {
        return russianName;
    }

    public String getEnglishName() {
        return englishName;
    }
}
