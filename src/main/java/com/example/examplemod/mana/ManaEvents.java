package com.example.examplemod.mana;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.network.SyncManaDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * Handles mana-related events such as player cloning and regeneration.
 */
@EventBusSubscriber(modid = ExampleMod.MODID)
public class ManaEvents {

    private static int syncTimer = 0;

    /**
     * Handles mana regeneration every tick and syncs to client periodically
     */
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide()) {
            ManaData manaData = player.getData(ModAttachments.PLAYER_MANA);
            manaData.tick();
            
            // Sync to client every 20 ticks (1 second)
            syncTimer++;
            if (syncTimer >= 20 && player instanceof ServerPlayer serverPlayer) {
                syncTimer = 0;
                syncManaToClient(serverPlayer, manaData);
            }
        }
    }

    /**
     * Copy mana data when player respawns or returns from End
     */
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            ManaData oldMana = event.getOriginal().getData(ModAttachments.PLAYER_MANA);
            ManaData newMana = event.getEntity().getData(ModAttachments.PLAYER_MANA);
            newMana.copyFrom(oldMana);
        }
    }

    /**
     * Sync mana data when player joins the server
     */
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            ManaData manaData = serverPlayer.getData(ModAttachments.PLAYER_MANA);
            syncManaToClient(serverPlayer, manaData);
        }
    }

    private static void syncManaToClient(ServerPlayer player, ManaData manaData) {
        PacketDistributor.sendToPlayer(player, new SyncManaDataPacket(
            manaData.getCurrentMana(),
            manaData.getMaxMana(),
            manaData.getRegenRate(),
            manaData.getColor()
        ));
    }
}
