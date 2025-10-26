package com.example.examplemod.spell;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public final class SpellEntry {
    private final String id;
    private final Component displayName;
    private final ItemStack icon;
    private final ResourceLocation iconTexture; // новая текстура-иконка
    private final SpellRarity rarity;
    private final int damage;
    private final int manaCost;
    private final VisualEffect visualEffect;
    private final Component description;

    public SpellEntry(String id, Component displayName, ItemStack icon,
                      ResourceLocation iconTexture,
                      SpellRarity rarity, int damage, int manaCost,
                      VisualEffect visualEffect, Component description) {
        this.id = id;
        this.displayName = displayName;
        this.icon = icon;
        this.iconTexture = iconTexture;
        this.rarity = rarity;
        this.damage = damage;
        this.manaCost = manaCost;
        this.visualEffect = visualEffect;
        this.description = description;
    }

    // Прежний конструктор с ItemStack (без текстуры)
    public SpellEntry(String id, Component displayName, ItemStack icon,
                      SpellRarity rarity, int damage, int manaCost,
                      VisualEffect visualEffect, Component description) {
        this(id, displayName, icon, null, rarity, damage, manaCost, visualEffect, description);
    }

    // Legacy constructor for compatibility
    public SpellEntry(String id, Component displayName, ItemStack icon) {
        this(id, displayName, icon, null, SpellRarity.COMMON, 10, 20,
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

    public ResourceLocation iconTexture() {
        return iconTexture;
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