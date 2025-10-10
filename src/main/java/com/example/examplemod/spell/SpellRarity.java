package com.example.examplemod.spell;

public enum SpellRarity {
    COMMON(0xFFAAAAAA, "Common"),
    UNCOMMON(0xFF55FF55, "Uncommon"),
    RARE(0xFF5555FF, "Rare"),
    EPIC(0xFFAA00AA, "Epic"),
    LEGENDARY(0xFFFFAA00, "Legendary"),
    MYTHIC(0xFFFF5555, "Mythic"),
    NECROMANCER_FIRE(0xFF8B0000, "Necromancer Fire"); // Темно-красный цвет для некроманта огня

    private final int color;
    private final String displayName;

    SpellRarity(int color, String displayName) {
        this.color = color;
        this.displayName = displayName;
    }

    public int getColor() {
        return color;
    }

    public String getDisplayName() {
        return displayName;
    }
}
