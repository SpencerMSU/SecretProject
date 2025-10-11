package com.example.examplemod.items;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.accessory.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = 
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExampleMod.MODID);

    // Вкладка для колец (слот "ring")
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> RINGS_TAB = 
        CREATIVE_MODE_TABS.register("rings", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.examplemod.rings"))
            .icon(() -> BaseAccessoryItem.createForCreativeTabWithSlot((BaseAccessoryItem) ModItems.FIRE_RING.get(), "ring"))
            .displayItems((parameters, output) -> {
                output.accept(BaseAccessoryItem.createForCreativeTabWithSlot((BaseAccessoryItem) ModItems.FIRE_RING.get(), "ring"));
                output.accept(BaseAccessoryItem.createForCreativeTabWithSlot((BaseAccessoryItem) ModItems.WATER_RING.get(), "ring"));
            })
            .build());

    // Вкладка для ожерелий (слот "necklace")
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NECKLACES_TAB = 
        CREATIVE_MODE_TABS.register("necklaces", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.examplemod.necklaces"))
            .icon(() -> BaseAccessoryItem.createForCreativeTabWithSlot((BaseAccessoryItem) ModItems.FIRE_NECKLACE.get(), "necklace"))
            .displayItems((parameters, output) -> {
                output.accept(BaseAccessoryItem.createForCreativeTabWithSlot((BaseAccessoryItem) ModItems.FIRE_NECKLACE.get(), "necklace"));
                output.accept(BaseAccessoryItem.createForCreativeTabWithSlot((BaseAccessoryItem) ModItems.WATER_NECKLACE.get(), "necklace"));
            })
            .build());

    // Вкладка для браслетов (слот "bracelet")
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BRACELETS_TAB = 
        CREATIVE_MODE_TABS.register("bracelets", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.examplemod.bracelets"))
            .icon(() -> BaseAccessoryItem.createForCreativeTabWithSlot((BaseAccessoryItem) ModItems.FIRE_BRACELET.get(), "bracelet"))
            .displayItems((parameters, output) -> {
                output.accept(BaseAccessoryItem.createForCreativeTabWithSlot((BaseAccessoryItem) ModItems.FIRE_BRACELET.get(), "bracelet"));
                output.accept(BaseAccessoryItem.createForCreativeTabWithSlot((BaseAccessoryItem) ModItems.WATER_BRACELET.get(), "bracelet"));
            })
            .build());

    // Вкладка для поясов (слот "belt")
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BELTS_TAB = 
        CREATIVE_MODE_TABS.register("belts", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.examplemod.belts"))
            .icon(() -> BaseAccessoryItem.createForCreativeTabWithSlot((BaseAccessoryItem) ModItems.FIRE_BELT.get(), "belt"))
            .displayItems((parameters, output) -> {
                output.accept(BaseAccessoryItem.createForCreativeTabWithSlot((BaseAccessoryItem) ModItems.FIRE_BELT.get(), "belt"));
                output.accept(BaseAccessoryItem.createForCreativeTabWithSlot((BaseAccessoryItem) ModItems.WATER_BELT.get(), "belt"));
            })
            .build());

    // Вкладка для амулетов (слот "charm")
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CHARMS_TAB = 
        CREATIVE_MODE_TABS.register("charms", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.examplemod.charms"))
            .icon(() -> BaseAccessoryItem.createForCreativeTabWithSlot((BaseAccessoryItem) ModItems.FIRE_CHARM.get(), "charm"))
            .displayItems((parameters, output) -> {
                output.accept(BaseAccessoryItem.createForCreativeTabWithSlot((BaseAccessoryItem) ModItems.FIRE_CHARM.get(), "charm"));
                output.accept(BaseAccessoryItem.createForCreativeTabWithSlot((BaseAccessoryItem) ModItems.WATER_CHARM.get(), "charm"));
            })
            .build());

    // Вкладка для плащей (слот "back")
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CLOAKS_TAB = 
        CREATIVE_MODE_TABS.register("cloaks", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.examplemod.cloaks"))
            .icon(() -> BaseAccessoryItem.createForCreativeTabWithSlot((BaseAccessoryItem) ModItems.FIRE_CLOAK.get(), "back"))
            .displayItems((parameters, output) -> {
                output.accept(BaseAccessoryItem.createForCreativeTabWithSlot((BaseAccessoryItem) ModItems.FIRE_CLOAK.get(), "back"));
                output.accept(BaseAccessoryItem.createForCreativeTabWithSlot((BaseAccessoryItem) ModItems.WATER_CLOAK.get(), "back"));
            })
            .build());

    // Общая вкладка
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ACCESSORIES_TAB = 
        CREATIVE_MODE_TABS.register("accessories", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.examplemod.accessories"))
            .icon(() -> new ItemStack(ModItems.ENCHANTMENT_STONE.get()))
            .displayItems((parameters, output) -> {
                output.accept(ModItems.ENCHANTMENT_STONE.get());
                output.accept(ModItems.TEST_ANKLET.get());
            })
            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
