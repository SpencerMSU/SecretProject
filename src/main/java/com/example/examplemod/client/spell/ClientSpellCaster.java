package com.example.examplemod.client.spell;

import com.example.examplemod.mana.ManaApi;
import com.example.examplemod.spell.SpellEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public final class ClientSpellCaster {
    private ClientSpellCaster() {}

    public static boolean tryCastActive(Minecraft mc) {
        if (!ClientSpellState.isHoldingWand(mc)) return false;
        int index = ClientSpellState.getActiveIndex();
        SpellEntry entry = ClientSpellState.getHotbarEntry(index);
        if (entry == null) return false;
        // Test: just print and consume a small amount of mana client-side
        if (mc.player != null) {
            mc.player.displayClientMessage(Component.literal("Casting spell: " + entry.displayName().getString()), true);
        }
        return true;
    }
}
