package com.example.examplemod.mana;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public final class ManaProvider {
    private ManaProvider() {}

    private static final String ROOT = "examplemod.mana";

    public static IMana get(Player player) {
        return new NbtBackedMana(player);
    }

    private static class NbtBackedMana implements IMana {
        private final Player player;

        NbtBackedMana(Player player) {
            this.player = player;
            // Initialize defaults if missing
            CompoundTag tag = ensureRoot();
            if (!tag.contains("Max")) tag.putInt("Max", 100);
            if (!tag.contains("Current")) tag.putInt("Current", tag.getInt("Max"));
            if (!tag.contains("Regen")) tag.putInt("Regen", 5);
        }

        private CompoundTag ensureRoot() {
            CompoundTag persistent = player.getPersistentData();
            if (!persistent.contains(ROOT)) persistent.put(ROOT, new CompoundTag());
            return persistent.getCompound(ROOT);
        }

        @Override
        public int getCurrentMana() {
            return ensureRoot().getInt("Current");
        }

        @Override
        public int getMaxMana() {
            return ensureRoot().getInt("Max");
        }

        @Override
        public int getRegenPerSecond() {
            return ensureRoot().getInt("Regen");
        }

        @Override
        public void setCurrentMana(int value) {
            ensureRoot().putInt("Current", Math.max(0, value));
        }

        @Override
        public void setMaxMana(int value) {
            ensureRoot().putInt("Max", Math.max(0, value));
        }

        @Override
        public void setRegenPerSecond(int value) {
            ensureRoot().putInt("Regen", Math.max(0, value));
        }
    }
}
