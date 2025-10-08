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
                        // Execute spell effect
                        com.example.examplemod.spell.SpellHandler.castSpell(sp, payload.spellId(), getDamageForSpell(payload.spellId()));
                    }
                }
            });
        }
    }
    
    private static int getDamageForSpell(String spellId) {
        // Map spell IDs to their damage values
        return switch (spellId) {
            case "spark" -> 8;
            case "ember" -> 12;
            case "fire_bolt" -> 25;
            case "flame_burst" -> 30;
            case "magma_spike" -> 55;
            case "blaze_rush" -> 60;
            case "inferno" -> 95;
            case "hellfire" -> 100;
            case "phoenix_rebirth" -> 150;
            case "solar_eclipse" -> 250;
            case "water_splash" -> 7;
            case "ice_shard" -> 10;
            case "aqua_jet" -> 22;
            case "frost_nova" -> 28;
            case "tidal_wave" -> 50;
            case "blizzard" -> 58;
            case "poseidon_wrath" -> 90;
            case "glacial_prison" -> 95;
            case "ocean_guardian" -> 140;
            case "absolute_zero" -> 240;
            default -> 10;
        };
    }

    public static void sendToPlayer(net.minecraft.server.level.ServerPlayer player, ManaSyncPayload payload) {
        PacketDistributor.sendToPlayer(player, payload);
    }

    public static void sendToServer(SpellCastPayload payload) {
        PacketDistributor.sendToServer(payload);
    }
}
