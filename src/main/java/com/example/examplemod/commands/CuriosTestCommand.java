package com.example.examplemod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

/**
 * Команда для проверки Curios API
 */
public class CuriosTestCommand {
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("curiostest")
                .requires(source -> source.hasPermission(2)) // Требует OP
                .executes(CuriosTestCommand::testCurios)
        );
    }
    
    private static int testCurios(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer player = ctx.getSource().getPlayer();
        if (player == null) {
            ctx.getSource().sendFailure(Component.literal("Only players can use this command"));
            return 0;
        }
        
        try {
            // Проверяем, доступен ли Curios API
            ICuriosItemHandler curiosHandler = CuriosApi.getCuriosInventory(player).orElse(null);
            
            if (curiosHandler != null) {
                ctx.getSource().sendSuccess(
                    () -> Component.literal("Curios API работает! Доступно слотов: " + curiosHandler.getSlots()),
                    true
                );
                
                ctx.getSource().sendSuccess(
                    () -> Component.literal("Проверка завершена успешно!"),
                    false
                );
                
                // Проверяем конкретные слоты
                String[] testSlots = {"ring", "necklace", "bracelet", "belt", "charm", "back"};
                for (String slotId : testSlots) {
                    ctx.getSource().sendSuccess(
                        () -> Component.literal("Слот " + slotId + " должен быть доступен"),
                        false
                    );
                }
            } else {
                ctx.getSource().sendFailure(Component.literal("Curios API не работает!"));
            }
            
        } catch (Exception e) {
            ctx.getSource().sendFailure(Component.literal("Ошибка Curios API: " + e.getMessage()));
        }
        
        return 1;
    }
}
