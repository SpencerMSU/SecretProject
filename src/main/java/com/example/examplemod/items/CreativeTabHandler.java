package com.example.examplemod.items;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.accessory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber(modid = ExampleMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CreativeTabHandler {
    
    @SubscribeEvent
    public static void onBuildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        System.out.println("BuildCreativeModeTabContentsEvent fired for tab: " + event.getTabKey());
        
        // Примечание: SLOT_VALIDATION теперь устанавливается через DataComponent в getDefaultInstance()
        // Это правильный подход для мода Accessories - он ожидает DataComponent, а не NBT теги
        event.getEntries().forEach(entry -> {
            ItemStack stack = entry.getKey();
            if (stack.getItem() instanceof BaseAccessoryItem accessory) {
                System.out.println("Found accessory in creative tab: " + accessory.getClass().getSimpleName() + 
                                   " with type " + accessory.getAccessoryType());
                // Slot validation уже установлена в getDefaultInstance() через SLOT_VALIDATION DataComponent
            }
        });
    }
}
