package com.example.examplemod.network;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.mana.ModAttachments;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Network packet for syncing mana data from server to client.
 */
public record SyncManaDataPacket(float currentMana, float maxMana, float regenRate, int color) implements CustomPacketPayload {
    
    public static final Type<SyncManaDataPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "sync_mana"));

    public static final StreamCodec<ByteBuf, SyncManaDataPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.FLOAT,
        SyncManaDataPacket::currentMana,
        ByteBufCodecs.FLOAT,
        SyncManaDataPacket::maxMana,
        ByteBufCodecs.FLOAT,
        SyncManaDataPacket::regenRate,
        ByteBufCodecs.INT,
        SyncManaDataPacket::color,
        SyncManaDataPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SyncManaDataPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                var manaData = player.getData(ModAttachments.PLAYER_MANA);
                manaData.setCurrentMana(packet.currentMana());
                manaData.setMaxMana(packet.maxMana());
                manaData.setRegenRate(packet.regenRate());
                manaData.setColor(packet.color());
            }
        });
    }
}
