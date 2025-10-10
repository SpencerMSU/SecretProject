package com.example.examplemod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.ModList;

/**
 * Команда для проверки загруженных модов
 */
public class ModListCommand {
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("modlist")
                .requires(source -> source.hasPermission(2)) // Требует OP
                .executes(ModListCommand::listMods)
        );
    }
    
    private static int listMods(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer player = ctx.getSource().getPlayer();
        if (player == null) {
            ctx.getSource().sendFailure(Component.literal("Only players can use this command"));
            return 0;
        }
        
        ctx.getSource().sendSuccess(
            () -> Component.literal("Загруженные моды:"),
            true
        );
        
        ModList.get().forEachModContainer((modId, container) -> {
            ctx.getSource().sendSuccess(
                () -> Component.literal("- " + modId + " v" + container.getModInfo().getVersion()),
                false
            );
        });
        
        // Проверяем конкретно Curios
        boolean curiosLoaded = ModList.get().isLoaded("curios");
        ctx.getSource().sendSuccess(
            () -> Component.literal("Curios загружен: " + curiosLoaded),
            true
        );
        
        return 1;
    }
}
