package com.example.examplemod.mana;

public interface IMana {
    int getCurrentMana();
    int getMaxMana();
    int getRegenPerSecond();

    void setCurrentMana(int value);
    void setMaxMana(int value);
    void setRegenPerSecond(int value);

    default void addMana(int amount) {
        setCurrentMana(Math.min(getCurrentMana() + amount, getMaxMana()));
    }

    default boolean consumeMana(int amount) {
        if (getCurrentMana() >= amount) {
            setCurrentMana(getCurrentMana() - amount);
            return true;
        }
        return false;
    }

    default void clamp() {
        if (getCurrentMana() > getMaxMana()) setCurrentMana(getMaxMana());
        if (getCurrentMana() < 0) setCurrentMana(0);
        if (getMaxMana() < 0) setMaxMana(0);
        if (getRegenPerSecond() < 0) setRegenPerSecond(0);
    }
}
