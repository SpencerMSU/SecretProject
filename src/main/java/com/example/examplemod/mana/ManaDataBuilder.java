package com.example.examplemod.mana;

/**
 * Builder pattern for constructing mana systems with custom configurations.
 * This allows easy creation of different types of mana systems without
 * dealing with complex constructors.
 */
public class ManaDataBuilder {
    private float maxMana = 100.0f;
    private float initialMana = -1; // -1 means use maxMana
    private float regenRate = 0.1f;
    private int color = 0xFF00A0FF; // Default blue color

    /**
     * Set the maximum mana capacity
     * @param maxMana maximum mana value
     * @return this builder for chaining
     */
    public ManaDataBuilder setMaxMana(float maxMana) {
        this.maxMana = maxMana;
        return this;
    }

    /**
     * Set the initial mana amount (defaults to max mana)
     * @param initialMana starting mana value
     * @return this builder for chaining
     */
    public ManaDataBuilder setInitialMana(float initialMana) {
        this.initialMana = initialMana;
        return this;
    }

    /**
     * Set the mana regeneration rate per tick
     * @param regenRate regeneration rate
     * @return this builder for chaining
     */
    public ManaDataBuilder setRegenRate(float regenRate) {
        this.regenRate = regenRate;
        return this;
    }

    /**
     * Set the color for the mana bar (ARGB format)
     * @param color ARGB color value
     * @return this builder for chaining
     */
    public ManaDataBuilder setColor(int color) {
        this.color = color;
        return this;
    }

    /**
     * Set the color using RGB values
     * @param r red (0-255)
     * @param g green (0-255)
     * @param b blue (0-255)
     * @return this builder for chaining
     */
    public ManaDataBuilder setColor(int r, int g, int b) {
        this.color = 0xFF000000 | (r << 16) | (g << 8) | b;
        return this;
    }

    /**
     * Build the mana data instance
     * @return new ManaData instance with configured properties
     */
    public ManaData build() {
        ManaData data = new ManaData(maxMana, regenRate, color);
        if (initialMana >= 0) {
            data.setCurrentMana(initialMana);
        }
        return data;
    }

    /**
     * Create a new builder instance
     * @return new ManaDataBuilder
     */
    public static ManaDataBuilder create() {
        return new ManaDataBuilder();
    }
}
