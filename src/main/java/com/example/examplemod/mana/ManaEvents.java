package com.example.examplemod.mana;

import com.example.examplemod.network.ManaSyncPayload;
import com.example.examplemod.network.NetworkHandler;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber
public class ManaEvents {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer sp) {
            var mana = ManaProvider.get(sp);
            NetworkHandler.sendToPlayer(sp, new ManaSyncPayload(mana.getCurrentMana(), mana.getMaxMana(), mana.getRegenPerSecond()));
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer sp) {
            var mana = ManaProvider.get(sp);
            NetworkHandler.sendToPlayer(sp, new ManaSyncPayload(mana.getCurrentMana(), mana.getMaxMana(), mana.getRegenPerSecond()));
        }
    }

    private static int tickAccumulator;

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        tickAccumulator++;
        if (tickAccumulator < 20) return; // ~1 second
        tickAccumulator = 0;

        for (ServerPlayer sp : event.getServer().getPlayerList().getPlayers()) {
            var mana = ManaProvider.get(sp);
            int before = mana.getCurrentMana();
            mana.addMana(mana.getRegenPerSecond());
            int after = mana.getCurrentMana();
            if (after != before) {
                NetworkHandler.sendToPlayer(sp, new ManaSyncPayload(mana.getCurrentMana(), mana.getMaxMana(), mana.getRegenPerSecond()));
            }
        }
    }
}
