package com.example.examplemod.mana;

import net.minecraft.nbt.CompoundTag;

public class Mana implements IMana {
    private int currentMana;
    private int maxMana;
    private int regenPerSecond;
    private boolean maxManaManuallySet = false;
    private boolean regenManuallySet = false;

    public Mana() {
        this.maxMana = 1000;
        this.currentMana = this.maxMana;
        this.regenPerSecond = 1;
    }

    @Override
    public int getCurrentMana() {
        return currentMana;
    }

    @Override
    public int getMaxMana() {
        return maxMana;
    }

    @Override
    public int getRegenPerSecond() {
        return regenPerSecond;
    }

    @Override
    public void setCurrentMana(int value) {
        this.currentMana = value;
    }

    @Override
    public void setMaxMana(int value) {
        this.maxMana = value;
    }

    @Override
    public void setRegenPerSecond(int value) {
        this.regenPerSecond = value;
    }
    
    @Override
    public boolean isMaxManaManuallySet() {
        return maxManaManuallySet;
    }
    
    @Override
    public void setMaxManaManuallySet(boolean value) {
        this.maxManaManuallySet = value;
    }
    
    @Override
    public boolean isRegenManuallySet() {
        return regenManuallySet;
    }
    
    @Override
    public void setRegenManuallySet(boolean value) {
        this.regenManuallySet = value;
    }

    public void saveNBTData(CompoundTag tag) {
        tag.putInt("Current", currentMana);
        tag.putInt("Max", maxMana);
        tag.putInt("Regen", regenPerSecond);
        tag.putBoolean("MaxManuallySet", maxManaManuallySet);
        tag.putBoolean("RegenManuallySet", regenManuallySet);
    }

    public void loadNBTData(CompoundTag tag) {
        if (tag.contains("Current")) this.currentMana = tag.getInt("Current");
        if (tag.contains("Max")) this.maxMana = tag.getInt("Max");
        if (tag.contains("Regen")) this.regenPerSecond = tag.getInt("Regen");
        if (tag.contains("MaxManuallySet")) this.maxManaManuallySet = tag.getBoolean("MaxManuallySet");
        if (tag.contains("RegenManuallySet")) this.regenManuallySet = tag.getBoolean("RegenManuallySet");
        clamp();
    }
}
