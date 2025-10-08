package com.example.examplemod.recipe;

import com.example.examplemod.ExampleMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = 
        DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, ExampleMod.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AccessoryUpgradeRecipe>> ACCESSORY_UPGRADE_SERIALIZER = 
        RECIPE_SERIALIZERS.register("accessory_upgrade", AccessoryUpgradeRecipe.Serializer::new);

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
