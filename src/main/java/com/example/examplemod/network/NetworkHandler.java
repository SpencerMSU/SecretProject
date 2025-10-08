package com.example.examplemod.network;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.mana.ManaApi;
import net.minecraft.server.level.ServerPlayer;
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
        registrar.playToServer(SpellCastPayload.TYPE, SpellCastPayload.STREAM_CODEC, NetworkHandler::handleSpellCast);
    }

    private static void handleManaSync(final ManaSyncPayload payload, final IPayloadContext context) {
        if (context.flow().getReceptionSide() == LogicalSide.CLIENT) {
            context.enqueueWork(() -> ClientPayloadHandlers.handleManaSync(payload));
        }
    }

    private static void handleSpellCast(final SpellCastPayload payload, final IPayloadContext context) {
        if (context.flow().getReceptionSide() == LogicalSide.SERVER) {
            context.enqueueWork(() -> {
                if (context.player() instanceof ServerPlayer sp) {
                    // Try to consume mana
                    boolean consumed = ManaApi.tryConsume(sp, payload.manaCost());
                    if (consumed) {
                        // Spell cast successful - mana already consumed and synced by ManaApi
                        // In the future, you could trigger spell effects here
                    }
                }
            });
        }
    }

    public static void sendToPlayer(net.minecraft.server.level.ServerPlayer player, ManaSyncPayload payload) {
        PacketDistributor.sendToPlayer(player, payload);
    }

    public static void sendToServer(SpellCastPayload payload) {
        PacketDistributor.sendToServer(payload);
    }
}
