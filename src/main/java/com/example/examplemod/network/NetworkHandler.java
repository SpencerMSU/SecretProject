package com.example.examplemod.network;

import com.example.examplemod.ExampleMod;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class NetworkHandler {
    private NetworkHandler() {}

    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(ExampleMod.MODID);
        registrar.playToClient(ManaSyncPayload.TYPE, ManaSyncPayload.STREAM_CODEC, NetworkHandler::handleManaSync);
    }

    private static void handleManaSync(final ManaSyncPayload payload, final IPayloadContext context) {
        if (context.flow().getReceptionSide() == LogicalSide.CLIENT) {
            context.enqueueWork(() -> ClientPayloadHandlers.handleManaSync(payload));
        }
    }

    public static void sendToPlayer(net.minecraft.server.level.ServerPlayer player, ManaSyncPayload payload) {
        PacketDistributor.sendToPlayer(player, payload);
    }
}
