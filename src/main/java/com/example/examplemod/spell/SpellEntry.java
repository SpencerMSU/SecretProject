package com.example.examplemod.spell;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public final class SpellEntry {
    private final String id;
    private final Component displayName;
    private final ItemStack icon;
    private final SpellRarity rarity;
    private final int damage;
    private final int manaCost;
    private final VisualEffect visualEffect;
    private final Component description;

    public SpellEntry(String id, Component displayName, ItemStack icon, 
                     SpellRarity rarity, int damage, int manaCost, 
                     VisualEffect visualEffect, Component description) {
        this.id = id;
        this.displayName = displayName;
        this.icon = icon;
        this.rarity = rarity;
        this.damage = damage;
        this.manaCost = manaCost;
        this.visualEffect = visualEffect;
        this.description = description;
    }

    // Legacy constructor for compatibility
    public SpellEntry(String id, Component displayName, ItemStack icon) {
        this(id, displayName, icon, SpellRarity.COMMON, 10, 20, 
             new VisualEffect("flame", 0xFFFF6600, 0xFFFF6600, "spiral", "entity.blaze.shoot", 1.0f, 10),
             Component.literal("A basic spell"));
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

    public SpellRarity rarity() {
        return rarity;
    }

    public int damage() {
        return damage;
    }

    public int manaCost() {
        return manaCost;
    }

    public VisualEffect visualEffect() {
        return visualEffect;
    }

    public Component description() {
        return description;
    }
}
