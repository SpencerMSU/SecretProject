package com.example.examplemod.accessory;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.AccessoriesContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.*;


public class AccessorySetManager {
    

    public static class EquippedSetInfo {
        public final Map<AccessoryElement, List<ItemStack>> accessoriesByElement = new EnumMap<>(AccessoryElement.class);
        public final Map<AccessoryElement, Set<AccessoryType>> typesByElement = new EnumMap<>(AccessoryElement.class);
        public final AccessoryStats totalStats;
        public final AccessoryStats setBonusStats;
        public final boolean hasFullFireSet;
        public final boolean hasFullWaterSet;
        public final boolean hasMaxFireSet;
        public final boolean hasMaxWaterSet;
        
        public EquippedSetInfo(Player player) {
            // Инициализация
            for (AccessoryElement element : AccessoryElement.values()) {
                accessoriesByElement.put(element, new ArrayList<>());
                typesByElement.put(element, EnumSet.noneOf(AccessoryType.class));
            }
            
            // Собираем все надетые аксессуары
            List<ItemStack> equippedAccessories = new ArrayList<>();
            
            // TODO: Временно упрощено - нужно обновить для нового API Accessories
            // AccessoriesCapability capability = AccessoriesCapability.get(player);
            // if (capability != null) {
            //     // Получаем все аксессуары
            // }
            
            for (ItemStack stack : equippedAccessories) {
                if (stack.getItem() instanceof BaseAccessoryItem accessory) {
                    AccessoryElement element = accessory.getElement();
                    AccessoryType type = accessory.getAccessoryType();
                    
                    accessoriesByElement.get(element).add(stack);
                    typesByElement.get(element).add(type);
                }
            }
            
            // Проверяем полные наборы (6/6 разных типов)
            hasFullFireSet = typesByElement.get(AccessoryElement.FIRE).size() == 6;
            hasFullWaterSet = typesByElement.get(AccessoryElement.WATER).size() == 6;
            
            // Проверяем максимальные наборы (6/6 мифических + 10 уровень)
            hasMaxFireSet = hasFullFireSet && isMaxSet(accessoriesByElement.get(AccessoryElement.FIRE));
            hasMaxWaterSet = hasFullWaterSet && isMaxSet(accessoriesByElement.get(AccessoryElement.WATER));
            
            // Подсчитываем общие статы
            totalStats = calculateTotalStats(equippedAccessories);
            setBonusStats = calculateSetBonuses();
        }
        
        private boolean isMaxSet(List<ItemStack> accessories) {
            if (accessories.size() != 6) return false;
            
            for (ItemStack stack : accessories) {
                AccessoryRarity rarity = BaseAccessoryItem.getRarity(stack);
                int level = BaseAccessoryItem.getLevel(stack);
                
                if (rarity != AccessoryRarity.MYTHIC || level != 10) {
                    return false;
                }
            }
            return true;
        }
        
        private AccessoryStats calculateTotalStats(List<ItemStack> equippedAccessories) {
            double health = 0, mana = 0, manaRegen = 0;
            double damage = 0, defense = 0, speed = 0;
            double elementalPower = 0, elementalResistance = 0;
            
            for (ItemStack stack : equippedAccessories) {
                if (stack.getItem() instanceof BaseAccessoryItem accessory) {
                    AccessoryStats stats = accessory.getStats(stack);
                    
                    health += stats.getHealth();
                    mana += stats.getMana();
                    manaRegen += stats.getManaRegen();
                    damage += stats.getDamage();
                    defense += stats.getDefense();
                    speed += stats.getSpeed();
                    elementalPower += stats.getElementalPower();
                    elementalResistance += stats.getElementalResistance();
                }
            }
            
            return new AccessoryStats(health, mana, manaRegen, damage, defense, speed, elementalPower, elementalResistance);
        }
        
        private AccessoryStats calculateSetBonuses() {
            double health = 0, mana = 0, manaRegen = 0;
            double damage = 0, defense = 0, speed = 0;
            double elementalPower = 0, elementalResistance = 0;
            
            // Бонус полного огненного набора
            if (hasFullFireSet) {
                AccessoryStats fireBonus = AccessoryStats.getFullSetBonus(AccessoryElement.FIRE);
                health += fireBonus.getHealth();
                mana += fireBonus.getMana();
                manaRegen += fireBonus.getManaRegen();
                damage += fireBonus.getDamage();
                defense += fireBonus.getDefense();
                speed += fireBonus.getSpeed();
                elementalPower += fireBonus.getElementalPower();
                elementalResistance += fireBonus.getElementalResistance();
            }
            
            // Бонус полного водного набора
            if (hasFullWaterSet) {
                AccessoryStats waterBonus = AccessoryStats.getFullSetBonus(AccessoryElement.WATER);
                health += waterBonus.getHealth();
                mana += waterBonus.getMana();
                manaRegen += waterBonus.getManaRegen();
                damage += waterBonus.getDamage();
                defense += waterBonus.getDefense();
                speed += waterBonus.getSpeed();
                elementalPower += waterBonus.getElementalPower();
                elementalResistance += waterBonus.getElementalResistance();
            }
            
            // Бонус максимального огненного набора
            if (hasMaxFireSet) {
                AccessoryStats maxFireBonus = AccessoryStats.getMaxSetBonus(AccessoryElement.FIRE);
                health += maxFireBonus.getHealth();
                mana += maxFireBonus.getMana();
                manaRegen += maxFireBonus.getManaRegen();
                damage += maxFireBonus.getDamage();
                defense += maxFireBonus.getDefense();
                speed += maxFireBonus.getSpeed();
                elementalPower += maxFireBonus.getElementalPower();
                elementalResistance += maxFireBonus.getElementalResistance();
            }
            
            // Бонус максимального водного набора
            if (hasMaxWaterSet) {
                AccessoryStats maxWaterBonus = AccessoryStats.getMaxSetBonus(AccessoryElement.WATER);
                health += maxWaterBonus.getHealth();
                mana += maxWaterBonus.getMana();
                manaRegen += maxWaterBonus.getManaRegen();
                damage += maxWaterBonus.getDamage();
                defense += maxWaterBonus.getDefense();
                speed += maxWaterBonus.getSpeed();
                elementalPower += maxWaterBonus.getElementalPower();
                elementalResistance += maxWaterBonus.getElementalResistance();
            }
            
            return new AccessoryStats(health, mana, manaRegen, damage, defense, speed, elementalPower, elementalResistance);
        }
        
        public AccessoryStats getTotalStatsWithBonuses() {
            double health = totalStats.getHealth() + setBonusStats.getHealth();
            double mana = totalStats.getMana() + setBonusStats.getMana();
            double manaRegen = totalStats.getManaRegen() + setBonusStats.getManaRegen();
            double damage = totalStats.getDamage() + setBonusStats.getDamage();
            double defense = totalStats.getDefense() + setBonusStats.getDefense();
            double speed = totalStats.getSpeed() + setBonusStats.getSpeed();
            double elementalPower = totalStats.getElementalPower() + setBonusStats.getElementalPower();
            double elementalResistance = totalStats.getElementalResistance() + setBonusStats.getElementalResistance();
            
            return new AccessoryStats(health, mana, manaRegen, damage, defense, speed, elementalPower, elementalResistance);
        }
    }
    
    /**
     * Получить информацию о надетых аксессуарах игрока
     */
    public static EquippedSetInfo getEquippedSetInfo(Player player) {
        return new EquippedSetInfo(player);
    }
}
