package com.example.examplemod.mana;

import net.minecraft.nbt.CompoundTag;

/**
 * Default implementation of IManaData with NBT serialization support.
 */
public class ManaData implements IManaData {
    private float currentMana;
    private float maxMana;
    private float regenRate;
    private int color;

    public ManaData(float maxMana, float regenRate, int color) {
        this.maxMana = maxMana;
        this.currentMana = maxMana;
        this.regenRate = regenRate;
        this.color = color;
    }

    @Override
    public float getCurrentMana() {
        return currentMana;
    }

    @Override
    public float getMaxMana() {
        return maxMana;
    }

    @Override
    public void setCurrentMana(float mana) {
        this.currentMana = Math.max(0, Math.min(mana, maxMana));
    }

    @Override
    public void setMaxMana(float maxMana) {
        this.maxMana = Math.max(0, maxMana);
        this.currentMana = Math.min(this.currentMana, this.maxMana);
    }

    @Override
    public float addMana(float amount) {
        float oldMana = currentMana;
        setCurrentMana(currentMana + amount);
        return currentMana - oldMana;
    }

    @Override
    public float removeMana(float amount) {
        float oldMana = currentMana;
        setCurrentMana(currentMana - amount);
        return oldMana - currentMana;
    }

    @Override
    public float getRegenRate() {
        return regenRate;
    }

    @Override
    public void setRegenRate(float regenRate) {
        this.regenRate = regenRate;
    }

    @Override
    public void tick() {
        if (regenRate > 0 && currentMana < maxMana) {
            addMana(regenRate);
        }
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public void copyFrom(IManaData other) {
        this.currentMana = other.getCurrentMana();
        this.maxMana = other.getMaxMana();
        this.regenRate = other.getRegenRate();
        this.color = other.getColor();
    }

    public void saveNBTData(CompoundTag tag) {
        tag.putFloat("currentMana", currentMana);
        tag.putFloat("maxMana", maxMana);
        tag.putFloat("regenRate", regenRate);
        tag.putInt("color", color);
    }

    public void loadNBTData(CompoundTag tag) {
        this.currentMana = tag.getFloat("currentMana");
        this.maxMana = tag.getFloat("maxMana");
        this.regenRate = tag.getFloat("regenRate");
        this.color = tag.getInt("color");
    }
}
