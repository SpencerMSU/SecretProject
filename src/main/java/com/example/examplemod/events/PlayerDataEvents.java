package com.example.examplemod.events;

import com.example.examplemod.data.PlayerDataManager;
import com.example.examplemod.network.NetworkHandler;
import com.example.examplemod.network.PlayerDataSyncPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber
public class PlayerDataEvents {
    
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            // Загружаем данные игрока при входе
            PlayerDataManager.loadPlayerData(player);
            
            // Отправляем данные на клиент
            var data = PlayerDataManager.getPlayerData(player);
            NetworkHandler.sendToPlayer(player, new PlayerDataSyncPayload(
                data.getScrollOffset(),
                data.getHotbarSpells(),
                data.getSelectedClass()
            ));
        }
    }
    
    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            // Сохраняем данные игрока при выходе
            PlayerDataManager.savePlayerData(player);
        }
    }
    
    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        // Сохраняем данные каждые 5 минут (6000 тиков)
        if (event.getServer().getTickCount() % 6000 == 0) {
            for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
                PlayerDataManager.savePlayerData(player);
            }
        }
    }
}
