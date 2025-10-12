package com.example.examplemod.accessory;

import com.example.examplemod.ExampleMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModDataComponents {
    
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
        DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ExampleMod.MODID);
    
    @SuppressWarnings("unchecked")
    public static final Supplier<DataComponentType<AccessoryData>> ACCESSORY_DATA =
        DATA_COMPONENTS.register("accessory_data", () -> 
            DataComponentType.<AccessoryData>builder()
                .persistent(AccessoryData.CODEC)
                .networkSynchronized((StreamCodec<? super RegistryFriendlyByteBuf, AccessoryData>) AccessoryData.STREAM_CODEC)
                .build()
        );
    
    public static void register(IEventBus eventBus) {
        DATA_COMPONENTS.register(eventBus);
    }
}

