package com.example.examplemod.commands;

import com.example.examplemod.mana.ManaSystem;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ManaCommand {
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("mana")
            .then(Commands.literal("info")
                .executes(ManaCommand::showManaInfo))
        );
    }
    
    private static int showManaInfo(CommandContext<CommandSourceStack> context) {
        try {
            ServerPlayer player = context.getSource().getPlayerOrException();
            
            int currentMana = ManaSystem.getCurrentMana(player);
            int maxMana = ManaSystem.getMaxMana(player);
            float cooldownMultiplier = ManaSystem.getCooldownMultiplier(player);
            
            player.sendSystemMessage(Component.literal("=== ИНФОРМАЦИЯ О МАНЕ ==="));
            player.sendSystemMessage(Component.literal("Текущая мана: " + currentMana + "/" + maxMana));
            player.sendSystemMessage(Component.literal("Множитель перезарядки: " + String.format("%.2f", cooldownMultiplier)));
            
            return 1;
        } catch (com.mojang.brigadier.exceptions.CommandSyntaxException e) {
            return 0;
        }
    }
}