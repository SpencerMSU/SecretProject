package com.example.examplemod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.ModList;

/**
 * Команда для проверки установленных модов
 */
public class CheckModsCommand {
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("checkmods")
                .requires(source -> source.hasPermission(2)) // Требует OP
                .executes(CheckModsCommand::checkMods)
        );
    }
    
    private static int checkMods(CommandContext<CommandSourceStack> ctx) {
        ctx.getSource().sendSuccess(
            () -> Component.literal("=== ПРОВЕРКА УСТАНОВЛЕННЫХ МОДОВ ==="),
            false
        );
        
        // Проверяем основные моды
        String[] importantMods = {"curios", "neoforge", "minecraft", "examplemod"};
        
        for (String modId : importantMods) {
            boolean isLoaded = ModList.get().isLoaded(modId);
            ctx.getSource().sendSuccess(
                () -> Component.literal("Мод " + modId + ": " + (isLoaded ? "✓ Загружен" : "✗ НЕ ЗАГРУЖЕН")),
                false
            );
            
            if (isLoaded) {
                var mod = ModList.get().getModContainerById(modId);
                if (mod.isPresent()) {
                    ctx.getSource().sendSuccess(
                        () -> Component.literal("  Версия: " + mod.get().getModInfo().getVersion()),
                        false
                    );
                }
            }
        }
        
        // Показываем все загруженные моды
        ctx.getSource().sendSuccess(
            () -> Component.literal("Всего загружено модов: " + ModList.get().getMods().size()),
            false
        );
        
        return 1;
    }
}
