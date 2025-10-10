package com.example.examplemod.commands;

import com.example.examplemod.mana.ManaApi;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

/**
 * Команда для управления маной
 * /msu mana set <amount> - установить текущую ману
 * /msu mana max <amount> - установить максимальную ману
 * /msu mana regen <amount> - установить регенерацию маны
 */
public class ManaCommand {
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("msu")
                .requires(source -> source.hasPermission(2)) // Требует OP
                .then(Commands.literal("mana")
                    .then(Commands.literal("set")
                        .then(Commands.argument("amount", IntegerArgumentType.integer(0, 10000))
                            .executes(ManaCommand::setMana)))
                    .then(Commands.literal("max")
                        .then(Commands.argument("amount", IntegerArgumentType.integer(1, 10000))
                            .executes(ManaCommand::setMaxMana)))
                    .then(Commands.literal("regen")
                        .then(Commands.argument("amount", IntegerArgumentType.integer(0, 100))
                            .executes(ManaCommand::setManaRegen)))
                    .then(Commands.literal("reset")
                        .then(Commands.argument("amount", IntegerArgumentType.integer(1, 10000))
                            .executes(ManaCommand::resetMana))))
        );
    }
    
    private static int setMana(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer player = ctx.getSource().getPlayer();
        if (player == null) {
            ctx.getSource().sendFailure(Component.literal("Only players can use this command"));
            return 0;
        }
        
        int amount = IntegerArgumentType.getInteger(ctx, "amount");
        var mana = ManaApi.get(player);
        
        // Если устанавливаемое значение больше максимальной маны, увеличиваем максимум
        if (amount > mana.getMaxMana()) {
            mana.setMaxMana(amount);
            mana.setMaxManaManuallySet(true);
        }
        
        mana.setCurrentMana(amount);
        ManaApi.sync(player);
        
        ctx.getSource().sendSuccess(
            () -> Component.literal("Set mana to " + amount + "/" + mana.getMaxMana()),
            true
        );
        
        return 1;
    }
    
    private static int setMaxMana(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer player = ctx.getSource().getPlayer();
        if (player == null) {
            ctx.getSource().sendFailure(Component.literal("Only players can use this command"));
            return 0;
        }
        
        int amount = IntegerArgumentType.getInteger(ctx, "amount");
        ManaApi.setMax(player, amount);
        
        var mana = ManaApi.get(player);
        mana.setMaxManaManuallySet(true);
        ManaApi.sync(player);
        
        ctx.getSource().sendSuccess(
            () -> Component.literal("Set max mana to " + amount),
            true
        );
        
        return 1;
    }
    
    private static int setManaRegen(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer player = ctx.getSource().getPlayer();
        if (player == null) {
            ctx.getSource().sendFailure(Component.literal("Only players can use this command"));
            return 0;
        }
        
        int amount = IntegerArgumentType.getInteger(ctx, "amount");
        ManaApi.setRegen(player, amount);
        
        var mana = ManaApi.get(player);
        mana.setRegenManuallySet(true);
        ManaApi.sync(player);
        
        ctx.getSource().sendSuccess(
            () -> Component.literal("Set mana regeneration to " + amount + " per second"),
            true
        );
        
        return 1;
    }
    
    private static int resetMana(CommandContext<CommandSourceStack> ctx) {
        ServerPlayer player = ctx.getSource().getPlayer();
        if (player == null) {
            ctx.getSource().sendFailure(Component.literal("Only players can use this command"));
            return 0;
        }
        
        int amount = IntegerArgumentType.getInteger(ctx, "amount");
        ManaApi.setMax(player, amount);
        
        var mana = ManaApi.get(player);
        mana.setMaxManaManuallySet(true);
        mana.setCurrentMana(amount);
        ManaApi.sync(player);
        
        ctx.getSource().sendSuccess(
            () -> Component.literal("Reset mana to " + amount + "/" + amount),
            true
        );
        
        return 1;
    }
}
