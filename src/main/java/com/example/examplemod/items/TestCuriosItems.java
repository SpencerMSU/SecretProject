package com.example.examplemod.items;

import com.example.examplemod.ExampleMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class TestCuriosItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ExampleMod.MODID);
    
    // Тестовые предметы для слотов Curios
    public static final DeferredItem<Item> TEST_RING = ITEMS.register("test_ring", 
        () -> new TestCuriosItem(new Item.Properties().rarity(Rarity.UNCOMMON), "Кольцо", "Ring"));
    
    public static final DeferredItem<Item> TEST_NECKLACE = ITEMS.register("test_necklace", 
        () -> new TestCuriosItem(new Item.Properties().rarity(Rarity.UNCOMMON), "Ожерелье", "Necklace"));
    
    public static final DeferredItem<Item> TEST_BRACELET = ITEMS.register("test_bracelet", 
        () -> new TestCuriosItem(new Item.Properties().rarity(Rarity.UNCOMMON), "Браслет", "Bracelet"));
    
    public static final DeferredItem<Item> TEST_BELT = ITEMS.register("test_belt", 
        () -> new TestCuriosItem(new Item.Properties().rarity(Rarity.UNCOMMON), "Пояс", "Belt"));
    
    public static final DeferredItem<Item> TEST_CHARM = ITEMS.register("test_charm", 
        () -> new TestCuriosItem(new Item.Properties().rarity(Rarity.UNCOMMON), "Амулет", "Charm"));
    
    public static final DeferredItem<Item> TEST_CLOAK = ITEMS.register("test_cloak", 
        () -> new TestCuriosItem(new Item.Properties().rarity(Rarity.UNCOMMON), "Плащ", "Cloak"));
    
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
    
    // Простой класс для тестовых предметов Curios
    public static class TestCuriosItem extends Item implements ICurioItem {
        private final String russianName;
        private final String englishName;
        
        public TestCuriosItem(Properties properties, String russianName, String englishName) {
            super(properties);
            this.russianName = russianName;
            this.englishName = englishName;
        }
        
        @Override
        public Component getName(ItemStack stack) {
            // Используем русское название по умолчанию
            return Component.literal(russianName);
        }
        
        public String getEnglishName() {
            return englishName;
        }
    }
}
