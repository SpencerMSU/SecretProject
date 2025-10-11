package com.example.examplemod.items;

import com.example.examplemod.accessory.BaseAccessoryItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.slot.SlotReference;

/**
 * Тестовый предмет с прямым добавлением слота anklet через SLOT_VALIDATION DataComponent
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
        
        // ВАЖНО! Accessories мод ищет тег НА УРОВНЕ КОМПОНЕНТОВ, а не в custom_data!
        // Используем утилитарный метод из BaseAccessoryItem для установки SLOT_VALIDATION DataComponent
        java.util.Set<String> validSlots = new java.util.HashSet<>();
        validSlots.add("anklet");
        
        if (BaseAccessoryItem.setSlotValidation(stack, validSlots)) {
            System.out.println("TestAnkletItem SLOT_VALIDATION DataComponent set successfully!");
        } else {
            System.err.println("TestAnkletItem: Failed to set SLOT_VALIDATION DataComponent!");
        }
        
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

