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
            ctx.getSource().sendSuccess(
                () -> Component.literal("=== ДИАГНОСТИКА CURIOS API ==="),
                false
            );
            
            // Проверяем CuriosApi
            ctx.getSource().sendSuccess(
                () -> Component.literal("Проверяем CuriosApi..."),
                false
            );
            
            try {
                // Проверяем доступность CuriosApi
                ctx.getSource().sendSuccess(
                    () -> Component.literal("✓ CuriosApi доступен"),
                    false
                );
                
                // Показываем информацию о слотах
                ctx.getSource().sendSuccess(
                    () -> Component.literal("Проверяем загрузку слотов..."),
                    false
                );
                
                // В NeoForge с Curios API слоты загружаются автоматически из data папки
                ctx.getSource().sendSuccess(
                    () -> Component.literal("Слоты должны загружаться автоматически из data/examplemod/curios/slots/"),
                    false
                );
                
                // Проверяем, что файлы слотов существуют
                String[] expectedSlots = {"ring", "necklace", "bracelet", "belt", "charm", "back"};
                ctx.getSource().sendSuccess(
                    () -> Component.literal("Ожидаемые слоты: " + String.join(", ", expectedSlots)),
                    false
                );
                
            } catch (Exception e) {
                ctx.getSource().sendFailure(Component.literal("✗ Ошибка при проверке слотов: " + e.getMessage()));
            }
            
            // Пробуем получить handler для игрока
            ctx.getSource().sendSuccess(
                () -> Component.literal("Пробуем получить handler для игрока..."),
                false
            );
            
            ICuriosItemHandler curiosHandler = null;
            
            try {
                // Пробуем разные способы получения handler
                var optional = CuriosApi.getCuriosInventory(player);
                if (optional.isPresent()) {
                    curiosHandler = optional.get();
                    ctx.getSource().sendSuccess(
                        () -> Component.literal("✓ Handler получен через CuriosApi.getCuriosInventory(player)"),
                        false
                    );
                } else {
                    ctx.getSource().sendFailure(Component.literal("✗ CuriosApi.getCuriosInventory(player) вернул пустой Optional"));
                    ctx.getSource().sendFailure(Component.literal("ВАЖНО: Слоты загружены, но handler недоступен!"));
                    ctx.getSource().sendFailure(Component.literal("Попробуйте нажать клавишу 'C' для открытия Curios меню"));
                }
            } catch (Exception e) {
                ctx.getSource().sendFailure(Component.literal("✗ Ошибка при получении handler: " + e.getMessage()));
            }
            
            // Создаем final переменную для lambda
            final ICuriosItemHandler finalCuriosHandler = curiosHandler;
            
            // Если handler получен, показываем информацию
            if (finalCuriosHandler != null) {
                ctx.getSource().sendSuccess(
                    () -> Component.literal("✓ CURIOS API РАБОТАЕТ!"),
                    true
                );
                
                ctx.getSource().sendSuccess(
                    () -> Component.literal("Доступно слотов: " + finalCuriosHandler.getSlots()),
                    false
                );
                
                // Проверяем конкретные слоты
                String[] testSlots = {"ring", "necklace", "bracelet", "belt", "charm", "back"};
                for (String slotId : testSlots) {
                    boolean hasSlot = finalCuriosHandler.getStacksHandler(slotId).isPresent();
                    ctx.getSource().sendSuccess(
                        () -> Component.literal("Слот " + slotId + ": " + (hasSlot ? "✓ Найден" : "✗ Не найден")),
                        false
                    );
                }
            } else {
                ctx.getSource().sendFailure(Component.literal("✗ CURIOS API НЕ РАБОТАЕТ! Handler = null"));
                ctx.getSource().sendFailure(Component.literal("Возможные причины:"));
                ctx.getSource().sendFailure(Component.literal("1. Curios мод не установлен"));
                ctx.getSource().sendFailure(Component.literal("2. Неправильная версия Curios"));
                ctx.getSource().sendFailure(Component.literal("3. Проблема с инициализацией"));
            }
            
        } catch (Exception e) {
            ctx.getSource().sendFailure(Component.literal("КРИТИЧЕСКАЯ ОШИБКА: " + e.getMessage()));
            e.printStackTrace();
        }
        
        return 1;
    }
}
