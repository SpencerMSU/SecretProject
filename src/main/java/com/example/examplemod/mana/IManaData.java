package com.example.examplemod.mana;

/**
 * Interface for mana data storage and manipulation.
 * This is the core interface for the mana system API.
 */
public interface IManaData {
    /**
     * Get the current mana amount
     * @return current mana value
     */
    float getCurrentMana();

    /**
     * Get the maximum mana capacity
     * @return maximum mana value
     */
    float getMaxMana();

    /**
     * Set the current mana amount
     * @param mana new mana value
     */
    void setCurrentMana(float mana);

    /**
     * Set the maximum mana capacity
     * @param maxMana new maximum mana value
     */
    void setMaxMana(float maxMana);

    /**
     * Add mana to the current amount
     * @param amount amount to add
     * @return actual amount added
     */
    float addMana(float amount);

    /**
     * Remove mana from the current amount
     * @param amount amount to remove
     * @return actual amount removed
     */
    float removeMana(float amount);

    /**
     * Get the mana regeneration rate per tick
     * @return regeneration rate
     */
    float getRegenRate();

    /**
     * Set the mana regeneration rate per tick
     * @param regenRate new regeneration rate
     */
    void setRegenRate(float regenRate);

    /**
     * Tick the mana system (for regeneration)
     */
    void tick();

    /**
     * Get the color for rendering the mana bar
     * @return ARGB color value
     */
    int getColor();

    /**
     * Set the color for rendering the mana bar
     * @param color ARGB color value
     */
    void setColor(int color);

    /**
     * Copy data from another IManaData
     * @param other source to copy from
     */
    void copyFrom(IManaData other);
}
