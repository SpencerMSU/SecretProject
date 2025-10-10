package com.example.examplemod.client.spell;

import com.example.examplemod.mana.ManaProvider;
import com.example.examplemod.network.NetworkHandler;
import com.example.examplemod.network.SpellCastPayload;
import com.example.examplemod.spell.SpellEntry;
import net.minecraft.client.Minecraft;

public final class ClientSpellCaster {
    private ClientSpellCaster() {}

    public static boolean tryCastActive(Minecraft mc) {
        if (mc.player == null) return false;
        
        // Debug: Check if holding wand
        boolean holdingWand = ClientSpellState.isHoldingWand(mc);
        if (!holdingWand) {
            // Debug message
            mc.player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§cНужно держать палочку для кастования заклинаний!"));
            return false;
        }
        
        int index = ClientSpellState.getActiveIndex();
        SpellEntry entry = ClientSpellState.getHotbarEntry(index);
        if (entry == null) {
            mc.player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§cНет заклинания в активном слоте!"));
            return false;
        }
        
        // Check if player has enough mana (client-side check for responsiveness)
        var mana = ManaProvider.get(mc.player);
        if (mana.getCurrentMana() < entry.manaCost()) {
            // Not enough mana - don't cast
            mc.player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§cНедостаточно маны! Нужно: " + entry.manaCost() + ", есть: " + mana.getCurrentMana()));
            return false;
        }
        
        // Send packet to server to actually consume mana
        NetworkHandler.sendToServer(new SpellCastPayload(entry.id(), entry.manaCost()));
        
        // Optimistically update client-side mana for visual feedback
        mana.setCurrentMana(mana.getCurrentMana() - entry.manaCost());
        
        // Success message
        mc.player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§aКастуется заклинание: " + entry.displayName().getString()));
        
        return true;
    }
}
