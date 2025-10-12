package com.example.examplemod.mana;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.accessory.SetBonusHandler;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = ExampleMod.MODID)
public class ManaSystem {
    
    private static final int BASE_MANA = 100;
    private static final int FULL_SET_MANA_BONUS = 200;
    private static final float FULL_SET_DISCOUNT = 0.4f; // 40% скидка
    private static final float FULL_SET_COOLDOWN_REDUCTION = 0.15f; // 15% уменьшение КД
    private static final float MANA_REGEN_BASE_RATE = 1.0f; // 1 мана в секунду
    
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        
        // Обновляем ману каждую секунду (20 тиков)
        if (player.level().getGameTime() % 20 != 0) {
            return;
        }
        
        // Получаем текущие данные маны из NBT игрока
        ManaData currentMana = getManaData(player);
        
        // Проверяем, есть ли полный огненный сет
        boolean hasFullFireSet = SetBonusHandler.hasFullFireSet(player);
        
        // Вычисляем новые параметры маны
        int newMaxMana = BASE_MANA + (hasFullFireSet ? FULL_SET_MANA_BONUS : 0);
        float newRegenRate = MANA_REGEN_BASE_RATE;
        float newSpellDiscount = hasFullFireSet ? FULL_SET_DISCOUNT : 0.0f;
        float newCooldownReduction = hasFullFireSet ? FULL_SET_COOLDOWN_REDUCTION : 0.0f;
        
        // Применяем бонусы от эффекта регенерации маны
        Holder<net.minecraft.world.effect.MobEffect> manaRegenHolder = 
            BuiltInRegistries.MOB_EFFECT.wrapAsHolder(com.example.examplemod.effects.ModEffects.MANA_REGENERATION.get());
        if (player.hasEffect(manaRegenHolder)) {
            int amplifier = player.getEffect(manaRegenHolder).getAmplifier();
            // Каждый уровень дает +7% к скорости регенерации (0-4 = уровни I-V)
            float regenBonus = 1.0f + (amplifier + 1) * 0.07f;
            newRegenRate *= regenBonus;
        }
        
        // Регенерируем ману
        int newCurrentMana = Math.min(
            currentMana.currentMana() + (int) newRegenRate,
            newMaxMana
        );
        
        // Создаем новые данные маны
        ManaData newManaData = new ManaData(
            newCurrentMana,
            newMaxMana,
            newRegenRate,
            newSpellDiscount,
            newCooldownReduction
        );
        
        // Обновляем данные маны игрока в NBT
        setManaData(player, newManaData);
    }
    
    private static ManaData getManaData(Player player) {
        CompoundTag persistentData = player.getPersistentData();
        CompoundTag manaTag = persistentData.getCompound("examplemod.mana");
        
        if (manaTag.isEmpty()) {
            return ManaData.createDefault();
        }
        
        return ManaData.CODEC.parse(net.minecraft.nbt.NbtOps.INSTANCE, manaTag)
            .result()
            .orElse(ManaData.createDefault());
    }
    
    private static void setManaData(Player player, ManaData manaData) {
        CompoundTag persistentData = player.getPersistentData();
        CompoundTag manaTag = ManaData.CODEC.encodeStart(net.minecraft.nbt.NbtOps.INSTANCE, manaData)
            .result()
            .map(tag -> (CompoundTag) tag)
            .orElse(new CompoundTag());
        
        persistentData.put("examplemod.mana", manaTag);
    }
    
    public static boolean canCastSpell(Player player, int baseManaCost) {
        ManaData manaData = getManaData(player);
        
        // Применяем скидку на огненные заклинания
        int actualCost = (int) (baseManaCost * (1.0f - manaData.spellDiscount()));
        
        return manaData.canCast(actualCost);
    }
    
    public static void consumeMana(Player player, int baseManaCost) {
        ManaData manaData = getManaData(player);
        
        // Применяем скидку на огненные заклинания
        int actualCost = (int) (baseManaCost * (1.0f - manaData.spellDiscount()));
        
        ManaData newManaData = manaData.consumeMana(actualCost);
        setManaData(player, newManaData);
    }
    
    public static float getCooldownMultiplier(Player player) {
        ManaData manaData = getManaData(player);
        return 1.0f - manaData.cooldownReduction();
    }
    
    public static int getCurrentMana(Player player) {
        ManaData manaData = getManaData(player);
        return manaData.currentMana();
    }
    
    public static int getMaxMana(Player player) {
        ManaData manaData = getManaData(player);
        return manaData.maxMana();
    }
}
