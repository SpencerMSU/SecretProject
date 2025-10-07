package com.example.examplemod.mana;

import net.minecraft.world.entity.player.Player;

/**
 * Helper class for accessing and manipulating player mana data.
 * Provides convenient methods for common mana operations.
 */
public class ManaHelper {
    
    /**
     * Get the mana data for a player
     * @param player the player
     * @return the mana data attachment
     */
    public static IManaData getMana(Player player) {
        return player.getData(ModAttachments.PLAYER_MANA);
    }
    
    /**
     * Check if a player has enough mana
     * @param player the player
     * @param amount the amount to check
     * @return true if the player has enough mana
     */
    public static boolean hasEnoughMana(Player player, float amount) {
        return getMana(player).getCurrentMana() >= amount;
    }
    
    /**
     * Consume mana from a player
     * @param player the player
     * @param amount the amount to consume
     * @return true if the mana was consumed successfully
     */
    public static boolean consumeMana(Player player, float amount) {
        IManaData mana = getMana(player);
        if (mana.getCurrentMana() >= amount) {
            mana.removeMana(amount);
            return true;
        }
        return false;
    }
    
    /**
     * Add mana to a player
     * @param player the player
     * @param amount the amount to add
     * @return the actual amount added
     */
    public static float addMana(Player player, float amount) {
        return getMana(player).addMana(amount);
    }
    
    /**
     * Get the current mana amount for a player
     * @param player the player
     * @return current mana
     */
    public static float getCurrentMana(Player player) {
        return getMana(player).getCurrentMana();
    }
    
    /**
     * Get the maximum mana amount for a player
     * @param player the player
     * @return maximum mana
     */
    public static float getMaxMana(Player player) {
        return getMana(player).getMaxMana();
    }
    
    /**
     * Set the current mana for a player
     * @param player the player
     * @param amount the amount to set
     */
    public static void setCurrentMana(Player player, float amount) {
        getMana(player).setCurrentMana(amount);
    }
    
    /**
     * Set the maximum mana for a player
     * @param player the player
     * @param amount the amount to set
     */
    public static void setMaxMana(Player player, float amount) {
        getMana(player).setMaxMana(amount);
    }
}
