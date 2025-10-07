package com.example.examplemod.spell;

public enum SpellClass {
    FIRE,
    WATER;

    public String getDisplayName() {
        return switch (this) {
            case FIRE -> "Fire";
            case WATER -> "Water";
        };
    }
}
