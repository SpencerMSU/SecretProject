package com.example.examplemod.network;

import com.example.examplemod.ExampleMod;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ManaSyncPayload(int current, int max, int regen) implements CustomPacketPayload {
    public static final Type<ManaSyncPayload> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "mana_sync")
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, ManaSyncPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ManaSyncPayload::current,
            ByteBufCodecs.INT, ManaSyncPayload::max,
            ByteBufCodecs.INT, ManaSyncPayload::regen,
            ManaSyncPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
