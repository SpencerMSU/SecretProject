package com.example.examplemod.accessory;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.mana.IMana;
import com.example.examplemod.mana.ManaApi;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * События для применения баффов от аксессуаров
 */
@EventBusSubscriber(modid = ExampleMod.MODID)
public class AccessoryEvents {
    
    private static final ResourceLocation HEALTH_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "accessory_health");
    private static final ResourceLocation SPEED_MODIFIER_ID = ResourceLocation.fromNamespaceAndPath(ExampleMod.MODID, "accessory_speed");
    
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        
        if (player.level().isClientSide()) {
            return;
        }
        
        // Обновляем каждые 20 тиков (1 секунда)
        if (player.tickCount % 20 != 0) {
            return;
        }
        
        AccessorySetManager.EquippedSetInfo setInfo = AccessorySetManager.getEquippedSetInfo(player);
        AccessoryStats stats = setInfo.getTotalStatsWithBonuses();
        
        // Применяем бонусы здоровья
        var healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (healthAttribute != null) {
            healthAttribute.removeModifier(HEALTH_MODIFIER_ID);
            if (stats.getHealth() > 0) {
                healthAttribute.addPermanentModifier(
                    new AttributeModifier(
                        HEALTH_MODIFIER_ID,
                        stats.getHealth(),
                        AttributeModifier.Operation.ADD_VALUE
                    )
                );
            }
        }
        
        // Применяем бонусы скорости
        var speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttribute != null) {
            speedAttribute.removeModifier(SPEED_MODIFIER_ID);
            if (stats.getSpeed() > 0) {
                speedAttribute.addPermanentModifier(
                    new AttributeModifier(
                        SPEED_MODIFIER_ID,
                        stats.getSpeed(),
                        AttributeModifier.Operation.ADD_VALUE
                    )
                );
            }
        }
        
        // Применяем реген маны (вызывается каждую секунду)
        if (stats.getManaRegen() > 0 && player instanceof net.minecraft.server.level.ServerPlayer sp) {
            IMana mana = ManaApi.get(player);
            int regenAmount = (int) (stats.getManaRegen() / 20.0); // Делим на 20 т.к. вызывается каждую секунду
            mana.addMana(regenAmount);
            ManaApi.sync(sp);
        }
        
        // Увеличиваем максимальную ману
        if (stats.getMana() > 0 && player instanceof net.minecraft.server.level.ServerPlayer sp) {
            IMana mana = ManaApi.get(player);
            
            // Сохраняем процент текущей маны
            int current = mana.getCurrentMana();
            int oldMax = mana.getMaxMana();
            float percent = oldMax > 0 ? (float) current / oldMax : 1.0f;
            
            // Устанавливаем новый максимум (базовый + бонус)
            int baseMax = 100; // Базовое значение
            int newMax = baseMax + (int) stats.getMana();
            mana.setMaxMana(newMax);
            
            // Восстанавливаем процент маны
            mana.setCurrentMana((int) (newMax * percent));
            
            ManaApi.sync(sp);
        }
        
        // Остальные баффы (урон, защита, сила стихии) будут применяться в других местах
        // Например, при атаке или использовании заклинаний
    }
}
