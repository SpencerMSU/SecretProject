package com.example.examplemod.items;

import com.example.examplemod.ExampleMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CreativeTabHandler {
    
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = 
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExampleMod.MODID);

    // Вкладка ProjectRadiation для камней улучшения
    public static final Supplier<CreativeModeTab> PROJECT_RADIATION_TAB = CREATIVE_MODE_TABS.register("project_radiation",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.examplemod.project_radiation"))
            .icon(() -> new ItemStack(ModItems.ENCHANTMENT_STONE.get()))
            .displayItems((parameters, output) -> {
                output.accept(ModItems.ENCHANTMENT_STONE.get());
                output.accept(ModItems.RARITY_UPGRADE_STONE.get());
            })
            .build()
    );

    // Вкладка огненных аксессуаров
    public static final Supplier<CreativeModeTab> FIRE_ACCESSORIES_TAB = CREATIVE_MODE_TABS.register("fire_accessories",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.examplemod.fire_accessories"))
            .icon(() -> new ItemStack(ModItems.FIRE_CHARM.get()))
            .displayItems((parameters, output) -> {
                output.accept(ModItems.FIRE_CHARM.get());
                output.accept(ModItems.FIRE_MASK.get());
                output.accept(ModItems.FIRE_HAT.get());
                output.accept(ModItems.FIRE_NECKLACE.get());
                output.accept(ModItems.FIRE_CAPE.get());
                output.accept(ModItems.FIRE_BACK.get());
                output.accept(ModItems.FIRE_WRIST.get());
                output.accept(ModItems.FIRE_HAND_1.get());
                output.accept(ModItems.FIRE_HAND_2.get());
                output.accept(ModItems.FIRE_RING_1.get());
                output.accept(ModItems.FIRE_RING_2.get());
                output.accept(ModItems.FIRE_BELT.get());
                output.accept(ModItems.FIRE_ANKLET.get());
                output.accept(ModItems.FIRE_SHOES.get());
            })
            .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}