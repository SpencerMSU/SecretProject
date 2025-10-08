package com.example.examplemod.recipe;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.accessory.BaseAccessoryItem;
import com.example.examplemod.items.ModItems;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

/**
 * Рецепт улучшения аксессуаров на верстаке
 * Аксессуар + Камень заточки = Аксессуар +1 уровень
 */
public class AccessoryUpgradeRecipe implements CraftingRecipe {
    
    @Override
    public boolean matches(CraftingInput input, Level level) {
        ItemStack accessory = ItemStack.EMPTY;
        ItemStack stone = ItemStack.EMPTY;
        int itemCount = 0;

        // Проверяем что в крафте ровно 2 предмета: аксессуар и камень
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty()) {
                itemCount++;
                
                if (stack.getItem() instanceof BaseAccessoryItem) {
                    if (!accessory.isEmpty()) {
                        return false; // Больше одного аксессуара
                    }
                    accessory = stack;
                } else if (stack.is(ModItems.ENCHANTMENT_STONE.get())) {
                    if (!stone.isEmpty()) {
                        return false; // Больше одного камня
                    }
                    stone = stack;
                }
            }
        }

        // Должно быть ровно 2 предмета: аксессуар и камень
        if (itemCount != 2 || accessory.isEmpty() || stone.isEmpty()) {
            return false;
        }

        // Проверяем, можно ли улучшить аксессуар
        return BaseAccessoryItem.canUpgrade(accessory);
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack accessory = ItemStack.EMPTY;

        // Находим аксессуар
        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.getItem() instanceof BaseAccessoryItem) {
                accessory = stack.copy();
                break;
            }
        }

        if (accessory.isEmpty()) {
            return ItemStack.EMPTY;
        }

        // Улучшаем копию аксессуара
        BaseAccessoryItem.upgrade(accessory);
        return accessory;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(input.size(), ItemStack.EMPTY);
        // Камень заточки расходуется полностью, ничего не остается
        return remaining;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2; // Нужно минимум 2 слота
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return ItemStack.EMPTY; // Динамический результат
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.ACCESSORY_UPGRADE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }

    @Override
    public boolean isSpecial() {
        return true; // Специальный рецепт с динамическим результатом
    }

    @Override
    public net.minecraft.world.item.crafting.CraftingBookCategory category() {
        return net.minecraft.world.item.crafting.CraftingBookCategory.MISC;
    }

    public static class Serializer implements RecipeSerializer<AccessoryUpgradeRecipe> {
        private static final MapCodec<AccessoryUpgradeRecipe> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.point(new AccessoryUpgradeRecipe())
        );

        private static final StreamCodec<RegistryFriendlyByteBuf, AccessoryUpgradeRecipe> STREAM_CODEC = 
            StreamCodec.unit(new AccessoryUpgradeRecipe());

        @Override
        public MapCodec<AccessoryUpgradeRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AccessoryUpgradeRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
