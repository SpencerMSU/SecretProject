package com.example.examplemod.mana;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record ManaData(int currentMana, int maxMana, float regenerationRate, float spellDiscount, float cooldownReduction) {
    
    public static final Codec<ManaData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.INT.fieldOf("currentMana").forGetter(ManaData::currentMana),
        Codec.INT.fieldOf("maxMana").forGetter(ManaData::maxMana),
        Codec.FLOAT.fieldOf("regenerationRate").forGetter(ManaData::regenerationRate),
        Codec.FLOAT.fieldOf("spellDiscount").forGetter(ManaData::spellDiscount),
        Codec.FLOAT.fieldOf("cooldownReduction").forGetter(ManaData::cooldownReduction)
    ).apply(instance, ManaData::new));
    
    public static final StreamCodec<RegistryFriendlyByteBuf, ManaData> STREAM_CODEC = StreamCodec.of(
        (buf, data) -> {
            buf.writeInt(data.currentMana());
            buf.writeInt(data.maxMana());
            buf.writeFloat(data.regenerationRate());
            buf.writeFloat(data.spellDiscount());
            buf.writeFloat(data.cooldownReduction());
        },
        buf -> new ManaData(
            buf.readInt(),
            buf.readInt(),
            buf.readFloat(),
            buf.readFloat(),
            buf.readFloat()
        )
    );
    
    public static ManaData createDefault() {
        return new ManaData(100, 100, 1.0f, 0.0f, 0.0f);
    }
    
    public ManaData withCurrentMana(int newMana) {
        return new ManaData(Math.min(newMana, maxMana), maxMana, regenerationRate, spellDiscount, cooldownReduction);
    }
    
    public ManaData withMaxMana(int newMaxMana) {
        return new ManaData(Math.min(currentMana, newMaxMana), newMaxMana, regenerationRate, spellDiscount, cooldownReduction);
    }
    
    public ManaData withRegenerationRate(float newRate) {
        return new ManaData(currentMana, maxMana, newRate, spellDiscount, cooldownReduction);
    }
    
    public ManaData withSpellDiscount(float newDiscount) {
        return new ManaData(currentMana, maxMana, regenerationRate, newDiscount, cooldownReduction);
    }
    
    public ManaData withCooldownReduction(float newReduction) {
        return new ManaData(currentMana, maxMana, regenerationRate, spellDiscount, newReduction);
    }
    
    public boolean canCast(int manaCost) {
        return currentMana >= manaCost;
    }
    
    public ManaData consumeMana(int amount) {
        return withCurrentMana(currentMana - amount);
    }
}
