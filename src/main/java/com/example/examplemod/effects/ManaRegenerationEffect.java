package com.example.examplemod.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ManaRegenerationEffect extends MobEffect {
    
    public ManaRegenerationEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00BFFF); // Голубой цвет для маны
    }
    
    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        // Эффект регенерации маны теперь обрабатывается в ManaSystem
        // Этот метод нужен только для отображения эффекта
        return true;
    }
}
