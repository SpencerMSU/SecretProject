package com.example.examplemod.accessory;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.items.EnchantmentStoneItem;
import com.example.examplemod.items.ModItems;
import com.example.examplemod.items.RarityUpgradeStoneItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

@EventBusSubscriber(modid = ExampleMod.MODID)
public class AnvilUpgradeHandler {
    
    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();
        
        if (left.isEmpty() || right.isEmpty()) {
            return;
        }
        
        // Проверяем, является ли левый предмет аксессуаром
        if (!(left.getItem() instanceof BaseAccessoryItem)) {
            return;
        }
        
        AccessoryData data = left.getOrDefault(ModDataComponents.ACCESSORY_DATA.get(), AccessoryData.createDefault());
        
        // Улучшение уровня (камень заточки)
        if (right.getItem() instanceof EnchantmentStoneItem) {
            if (data.level() >= AccessoryData.MAX_LEVEL) {
                return; // Максимальный уровень достигнут
            }
            
            ItemStack result = left.copy();
            AccessoryData newData = data.withLevel(data.level() + 1);
            result.set(ModDataComponents.ACCESSORY_DATA.get(), newData);
            
            // Устанавливаем стоимость в уровнях опыта
            int cost = data.level() * 5;
            event.setCost(cost);
            event.setMaterialCost(1);
            event.setOutput(result);
            
            return;
        }
        
        // Улучшение редкости (камень улучшения редкости)
        if (right.getItem() instanceof RarityUpgradeStoneItem) {
            AccessoryRarity currentRarity = data.rarity();
            AccessoryRarity nextRarity = getNextRarity(currentRarity);
            
            if (nextRarity == null) {
                return; // Максимальная редкость достигнута
            }
            
            ItemStack result = left.copy();
            AccessoryData newData = data.withRarity(nextRarity);
            result.set(ModDataComponents.ACCESSORY_DATA.get(), newData);
            
            // Стоимость зависит от текущей редкости
            int cost = switch (currentRarity) {
                case COMMON -> 10;
                case UNCOMMON -> 15;
                case RARE -> 20;
                case EPIC -> 30;
                case LEGENDARY -> 40;
                case MYTHICAL -> 50;
                default -> 60;
            };
            
            event.setCost(cost);
            event.setMaterialCost(1);
            event.setOutput(result);
        }
    }
    
    private static AccessoryRarity getNextRarity(AccessoryRarity current) {
        return switch (current) {
            case COMMON -> AccessoryRarity.UNCOMMON;
            case UNCOMMON -> AccessoryRarity.RARE;
            case RARE -> AccessoryRarity.EPIC;
            case EPIC -> AccessoryRarity.LEGENDARY;
            case LEGENDARY -> AccessoryRarity.MYTHICAL;
            case MYTHICAL -> AccessoryRarity.ABSOLUTE;
            case ABSOLUTE -> null; // Максимальная редкость
        };
    }
}

