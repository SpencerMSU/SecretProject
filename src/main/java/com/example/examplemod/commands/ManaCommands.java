package com.example.examplemod.commands;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.mana.ManaHelper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

/**
 * Commands for testing and manipulating the mana system.
 */
@EventBusSubscriber(modid = ExampleMod.MODID)
public class ManaCommands {
    
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        
        dispatcher.register(Commands.literal("mana")
            .then(Commands.literal("get")
                .executes(ManaCommands::getMana))
            .then(Commands.literal("set")
                .then(Commands.argument("amount", FloatArgumentType.floatArg(0))
                    .executes(ctx -> setMana(ctx, FloatArgumentType.getFloat(ctx, "amount")))))
            .then(Commands.literal("add")
                .then(Commands.argument("amount", FloatArgumentType.floatArg(0))
                    .executes(ctx -> addMana(ctx, FloatArgumentType.getFloat(ctx, "amount")))))
            .then(Commands.literal("setmax")
                .then(Commands.argument("amount", FloatArgumentType.floatArg(0))
                    .executes(ctx -> setMaxMana(ctx, FloatArgumentType.getFloat(ctx, "amount")))))
        );
    }
    
    private static int getMana(CommandContext<CommandSourceStack> ctx) {
        if (ctx.getSource().getEntity() instanceof ServerPlayer player) {
            float current = ManaHelper.getCurrentMana(player);
            float max = ManaHelper.getMaxMana(player);
            ctx.getSource().sendSuccess(() -> 
                Component.literal(String.format("Mana: %.1f / %.1f", current, max)), false);
            return 1;
        }
        return 0;
    }
    
    private static int setMana(CommandContext<CommandSourceStack> ctx, float amount) {
        if (ctx.getSource().getEntity() instanceof ServerPlayer player) {
            ManaHelper.setCurrentMana(player, amount);
            ctx.getSource().sendSuccess(() -> 
                Component.literal(String.format("Set mana to %.1f", amount)), false);
            return 1;
        }
        return 0;
    }
    
    private static int addMana(CommandContext<CommandSourceStack> ctx, float amount) {
        if (ctx.getSource().getEntity() instanceof ServerPlayer player) {
            float added = ManaHelper.addMana(player, amount);
            ctx.getSource().sendSuccess(() -> 
                Component.literal(String.format("Added %.1f mana (actual: %.1f)", amount, added)), false);
            return 1;
        }
        return 0;
    }
    
    private static int setMaxMana(CommandContext<CommandSourceStack> ctx, float amount) {
        if (ctx.getSource().getEntity() instanceof ServerPlayer player) {
            ManaHelper.setMaxMana(player, amount);
            ctx.getSource().sendSuccess(() -> 
                Component.literal(String.format("Set max mana to %.1f", amount)), false);
            return 1;
        }
        return 0;
    }
}
