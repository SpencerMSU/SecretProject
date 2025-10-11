package com.example.examplemod.curios;

import com.example.examplemod.ExampleMod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

@EventBusSubscriber(modid = ExampleMod.MODID)
public class CuriosSlots {
    
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        // Принудительно инициализируем Curios при входе игрока
        if (!event.getEntity().level().isClientSide()) {
            var player = event.getEntity();
            System.out.println("Player logged in: " + player.getName().getString());
            
            // Ждем несколько тиков, чтобы Curios успел инициализироваться
            event.getEntity().level().getServer().tell(new net.minecraft.server.TickTask(20, () -> {
                ICuriosItemHandler curiosHandler = CuriosCapabilityProvider.getCuriosHandler(player);
                if (curiosHandler != null) {
                    System.out.println("=== CURIOS SLOTS INITIALIZED ===");
                    System.out.println("Player: " + player.getName().getString());
                    System.out.println("Total slots: " + curiosHandler.getSlots());
                    System.out.println("Curios inventory successfully attached!");
                    
                    // Проверяем конкретные слоты
                    String[] testSlots = {"ring", "necklace", "bracelet", "belt", "charm", "back"};
                    for (String slotId : testSlots) {
                        boolean hasSlot = curiosHandler.getStacksHandler(slotId).isPresent();
                        System.out.println("Slot " + slotId + ": " + (hasSlot ? "✓ Found" : "✗ Not found"));
                    }
                } else {
                    System.err.println("ERROR: Curios handler is NULL for player " + player.getName().getString());
                    System.err.println("This means slots are loaded but not attached to player!");
                    
                    // Пробуем принудительно инициализировать
                    System.out.println("Attempting to force initialize Curios for player...");
                    CuriosCapabilityProvider.forceInitializeCurios(player);
                    
                    try {
                        // Пробуем получить handler еще раз через некоторое время
                        event.getEntity().level().getServer().tell(new net.minecraft.server.TickTask(40, () -> {
                            ICuriosItemHandler retryHandler = CuriosCapabilityProvider.getCuriosHandler(player);
                            if (retryHandler != null) {
                                System.out.println("SUCCESS: Curios handler found on retry!");
                            } else {
                                System.err.println("FAILED: Curios handler still NULL after retry");
                            }
                        }));
                    } catch (Exception e) {
                        System.err.println("Exception during retry: " + e.getMessage());
                    }
                }
            }));
        }
    }
    
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        // Дополнительная проверка при входе в мир
        if (event.getEntity() instanceof net.minecraft.world.entity.player.Player player && !event.getEntity().level().isClientSide()) {
            // Ждем несколько тиков, чтобы Curios успел инициализироваться
            event.getEntity().level().getServer().tell(new net.minecraft.server.TickTask(60, () -> {
                ICuriosItemHandler curiosHandler = CuriosCapabilityProvider.getCuriosHandler(player);
                if (curiosHandler != null) {
                    System.out.println("=== CURIOS SLOTS VERIFIED ===");
                    System.out.println("Player: " + player.getName().getString());
                    System.out.println("Total slots: " + curiosHandler.getSlots());
                    System.out.println("Curios inventory verified!");
                } else {
                    System.err.println("WARNING: Curios handler still NULL for player " + player.getName().getString());
                }
            }));
        }
    }
}
