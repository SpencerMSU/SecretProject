package com.example.examplemod.effects;

import com.example.examplemod.ExampleMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEffects {
    
    public static final DeferredRegister<MobEffect> EFFECTS = 
        DeferredRegister.create(Registries.MOB_EFFECT, ExampleMod.MODID);
    
    public static final Supplier<MobEffect> MANA_REGENERATION = EFFECTS.register(
        "mana_regeneration", 
        ManaRegenerationEffect::new
    );
    
    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}
