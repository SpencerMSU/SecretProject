package com.example.examplemod.items;

import com.example.examplemod.mana.ManaHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Example item that demonstrates mana consumption.
 * Right-clicking consumes 20 mana.
 */
public class ManaTestItem extends Item {
    private static final float MANA_COST = 20.0f;
    
    public ManaTestItem(Properties properties) {
        super(properties);
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            if (ManaHelper.consumeMana(player, MANA_COST)) {
                player.sendSystemMessage(Component.literal("Used 20 mana! Current: " + 
                    (int)ManaHelper.getCurrentMana(player) + "/" + 
                    (int)ManaHelper.getMaxMana(player)));
                return InteractionResultHolder.success(player.getItemInHand(hand));
            } else {
                player.sendSystemMessage(Component.literal("Not enough mana! Need: " + MANA_COST));
                return InteractionResultHolder.fail(player.getItemInHand(hand));
            }
        }
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }
}
