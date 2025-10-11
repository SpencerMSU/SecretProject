package com.example.examplemod.items;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.accessory.*;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber(modid = ExampleMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CreativeTabHandler {
    
    @SubscribeEvent
    public static void onBuildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        System.out.println("BuildCreativeModeTabContentsEvent fired for tab: " + event.getTabKey());
        
        // Проходимся по всем добавленным предметам и добавляем NBT теги к аксессуарам
        event.getEntries().forEach(entry -> {
            ItemStack stack = entry.getKey();
            if (stack.getItem() instanceof BaseAccessoryItem accessory) {
                System.out.println("Found accessory in creative tab: " + accessory.getClass().getSimpleName());
                
                // Добавляем NBT теги
                String[] compatibleSlots = accessory.getAccessoryType().getCompatibleSlots();
                if (compatibleSlots.length > 0) {
                    stack.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY, data -> 
                        data.update(tag -> {
                            ListTag validSlotsList = new ListTag();
                            for (String slot : compatibleSlots) {
                                validSlotsList.add(net.minecraft.nbt.StringTag.valueOf(slot));
                            }
                            tag.put("accessories:valid_slots", validSlotsList);
                            System.out.println("NBT applied to " + accessory.getClass().getSimpleName() + ": " + tag.toString());
                        })
                    );
                }
            }
        });
    }
}
