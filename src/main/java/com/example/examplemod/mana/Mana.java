package com.example.examplemod.mana;

import net.minecraft.nbt.CompoundTag;

public class Mana implements IMana {
    private int currentMana;
    private int maxMana;
    private int regenPerSecond;

    public Mana() {
        this.maxMana = 100;
        this.currentMana = this.maxMana;
        this.regenPerSecond = 5;
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

    public void saveNBTData(CompoundTag tag) {
        tag.putInt("Current", currentMana);
        tag.putInt("Max", maxMana);
        tag.putInt("Regen", regenPerSecond);
    }

    public void loadNBTData(CompoundTag tag) {
        if (tag.contains("Current")) this.currentMana = tag.getInt("Current");
        if (tag.contains("Max")) this.maxMana = tag.getInt("Max");
        if (tag.contains("Regen")) this.regenPerSecond = tag.getInt("Regen");
        clamp();
    }
}
