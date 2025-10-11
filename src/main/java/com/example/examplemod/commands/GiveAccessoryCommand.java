package com.example.examplemod.commands;

import com.example.examplemod.accessory.*;
import com.example.examplemod.items.ModItems;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

/**
 * Команда для выдачи аксессуаров (для тестирования)
 * /giveaccessory <fire|water> <ring|necklace|bracelet|belt|charm|cloak> <rarity> <level>
 */
public class GiveAccessoryCommand {
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("giveaccessory")
                .requires(source -> source.hasPermission(2)) // Требует OP
                .then(Commands.literal("fire")
                    .then(Commands.literal("ring")
                        .then(registerRarityAndLevel(AccessoryElement.FIRE, AccessoryType.RING)))
                    .then(Commands.literal("necklace")
                        .then(registerRarityAndLevel(AccessoryElement.FIRE, AccessoryType.NECKLACE)))
                    .then(Commands.literal("bracelet")
                        .then(registerRarityAndLevel(AccessoryElement.FIRE, AccessoryType.BRACELET)))
                    .then(Commands.literal("belt")
                        .then(registerRarityAndLevel(AccessoryElement.FIRE, AccessoryType.BELT)))
                    .then(Commands.literal("charm")
                        .then(registerRarityAndLevel(AccessoryElement.FIRE, AccessoryType.CHARM)))
                    .then(Commands.literal("cloak")
                        .then(registerRarityAndLevel(AccessoryElement.FIRE, AccessoryType.CLOAK))))
                .then(Commands.literal("water")
                    .then(Commands.literal("ring")
                        .then(registerRarityAndLevel(AccessoryElement.WATER, AccessoryType.RING)))
                    .then(Commands.literal("necklace")
                        .then(registerRarityAndLevel(AccessoryElement.WATER, AccessoryType.NECKLACE)))
                    .then(Commands.literal("bracelet")
                        .then(registerRarityAndLevel(AccessoryElement.WATER, AccessoryType.BRACELET)))
                    .then(Commands.literal("belt")
                        .then(registerRarityAndLevel(AccessoryElement.WATER, AccessoryType.BELT)))
                    .then(Commands.literal("charm")
                        .then(registerRarityAndLevel(AccessoryElement.WATER, AccessoryType.CHARM)))
                    .then(Commands.literal("cloak")
                        .then(registerRarityAndLevel(AccessoryElement.WATER, AccessoryType.CLOAK))))
        );
    }
    
    private static com.mojang.brigadier.builder.ArgumentBuilder<CommandSourceStack, ?> registerRarityAndLevel(
            AccessoryElement element, AccessoryType type) {
        var builder = Commands.literal("common")
            .then(Commands.argument("level", IntegerArgumentType.integer(1, 10))
                .executes(ctx -> giveAccessory(ctx, element, type, AccessoryRarity.COMMON)));
        
        builder.then(Commands.literal("uncommon")
            .then(Commands.argument("level", IntegerArgumentType.integer(1, 10))
                .executes(ctx -> giveAccessory(ctx, element, type, AccessoryRarity.UNCOMMON))));
        
        builder.then(Commands.literal("rare")
            .then(Commands.argument("level", IntegerArgumentType.integer(1, 10))
                .executes(ctx -> giveAccessory(ctx, element, type, AccessoryRarity.RARE))));
        
        builder.then(Commands.literal("epic")
            .then(Commands.argument("level", IntegerArgumentType.integer(1, 10))
                .executes(ctx -> giveAccessory(ctx, element, type, AccessoryRarity.EPIC))));
        
        builder.then(Commands.literal("legendary")
            .then(Commands.argument("level", IntegerArgumentType.integer(1, 10))
                .executes(ctx -> giveAccessory(ctx, element, type, AccessoryRarity.LEGENDARY))));
        
        builder.then(Commands.literal("mythic")
            .then(Commands.argument("level", IntegerArgumentType.integer(1, 10))
                .executes(ctx -> giveAccessory(ctx, element, type, AccessoryRarity.MYTHIC))));
        
        return builder;
    }
    
    private static int giveAccessory(CommandContext<CommandSourceStack> ctx, 
                                    AccessoryElement element, 
                                    AccessoryType type,
                                    AccessoryRarity rarity) {
        ServerPlayer player = ctx.getSource().getPlayer();
        if (player == null) {
            ctx.getSource().sendFailure(Component.literal("Only players can use this command"));
            return 0;
        }
        
        int level = IntegerArgumentType.getInteger(ctx, "level");
        
        ItemStack stack = createAccessoryStack(element, type);
        if (stack.isEmpty()) {
            ctx.getSource().sendFailure(Component.literal("Failed to create accessory"));
            return 0;
        }
        
        BaseAccessoryItem.setRarity(stack, rarity);
        BaseAccessoryItem.setLevel(stack, level);
        
        player.addItem(stack);
        
        ctx.getSource().sendSuccess(
            () -> Component.literal("Given " + rarity.getEnglishName() + " " + 
                  element.getEnglishName() + " " + type.getEnglishName() + " +" + level),
            true
        );
        
        return 1;
    }
    
    private static ItemStack createAccessoryStack(AccessoryElement element, AccessoryType type) {
        if (element == AccessoryElement.FIRE) {
            return switch (type) {
                case RING -> new ItemStack(ModItems.FIRE_RING.get());
                case NECKLACE -> new ItemStack(ModItems.FIRE_NECKLACE.get());
                case BRACELET -> new ItemStack(ModItems.FIRE_BRACELET.get());
                case BELT -> new ItemStack(ModItems.FIRE_BELT.get());
                case CHARM -> new ItemStack(ModItems.FIRE_CHARM.get());
                case CLOAK -> new ItemStack(ModItems.FIRE_CLOAK.get());
            };
        } else {
            return switch (type) {
                case RING -> new ItemStack(ModItems.WATER_RING.get());
                case NECKLACE -> new ItemStack(ModItems.WATER_NECKLACE.get());
                case BRACELET -> new ItemStack(ModItems.WATER_BRACELET.get());
                case BELT -> new ItemStack(ModItems.WATER_BELT.get());
                case CHARM -> new ItemStack(ModItems.WATER_CHARM.get());
                case CLOAK -> new ItemStack(ModItems.WATER_CLOAK.get());
            };
        }
    }
}
