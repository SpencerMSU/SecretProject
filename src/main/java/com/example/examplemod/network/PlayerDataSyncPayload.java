package com.example.examplemod.network;

import com.example.examplemod.ExampleMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Пакет для синхронизации данных игрока между клиентом и сервером
 */
public record PlayerDataSyncPayload(
    int scrollOffset,
    List<String> hotbarSpells,
    String selectedClass
) implements CustomPacketPayload {
    
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "player_data_sync");
    public static final Type<PlayerDataSyncPayload> TYPE = new Type<>(ID);
    public static final StreamCodec<FriendlyByteBuf, PlayerDataSyncPayload> STREAM_CODEC = StreamCodec.of(
        (buf, payload) -> payload.write(buf),
        buf -> new PlayerDataSyncPayload(buf)
    );
    
    public PlayerDataSyncPayload(FriendlyByteBuf buf) {
        this(
            buf.readInt(),
            buf.readCollection(ArrayList::new, FriendlyByteBuf::readUtf),
            buf.readUtf()
        );
    }
    
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(scrollOffset);
        buf.writeCollection(hotbarSpells, FriendlyByteBuf::writeUtf);
        buf.writeUtf(selectedClass);
    }
    
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
