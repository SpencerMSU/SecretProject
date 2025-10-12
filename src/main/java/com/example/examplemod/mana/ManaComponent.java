package com.example.examplemod.mana;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ManaComponent {
    
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
        DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, "examplemod");
    
    public static final Supplier<DataComponentType<ManaData>> MANA_DATA =
        DATA_COMPONENTS.register("mana_data", () ->
            DataComponentType.<ManaData>builder()
                .persistent(ManaData.CODEC)
                .networkSynchronized((StreamCodec<? super RegistryFriendlyByteBuf, ManaData>) ManaData.STREAM_CODEC)
                .build()
        );
    
    public static void register(IEventBus eventBus) {
        DATA_COMPONENTS.register(eventBus);
    }
}
