package com.example.examplemod.spell;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public final class SpellEntry {
    private final String id;
    private final Component displayName;
    private final ItemStack icon;

    public SpellEntry(String id, Component displayName, ItemStack icon) {
        this.id = id;
        this.displayName = displayName;
        this.icon = icon;
    }

    public String id() {
        return id;
    }

    public Component displayName() {
        return displayName;
    }

    public ItemStack icon() {
        return icon;
    }
}
