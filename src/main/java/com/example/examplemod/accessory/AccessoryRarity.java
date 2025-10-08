package com.example.examplemod.accessory;

/**
 * Редкость аксессуаров - влияет на начальные характеристики и максимальный потенциал
 * Назначается при выпадении с мобов
 */
public enum AccessoryRarity {
    COMMON(0xFFFFFFFF, "Common", 1.0, "Обычный"),
    UNCOMMON(0xFF55FF55, "Uncommon", 1.2, "Необычный"),
    RARE(0xFF5555FF, "Rare", 1.5, "Редкий"),
    EPIC(0xFFAA00AA, "Epic", 2.0, "Эпический"),
    LEGENDARY(0xFFFFAA00, "Legendary", 2.5, "Легендарный"),
    MYTHIC(0xFFFF5555, "Mythic", 3.0, "Мифический");

    private final int color;
    private final String englishName;
    private final double statsMultiplier; // Множитель статов
    private final String russianName;

    AccessoryRarity(int color, String englishName, double statsMultiplier, String russianName) {
        this.color = color;
        this.englishName = englishName;
        this.statsMultiplier = statsMultiplier;
        this.russianName = russianName;
    }

    public int getColor() {
        return color;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getRussianName() {
        return russianName;
    }

    /**
     * Множитель для базовых характеристик аксессуара
     * Common = 1.0x, Uncommon = 1.2x, Rare = 1.5x, Epic = 2.0x, Legendary = 2.5x, Mythic = 3.0x
     */
    public double getStatsMultiplier() {
        return statsMultiplier;
    }

    /**
     * Вес выпадения (для лута с мобов)
     */
    public int getDropWeight() {
        return switch (this) {
            case COMMON -> 50;      // 50% шанс
            case UNCOMMON -> 25;    // 25% шанс
            case RARE -> 15;        // 15% шанс
            case EPIC -> 7;         // 7% шанс
            case LEGENDARY -> 2;    // 2% шанс
            case MYTHIC -> 1;       // 1% шанс
        };
    }
}
