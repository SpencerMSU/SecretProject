package com.example.examplemod.accessory;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.effects.ModEffects;
import com.example.examplemod.items.ModItems;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@EventBusSubscriber(modid = ExampleMod.MODID)
public class SetBonusHandler {

    private static List<Item> fireAccessories = null;
    private static List<Item> waterAccessories = null;

    private static List<Item> getFireAccessories() {
        if (fireAccessories == null) {
            fireAccessories = List.of(
                ModItems.FIRE_CHARM.get(),
                ModItems.FIRE_MASK.get(),
                ModItems.FIRE_HAT.get(),
                ModItems.FIRE_NECKLACE.get(),
                ModItems.FIRE_CAPE.get(),
                ModItems.FIRE_BACK.get(),
                ModItems.FIRE_WRIST.get(),
                ModItems.FIRE_HAND_1.get(),
                ModItems.FIRE_HAND_2.get(),
                ModItems.FIRE_RING_1.get(),
                ModItems.FIRE_RING_2.get(),
                ModItems.FIRE_BELT.get(),
                ModItems.FIRE_ANKLET.get(),
                ModItems.FIRE_SHOES.get()
            );
        }
        return fireAccessories;
    }

    private static List<Item> getWaterAccessories() {
        if (waterAccessories == null) {
            waterAccessories = List.of(
                ModItems.WATER_CHARM.get(),
                ModItems.WATER_MASK.get(),
                ModItems.WATER_HAT.get(),
                ModItems.WATER_NECKLACE.get(),
                ModItems.WATER_CAPE.get(),
                ModItems.WATER_BACK.get(),
                ModItems.WATER_WRIST.get(),
                ModItems.WATER_HAND_1.get(),
                ModItems.WATER_HAND_2.get(),
                ModItems.WATER_RING_1.get(),
                ModItems.WATER_RING_2.get(),
                ModItems.WATER_BELT.get(),
                ModItems.WATER_ANKLET.get(),
                ModItems.WATER_SHOES.get()
            );
        }
        return waterAccessories;
    }

    // Отслеживание состояния полного сета для каждого игрока
    private static final Map<UUID, SetStatus> FULL_SET_STATUS = new HashMap<>();
    
    private static class SetStatus {
        public final boolean fireSet;
        public final boolean waterSet;
        
        public SetStatus(boolean fireSet, boolean waterSet) {
            this.fireSet = fireSet;
            this.waterSet = waterSet;
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();

        // Проверяем только каждую секунду (20 тиков)
        if (player.level().getGameTime() % 20 != 0) {
            return;
        }

        // Проверяем наличие полных сетов
        int fireAccessoriesCount = countFireAccessories(player);
        int waterAccessoriesCount = countWaterAccessories(player);
        boolean hasFullFireSet = fireAccessoriesCount >= 14;
        boolean hasFullWaterSet = waterAccessoriesCount >= 14;

        // Получаем предыдущее состояние
        SetStatus previousStatus = FULL_SET_STATUS.get(player.getUUID());
        if (previousStatus == null) {
            previousStatus = new SetStatus(false, false);
        }

        // Обрабатываем огненный сет
        handleFireSet(player, hasFullFireSet, previousStatus.fireSet);
        
        // Обрабатываем водный сет
        handleWaterSet(player, hasFullWaterSet, previousStatus.waterSet);

        // Обновляем статус
        FULL_SET_STATUS.put(player.getUUID(), new SetStatus(hasFullFireSet, hasFullWaterSet));
    }

    private static void handleFireSet(Player player, boolean hasFullSet, boolean previousStatus) {
        if (hasFullSet) {
            // Отправляем уведомление только при первом надевании полного сета
            if (!previousStatus) {
                player.sendSystemMessage(
                    Component.literal("")
                );
                player.sendSystemMessage(
                    Component.literal("════════════════════════════════")
                        .withStyle(ChatFormatting.DARK_RED, ChatFormatting.BOLD)
                );
                player.sendSystemMessage(
                    Component.literal("🔥 ПОЛНЫЙ ОГНЕННЫЙ СЕТ АКТИВИРОВАН! 🔥")
                        .withStyle(ChatFormatting.RED, ChatFormatting.BOLD)
                );
                player.sendSystemMessage(
                    Component.literal("Бонусы:")
                        .withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD)
                );
                player.sendSystemMessage(
                    Component.literal("  ∞ Бесконечная защита от огня")
                        .withStyle(ChatFormatting.YELLOW)
                );
                player.sendSystemMessage(
                    Component.literal("  ⚔ Сила II")
                        .withStyle(ChatFormatting.YELLOW)
                );
                player.sendSystemMessage(
                    Component.literal("  🛡 Сопротивление II")
                        .withStyle(ChatFormatting.YELLOW)
                );
                player.sendSystemMessage(
                    Component.literal("  ❤ Регенерация I")
                        .withStyle(ChatFormatting.YELLOW)
                );
                player.sendSystemMessage(
                    Component.literal("  ➤ Скорость I")
                        .withStyle(ChatFormatting.YELLOW)
                );
                player.sendSystemMessage(
                    Component.literal("  ✨ Регенерация маны V")
                        .withStyle(ChatFormatting.YELLOW)
                );
                player.sendSystemMessage(
                    Component.literal("  🔥 -40% стоимость огненных заклинаний")
                        .withStyle(ChatFormatting.YELLOW)
                );
                player.sendSystemMessage(
                    Component.literal("  ⏱ -15% время перезарядки")
                        .withStyle(ChatFormatting.YELLOW)
                );
                player.sendSystemMessage(
                    Component.literal("  💎 +200 максимальная мана")
                        .withStyle(ChatFormatting.YELLOW)
                );
                player.sendSystemMessage(
                    Component.literal("════════════════════════════════")
                        .withStyle(ChatFormatting.DARK_RED, ChatFormatting.BOLD)
                );
                player.sendSystemMessage(
                    Component.literal("")
                );
            }

            // Применяем бесконечную защиту от огня
            player.addEffect(new MobEffectInstance(
                MobEffects.FIRE_RESISTANCE,
                Integer.MAX_VALUE, // Бесконечная длительность
                0,
                false, // не ambient
                false, // не видимый
                true // показывать иконку
            ));

            // Дополнительные баффы полного сета
            player.addEffect(new MobEffectInstance(
                MobEffects.DAMAGE_BOOST, // Сила
                25,
                1, // Сила II
                true,
                false
            ));

            player.addEffect(new MobEffectInstance(
                MobEffects.DAMAGE_RESISTANCE, // Сопротивление урону
                25,
                1, // Сопротивление II
                true,
                false
            ));

            player.addEffect(new MobEffectInstance(
                MobEffects.REGENERATION, // Регенерация
                25,
                0, // Регенерация I
                true,
                false
            ));

            player.addEffect(new MobEffectInstance(
                MobEffects.MOVEMENT_SPEED, // Скорость
                25,
                0, // Скорость I
                true,
                false
            ));

            // Регенерация маны V (5 уровней по +7% = +35% к скорости регенерации маны)
            Holder<MobEffect> manaRegenHolder = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(ModEffects.MANA_REGENERATION.get());
            player.addEffect(new MobEffectInstance(
                manaRegenHolder,
                25,
                4, // Уровень V (0-4 = уровни I-V)
                true,
                false
            ));
        } else {
            // Если полный сет снят, снимаем все эффекты
            if (previousStatus) {
                // Снимаем все эффекты полного сета
                player.removeEffect(MobEffects.FIRE_RESISTANCE);
                player.removeEffect(MobEffects.DAMAGE_BOOST);
                player.removeEffect(MobEffects.DAMAGE_RESISTANCE);
                player.removeEffect(MobEffects.REGENERATION);
                player.removeEffect(MobEffects.MOVEMENT_SPEED);
                player.removeEffect(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(ModEffects.MANA_REGENERATION.get()));
                
                player.sendSystemMessage(
                    Component.literal("🔥 Огненный сет деактивирован.")
                        .withStyle(ChatFormatting.GRAY)
                );
            }
        }
    }

    private static void handleWaterSet(Player player, boolean hasFullSet, boolean previousStatus) {
        if (hasFullSet) {
            // Отправляем уведомление только при первом надевании полного сета
            if (!previousStatus) {
                player.sendSystemMessage(
                    Component.literal("")
                );
                player.sendSystemMessage(
                    Component.literal("════════════════════════════════")
                        .withStyle(ChatFormatting.DARK_BLUE, ChatFormatting.BOLD)
                );
                player.sendSystemMessage(
                    Component.literal("💧 ПОЛНЫЙ ВОДНЫЙ СЕТ АКТИВИРОВАН! 💧")
                        .withStyle(ChatFormatting.BLUE, ChatFormatting.BOLD)
                );
                player.sendSystemMessage(
                    Component.literal("Бонусы:")
                        .withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD)
                );
                player.sendSystemMessage(
                    Component.literal("  ∞ Бесконечное подводное дыхание")
                        .withStyle(ChatFormatting.AQUA)
                );
                player.sendSystemMessage(
                    Component.literal("  🐬 Грация дельфина II")
                        .withStyle(ChatFormatting.AQUA)
                );
                player.sendSystemMessage(
                    Component.literal("  🛡 Сопротивление II")
                        .withStyle(ChatFormatting.AQUA)
                );
                player.sendSystemMessage(
                    Component.literal("  ❤ Регенерация II")
                        .withStyle(ChatFormatting.AQUA)
                );
                player.sendSystemMessage(
                    Component.literal("  ➤ Скорость II")
                        .withStyle(ChatFormatting.AQUA)
                );
                player.sendSystemMessage(
                    Component.literal("  ✨ Регенерация маны V")
                        .withStyle(ChatFormatting.AQUA)
                );
                player.sendSystemMessage(
                    Component.literal("  💧 -40% стоимость водных заклинаний")
                        .withStyle(ChatFormatting.AQUA)
                );
                player.sendSystemMessage(
                    Component.literal("  ⏱ -15% время перезарядки")
                        .withStyle(ChatFormatting.AQUA)
                );
                player.sendSystemMessage(
                    Component.literal("  💎 +200 максимальная мана")
                        .withStyle(ChatFormatting.AQUA)
                );
                player.sendSystemMessage(
                    Component.literal("════════════════════════════════")
                        .withStyle(ChatFormatting.DARK_BLUE, ChatFormatting.BOLD)
                );
                player.sendSystemMessage(
                    Component.literal("")
                );
            }

            // Применяем бесконечное подводное дыхание
            player.addEffect(new MobEffectInstance(
                MobEffects.WATER_BREATHING,
                Integer.MAX_VALUE, // Бесконечная длительность
                0,
                false, // не ambient
                false, // не видимый
                true // показывать иконку
            ));

            // Дополнительные баффы полного водного сета
            player.addEffect(new MobEffectInstance(
                MobEffects.DOLPHINS_GRACE, // Грация дельфина
                25,
                1, // Грация дельфина II
                true,
                false
            ));

            player.addEffect(new MobEffectInstance(
                MobEffects.DAMAGE_RESISTANCE, // Сопротивление урону
                25,
                1, // Сопротивление II
                true,
                false
            ));

            player.addEffect(new MobEffectInstance(
                MobEffects.REGENERATION, // Регенерация
                25,
                1, // Регенерация II
                true,
                false
            ));

            player.addEffect(new MobEffectInstance(
                MobEffects.MOVEMENT_SPEED, // Скорость
                25,
                1, // Скорость II
                true,
                false
            ));

            // Регенерация маны V (5 уровней по +7% = +35% к скорости регенерации маны)
            Holder<MobEffect> manaRegenHolder = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(ModEffects.MANA_REGENERATION.get());
            player.addEffect(new MobEffectInstance(
                manaRegenHolder,
                25,
                4, // Уровень V (0-4 = уровни I-V)
                true,
                false
            ));
        } else {
            // Если полный сет снят, снимаем все эффекты
            if (previousStatus) {
                // Снимаем все эффекты полного водного сета
                player.removeEffect(MobEffects.WATER_BREATHING);
                player.removeEffect(MobEffects.DOLPHINS_GRACE);
                player.removeEffect(MobEffects.DAMAGE_RESISTANCE);
                player.removeEffect(MobEffects.REGENERATION);
                player.removeEffect(MobEffects.MOVEMENT_SPEED);
                player.removeEffect(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(ModEffects.MANA_REGENERATION.get()));
                
                player.sendSystemMessage(
                    Component.literal("💧 Водный сет деактивирован.")
                        .withStyle(ChatFormatting.GRAY)
                );
            }
        }
    }

    public static boolean hasFullFireSet(Player player) {
        int fireAccessoriesCount = countFireAccessories(player);
        return fireAccessoriesCount >= 14; // Все 14 огненных аксессуаров
    }
    
    public static boolean hasFullWaterSet(Player player) {
        int waterAccessoriesCount = countWaterAccessories(player);
        return waterAccessoriesCount >= 14; // Все 14 водных аксессуаров
    }
    
    public static int countFireAccessories(Player player) {
        Optional<AccessoriesCapability> capability = AccessoriesCapability.getOptionally(player);
        if (capability.isEmpty()) {
            return 0;
        }

        List<Item> fireAccessoriesList = getFireAccessories();
        int count = 0;
        
        // Перебираем все возможные слоты аксессуаров
        var containers = capability.get().getContainers();
        for (var containerEntry : containers.entrySet()) {
            var accessories = containerEntry.getValue().getAccessories();
            for (var stackPair : accessories) {
                ItemStack stack = stackPair.getSecond();
                if (!stack.isEmpty() && fireAccessoriesList.contains(stack.getItem())) {
                    count++;
                }
            }
        }

        return count;
    }
    
    public static int countWaterAccessories(Player player) {
        Optional<AccessoriesCapability> capability = AccessoriesCapability.getOptionally(player);
        if (capability.isEmpty()) {
            return 0;
        }

        List<Item> waterAccessoriesList = getWaterAccessories();
        int count = 0;
        
        // Перебираем все возможные слоты аксессуаров
        var containers = capability.get().getContainers();
        for (var containerEntry : containers.entrySet()) {
            var accessories = containerEntry.getValue().getAccessories();
            for (var stackPair : accessories) {
                ItemStack stack = stackPair.getSecond();
                if (!stack.isEmpty() && waterAccessoriesList.contains(stack.getItem())) {
                    count++;
                }
            }
        }

        return count;
    }
}

