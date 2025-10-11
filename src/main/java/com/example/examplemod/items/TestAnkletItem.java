package com.example.examplemod.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;

/**
 * Тестовый предмет с прямым добавлением NBT тега anklet
 */
public class TestAnkletItem extends Item implements Accessory {
    
    public TestAnkletItem(Properties properties) {
        super(properties);
        System.out.println("TestAnkletItem constructor called!");
    }
    
    @Override
    public ItemStack getDefaultInstance() {
        System.out.println("TestAnkletItem getDefaultInstance called!");
        ItemStack stack = super.getDefaultInstance();
        
        // ПРАВИЛЬНАЯ структура NBT для Accessories мода!
        CompoundTag mainTag = new CompoundTag();
        CompoundTag slotValidation = new CompoundTag();
        
        // Список валидных слотов
        ListTag validSlots = new ListTag();
        validSlots.add(StringTag.valueOf("anklet"));
        slotValidation.put("valid_slots", validSlots);
        
        // Список невалидных слотов (пустой)
        ListTag invalidSlots = new ListTag();
        slotValidation.put("invalid_slots", invalidSlots);
        
        // Добавляем в главный тег
        mainTag.put("accessories:slot_validation", slotValidation);
        
        CustomData customData = CustomData.of(mainTag);
        stack.set(DataComponents.CUSTOM_DATA, customData);
        
        System.out.println("TestAnkletItem NBT set: " + mainTag.toString());
        System.out.println("TestAnkletItem CustomData: " + stack.get(DataComponents.CUSTOM_DATA));
        
        return stack;
    }
    
    // Обязательные методы интерфейса Accessory
    @Override
    public void onEquip(ItemStack stack, SlotReference reference) {
        System.out.println("TestAnkletItem equipped in slot: " + reference.slotName());
    }
    
    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        System.out.println("TestAnkletItem unequipped from slot: " + reference.slotName());
    }
    
    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        System.out.println("TestAnkletItem canEquip check for slot: " + reference.slotName());
        return true;
    }
}

