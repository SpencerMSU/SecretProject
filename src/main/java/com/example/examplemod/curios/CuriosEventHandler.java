package com.example.examplemod.curios;

import com.example.examplemod.ExampleMod;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

@EventBusSubscriber(modid = ExampleMod.MODID)
public class CuriosEventHandler {
    
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().level().isClientSide()) {
            var player = event.getEntity();
            System.out.println("=== CURIOS EVENT HANDLER ===");
            System.out.println("Player logged in: " + player.getName().getString());
            
            // Принудительно инициализируем Curios
            forceCuriosInitialization(player);
        }
    }
    
    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player player && !event.getEntity().level().isClientSide()) {
            System.out.println("=== CURIOS ENTITY JOIN ===");
            System.out.println("Player joined level: " + player.getName().getString());
            
            // Принудительно инициализируем Curios
            forceCuriosInitialization(player);
        }
    }
    
    private static void forceCuriosInitialization(Player player) {
        try {
            System.out.println("Forcing Curios initialization for player: " + player.getName().getString());
            
            // Пробуем получить handler
            ICuriosItemHandler handler = CuriosApi.getCuriosInventory(player).orElse(null);
            
            if (handler != null) {
                System.out.println("SUCCESS: Curios handler found!");
                System.out.println("Total slots: " + handler.getSlots());
                
                // Проверяем конкретные слоты
                String[] testSlots = {"ring", "necklace", "bracelet", "belt", "charm", "back"};
                for (String slotId : testSlots) {
                    boolean hasSlot = handler.getStacksHandler(slotId).isPresent();
                    System.out.println("Slot " + slotId + ": " + (hasSlot ? "✓ Found" : "✗ Not found"));
                }
            } else {
                System.err.println("FAILED: Curios handler is still NULL");
                System.err.println("This means Curios API is not properly initialized for this player");
                
                // Пробуем альтернативные способы
                System.out.println("Trying alternative initialization methods...");
                
                // Ждем и пробуем еще раз
                player.level().getServer().tell(new net.minecraft.server.TickTask(40, () -> {
                    ICuriosItemHandler retryHandler = CuriosApi.getCuriosInventory(player).orElse(null);
                    if (retryHandler != null) {
                        System.out.println("SUCCESS: Curios handler found on retry!");
                    } else {
                        System.err.println("FAILED: Curios handler still NULL after retry");
                        System.err.println("Curios API may not be properly loaded or configured");
                    }
                }));
            }
        } catch (Exception e) {
            System.err.println("Exception during Curios initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
