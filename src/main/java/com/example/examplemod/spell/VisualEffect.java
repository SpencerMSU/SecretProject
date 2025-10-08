package com.example.examplemod.spell;

public final class VisualEffect {
    private final String particleType;
    private final int primaryColor;
    private final int secondaryColor;
    private final String animation;
    private final String soundEffect;
    private final float particleSize;
    private final int particleCount;

    public VisualEffect(String particleType, int primaryColor, int secondaryColor, 
                       String animation, String soundEffect, float particleSize, int particleCount) {
        this.particleType = particleType;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.animation = animation;
        this.soundEffect = soundEffect;
        this.particleSize = particleSize;
        this.particleCount = particleCount;
    }

    public String getParticleType() {
        return particleType;
    }

    public int getPrimaryColor() {
        return primaryColor;
    }

    public int getSecondaryColor() {
        return secondaryColor;
    }

    public String getAnimation() {
        return animation;
    }

    public String getSoundEffect() {
        return soundEffect;
    }

    public float getParticleSize() {
        return particleSize;
    }

    public int getParticleCount() {
        return particleCount;
    }

    public String getDescription() {
        return String.format("%s with %s animation - %d %s particles", 
            particleType, animation, particleCount, getColorDescription());
    }

    private String getColorDescription() {
        if (primaryColor == secondaryColor) {
            return "single-colored";
        }
        return "gradient";
    }
}
