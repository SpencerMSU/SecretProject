package com.example.examplemod.accessory;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record AccessoryData(int level, AccessoryRarity rarity) {
    
    public static final int MAX_LEVEL = 10;
    public static final int MIN_LEVEL = 1;
    
    public static final Codec<AccessoryData> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.INT.fieldOf("level").forGetter(AccessoryData::level),
            Codec.STRING.xmap(
                name -> AccessoryRarity.valueOf(name.toUpperCase()),
                rarity -> rarity.getName()
            ).fieldOf("rarity").forGetter(AccessoryData::rarity)
        ).apply(instance, AccessoryData::new)
    );
    
    public static final StreamCodec<?, AccessoryData> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT,
        AccessoryData::level,
        ByteBufCodecs.STRING_UTF8.map(
            name -> AccessoryRarity.valueOf(name.toUpperCase()),
            AccessoryRarity::getName
        ),
        AccessoryData::rarity,
        AccessoryData::new
    );
    
    public AccessoryData {
        if (level < MIN_LEVEL) level = MIN_LEVEL;
        if (level > MAX_LEVEL) level = MAX_LEVEL;
    }
    
    public static AccessoryData createDefault() {
        return new AccessoryData(1, AccessoryRarity.COMMON);
    }
    
    public AccessoryData withLevel(int newLevel) {
        return new AccessoryData(newLevel, this.rarity);
    }
    
    public AccessoryData withRarity(AccessoryRarity newRarity) {
        return new AccessoryData(this.level, newRarity);
    }
    
    public double getStatMultiplier() {
        double rarityMult = switch (rarity) {
            case COMMON -> 1.0;
            case UNCOMMON -> 1.2;
            case RARE -> 1.5;
            case EPIC -> 2.0;
            case LEGENDARY -> 2.5;
            case MYTHICAL -> 3.5;
            case ABSOLUTE -> 5.0;
        };
        
        double levelMult = 1.0 + (level - 1) * 0.15;
        
        return rarityMult * levelMult;
    }
}

