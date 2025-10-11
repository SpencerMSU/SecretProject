package com.example.examplemod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import com.example.examplemod.items.TestCuriosItems;

/**
 * Команда для выдачи тестовых предметов Curios
 */
public class GiveTestCuriosCommand {
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("givetestcurios")
                .requires(source -> source.hasPermission(2)) // Требует OP
                .executes(ctx -> giveTestCurios(ctx, ctx.getSource().getPlayerOrException()))
                .then(Commands.argument("target", EntityArgument.player())
                    .executes(ctx -> giveTestCurios(ctx, EntityArgument.getPlayer(ctx, "target"))))
        );
    }
    
    private static int giveTestCurios(CommandContext<CommandSourceStack> ctx, ServerPlayer player) {
        try {
            // Выдаем по одному тестовому предмету каждого типа
            player.getInventory().add(TestCuriosItems.TEST_RING.get().getDefaultInstance());
            player.getInventory().add(TestCuriosItems.TEST_NECKLACE.get().getDefaultInstance());
            player.getInventory().add(TestCuriosItems.TEST_BRACELET.get().getDefaultInstance());
            player.getInventory().add(TestCuriosItems.TEST_BELT.get().getDefaultInstance());
            player.getInventory().add(TestCuriosItems.TEST_CHARM.get().getDefaultInstance());
            player.getInventory().add(TestCuriosItems.TEST_CLOAK.get().getDefaultInstance());
            
            ctx.getSource().sendSuccess(
                () -> Component.literal("✓ Выданы тестовые предметы Curios игроку " + player.getName().getString()),
                true
            );
            
            return 1;
        } catch (Exception e) {
            ctx.getSource().sendFailure(Component.literal("Ошибка при выдаче предметов: " + e.getMessage()));
            return 0;
        }
    }
}
