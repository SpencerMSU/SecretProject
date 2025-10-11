package com.example.examplemod.accessory;

/**
 * Типы аксессуаров и их слоты в Accessories
 */
public enum AccessoryType {
    RING("ring", "Кольцо", "Ring", new String[]{"ring", "earring"}),
    NECKLACE("necklace", "Ожерелье", "Necklace", new String[]{"necklace"}),
    BRACELET("bracelet", "Браслет", "Bracelet", new String[]{"bracelet", "anklet"}),
    BELT("belt", "Пояс", "Belt", new String[]{"belt"}),
    CHARM("charm", "Амулет", "Charm", new String[]{"charm"}),
    CLOAK("back", "Плащ", "Cloak", new String[]{"back", "cape"});

    private final String primarySlot;
    private final String russianName;
    private final String englishName;
    private final String[] compatibleSlots;

    AccessoryType(String primarySlot, String russianName, String englishName, String[] compatibleSlots) {
        this.primarySlot = primarySlot;
        this.russianName = russianName;
        this.englishName = englishName;
        this.compatibleSlots = compatibleSlots;
    }

    public String getSlotName() {
        return primarySlot;
    }

    public String[] getCompatibleSlots() {
        return compatibleSlots;
    }

    public boolean canEquipInSlot(String slotName) {
        for (String slot : compatibleSlots) {
            if (slot.equals(slotName)) {
                return true;
            }
        }
        return false;
    }

    public String getRussianName() {
        return russianName;
    }

    public String getEnglishName() {
        return englishName;
    }
}
