package com.example.examplemod.accessory;

/**
 * Класс хранящий характеристики аксессуара
 * Характеристики зависят от: типа аксессуара, стихии, редкости и уровня прокачки
 */
public class AccessoryStats {
    // Базовые характеристики (на 1 уровне)
    private final double baseHealth;          // Доп. здоровье
    private final double baseMana;            // Доп. мана
    private final double baseManaRegen;       // Реген маны
    private final double baseDamage;          // Доп. урон
    private final double baseDefense;         // Доп. защита
    private final double baseSpeed;           // Доп. скорость
    
    // Специфичные для стихии
    private final double elementalPower;      // Сила стихии (урон заклинаний стихии)
    private final double elementalResistance; // Сопротивление стихии

    public AccessoryStats(double baseHealth, double baseMana, double baseManaRegen,
                         double baseDamage, double baseDefense, double baseSpeed,
                         double elementalPower, double elementalResistance) {
        this.baseHealth = baseHealth;
        this.baseMana = baseMana;
        this.baseManaRegen = baseManaRegen;
        this.baseDamage = baseDamage;
        this.baseDefense = baseDefense;
        this.baseSpeed = baseSpeed;
        this.elementalPower = elementalPower;
        this.elementalResistance = elementalResistance;
    }

    /**
     * Вычисляет финальные характеристики с учетом уровня и редкости
     */
    public AccessoryStats calculate(int level, AccessoryRarity rarity) {
        double levelMultiplier = 1.0 + (level - 1) * 0.15; // +15% за уровень
        double rarityMultiplier = rarity.getStatsMultiplier();
        double totalMultiplier = levelMultiplier * rarityMultiplier;

        return new AccessoryStats(
            baseHealth * totalMultiplier,
            baseMana * totalMultiplier,
            baseManaRegen * totalMultiplier,
            baseDamage * totalMultiplier,
            baseDefense * totalMultiplier,
            baseSpeed * totalMultiplier,
            elementalPower * totalMultiplier,
            elementalResistance * totalMultiplier
        );
    }

    // Геттеры
    public double getHealth() { return baseHealth; }
    public double getMana() { return baseMana; }
    public double getManaRegen() { return baseManaRegen; }
    public double getDamage() { return baseDamage; }
    public double getDefense() { return baseDefense; }
    public double getSpeed() { return baseSpeed; }
    public double getElementalPower() { return elementalPower; }
    public double getElementalResistance() { return elementalResistance; }

    /**
     * Создает базовые статы для огненного аксессуара
     */
    public static AccessoryStats createFireStats(AccessoryType type) {
        return switch (type) {
            case RING -> new AccessoryStats(
                1.0,  // health
                5.0,  // mana
                0.5,  // mana regen
                0.5,  // damage
                0.5,  // defense
                0.0,  // speed
                1.0,  // elemental power
                0.5   // elemental resistance
            );
            case NECKLACE -> new AccessoryStats(2.0, 10.0, 1.0, 1.0, 1.0, 0.01, 2.0, 1.0);
            case BRACELET -> new AccessoryStats(1.5, 7.0, 0.7, 0.7, 0.7, 0.005, 1.5, 0.7);
            case BELT -> new AccessoryStats(3.0, 8.0, 0.8, 0.8, 2.0, 0.0, 1.2, 1.5);
            case CHARM -> new AccessoryStats(1.0, 15.0, 1.5, 1.5, 0.5, 0.0, 3.0, 0.5);
            case CLOAK -> new AccessoryStats(2.0, 5.0, 0.5, 0.5, 3.0, 0.02, 1.0, 2.0);
        };
    }

    /**
     * Создает базовые статы для водного аксессуара
     */
    public static AccessoryStats createWaterStats(AccessoryType type) {
        return switch (type) {
            case RING -> new AccessoryStats(
                1.5,  // health (вода дает больше хп)
                7.0,  // mana
                1.0,  // mana regen (вода дает больше регена)
                0.3,  // damage (меньше урона)
                1.0,  // defense (больше защиты)
                0.005,  // speed
                1.0,  // elemental power
                0.5   // elemental resistance
            );
            case NECKLACE -> new AccessoryStats(3.0, 12.0, 2.0, 0.5, 1.5, 0.01, 2.0, 1.0);
            case BRACELET -> new AccessoryStats(2.0, 9.0, 1.5, 0.4, 1.2, 0.01, 1.5, 0.7);
            case BELT -> new AccessoryStats(4.0, 10.0, 1.2, 0.5, 3.0, 0.0, 1.2, 2.0);
            case CHARM -> new AccessoryStats(2.0, 20.0, 3.0, 0.8, 1.0, 0.0, 3.0, 1.0);
            case CLOAK -> new AccessoryStats(3.0, 8.0, 1.0, 0.3, 4.0, 0.02, 1.0, 3.0);
        };
    }

    /**
     * Бонус полного набора (6/6 предметов одной стихии)
     */
    public static AccessoryStats getFullSetBonus(AccessoryElement element) {
        if (element == AccessoryElement.FIRE) {
            return new AccessoryStats(5.0, 20.0, 2.0, 3.0, 2.0, 0.05, 5.0, 2.0);
        } else { // WATER
            return new AccessoryStats(10.0, 30.0, 5.0, 1.0, 5.0, 0.05, 5.0, 5.0);
        }
    }

    /**
     * Бонус максимального набора (6/6 мифической редкости + 10 уровень)
     */
    public static AccessoryStats getMaxSetBonus(AccessoryElement element) {
        if (element == AccessoryElement.FIRE) {
            // Огонь: Огромный урон и сила огня
            return new AccessoryStats(10.0, 50.0, 5.0, 10.0, 5.0, 0.1, 15.0, 5.0);
        } else { // WATER
            // Вода: Огромная мана, реген и защита
            return new AccessoryStats(20.0, 100.0, 15.0, 3.0, 10.0, 0.1, 15.0, 10.0);
        }
    }
}
