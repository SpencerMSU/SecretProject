package com.example.examplemod.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
        
        // ВАЖНО! Accessories мод ищет тег НА УРОВНЕ КОМПОНЕНТОВ, а не в custom_data!
        // Используем специальный DataComponent от мода Accessories
        try {
            // Получаем класс AccessoriesDataComponents
            Class<?> dataComponentsClass = Class.forName("io.wispforest.accessories.api.AccessoriesDataComponents");
            
            // Получаем поле SLOT_VALIDATION
            java.lang.reflect.Field slotValidationField = dataComponentsClass.getDeclaredField("SLOT_VALIDATION");
            slotValidationField.setAccessible(true);
            
            @SuppressWarnings("unchecked")
            net.minecraft.core.component.DataComponentType<Object> slotValidationType = 
                (net.minecraft.core.component.DataComponentType<Object>) slotValidationField.get(null);
            
            // Создаем SlotValidation с нужным слотом
            java.util.Set<String> validSlots = new java.util.HashSet<>();
            validSlots.add("anklet");
            java.util.Set<String> invalidSlots = new java.util.HashSet<>();
            
            // Получаем конструктор SlotValidation
            Class<?> slotValidationClass = Class.forName("io.wispforest.accessories.api.data.SlotValidation");
            java.lang.reflect.Constructor<?> constructor = slotValidationClass.getDeclaredConstructor(java.util.Set.class, java.util.Set.class);
            constructor.setAccessible(true);
            
            Object slotValidation = constructor.newInstance(validSlots, invalidSlots);
            
            // Устанавливаем компонент
            @SuppressWarnings("unchecked")
            net.minecraft.core.component.DataComponentType rawType = (net.minecraft.core.component.DataComponentType) slotValidationType;
            stack.set(rawType, slotValidation);
            
            System.out.println("TestAnkletItem SLOT_VALIDATION DataComponent set successfully!");
            
        } catch (Exception e) {
            System.err.println("ERROR: Failed to set SlotValidation component!");
            e.printStackTrace();
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

