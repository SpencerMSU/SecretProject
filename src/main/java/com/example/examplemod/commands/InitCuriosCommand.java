package com.example.examplemod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import com.example.examplemod.curios.CuriosCapabilityProvider;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

/**
 * Команда для принудительной инициализации Curios слотов
 */
public class InitCuriosCommand {
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("initcurios")
                .requires(source -> source.hasPermission(2)) // Требует OP
                .executes(InitCuriosCommand::initCurios)
        );
    }
    
    private static int initCurios(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer player = ctx.getSource().getPlayer();
        if (player == null) {
            ctx.getSource().sendFailure(Component.literal("Only players can use this command"));
            return 0;
        }
        
        try {
            ctx.getSource().sendSuccess(
                () -> Component.literal("=== ПРИНУДИТЕЛЬНАЯ ИНИЦИАЛИЗАЦИЯ CURIOS ==="),
                false
            );
            
            // Пробуем получить handler
            ICuriosItemHandler curiosHandler = CuriosCapabilityProvider.getCuriosHandler(player);
            
            if (curiosHandler != null) {
                ctx.getSource().sendSuccess(
                    () -> Component.literal("✓ Handler найден! Слоты уже инициализированы"),
                    false
                );
                
                ctx.getSource().sendSuccess(
                    () -> Component.literal("Доступно слотов: " + curiosHandler.getSlots()),
                    false
                );
                
                // Проверяем конкретные слоты
                String[] testSlots = {"ring", "necklace", "bracelet", "belt", "charm", "back"};
                for (String slotId : testSlots) {
                    boolean hasSlot = curiosHandler.getStacksHandler(slotId).isPresent();
                    ctx.getSource().sendSuccess(
                        () -> Component.literal("Слот " + slotId + ": " + (hasSlot ? "✓ Найден" : "✗ Не найден")),
                        false
                    );
                }
                
                ctx.getSource().sendSuccess(
                    () -> Component.literal("Попробуйте нажать клавишу 'C' для открытия меню"),
                    true
                );
                
            } else {
                ctx.getSource().sendFailure(Component.literal("✗ Handler не найден"));
                ctx.getSource().sendFailure(Component.literal("Проблема: слоты загружены, но не привязаны к игроку"));
                ctx.getSource().sendFailure(Component.literal("Попробуйте перезайти в мир или перезапустить игру"));
            }
            
        } catch (Exception e) {
            ctx.getSource().sendFailure(Component.literal("Ошибка: " + e.getMessage()));
            e.printStackTrace();
        }
        
        return 1;
    }
}
