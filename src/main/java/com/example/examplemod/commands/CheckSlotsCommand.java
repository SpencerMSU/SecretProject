package com.example.examplemod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.ISlotType;

import java.util.Collection;

/**
 * Команда для проверки загруженных слотов Curios
 */
public class CheckSlotsCommand {
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("checkslots")
                .requires(source -> source.hasPermission(2)) // Требует OP
                .executes(CheckSlotsCommand::checkSlots)
        );
    }
    
    private static int checkSlots(CommandContext<CommandSourceStack> ctx) {
        ctx.getSource().sendSuccess(
            () -> Component.literal("=== ПРОВЕРКА СЛОТОВ CURIOS ==="),
            false
        );
        
        try {
            var slotHelper = CuriosApi.getSlotHelper();
            if (slotHelper != null) {
                ctx.getSource().sendSuccess(
                    () -> Component.literal("✓ CuriosApi.getSlotHelper() доступен"),
                    false
                );
                
                Collection<ISlotType> allSlots = slotHelper.getSlotTypes();
                ctx.getSource().sendSuccess(
                    () -> Component.literal("Найдено типов слотов: " + allSlots.size()),
                    false
                );
                
                if (allSlots.isEmpty()) {
                    ctx.getSource().sendFailure(Component.literal("✗ Слоты не найдены!"));
                } else {
                    ctx.getSource().sendSuccess(
                        () -> Component.literal("Загруженные слоты:"),
                        false
                    );
                    
                    allSlots.forEach(slotType -> {
                        ctx.getSource().sendSuccess(
                            () -> Component.literal("  - " + slotType.getIdentifier() + " (размер: " + slotType.getSize() + ")"),
                            false
                        );
                    });
                    
                    // Проверяем наши слоты
                    String[] ourSlots = {"ring", "necklace", "bracelet", "belt", "charm", "back"};
                    ctx.getSource().sendSuccess(
                        () -> Component.literal("Проверяем наши слоты:"),
                        false
                    );
                    
                    for (String slotId : ourSlots) {
                        boolean found = allSlots.stream().anyMatch(slot -> slot.getIdentifier().toString().contains(slotId));
                        ctx.getSource().sendSuccess(
                            () -> Component.literal("  " + slotId + ": " + (found ? "✓ Найден" : "✗ НЕ НАЙДЕН")),
                            false
                        );
                    }
                }
            } else {
                ctx.getSource().sendFailure(Component.literal("✗ CuriosApi.getSlotHelper() недоступен"));
            }
            
        } catch (Exception e) {
            ctx.getSource().sendFailure(Component.literal("Ошибка при проверке слотов: " + e.getMessage()));
            e.printStackTrace();
        }
        
        return 1;
    }
}