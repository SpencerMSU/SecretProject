package com.example.examplemod.client.spell;

import com.example.examplemod.mana.ManaProvider;
import com.example.examplemod.network.NetworkHandler;
import com.example.examplemod.network.SpellCastPayload;
import com.example.examplemod.spell.SpellEntry;
import net.minecraft.client.Minecraft;

public final class ClientSpellCaster {
    private ClientSpellCaster() {}

    public static boolean tryCastActive(Minecraft mc) {
        if (!ClientSpellState.isHoldingWand(mc)) return false;
        int index = ClientSpellState.getActiveIndex();
        SpellEntry entry = ClientSpellState.getHotbarEntry(index);
        if (entry == null) return false;
        
        // Check if player has enough mana (client-side check for responsiveness)
        if (mc.player != null) {
            var mana = ManaProvider.get(mc.player);
            if (mana.getCurrentMana() < entry.manaCost()) {
                // Not enough mana - don't cast
                return false;
            }
            
            // Send packet to server to actually consume mana
            NetworkHandler.sendToServer(new SpellCastPayload(entry.id(), entry.manaCost()));
            
            // Optimistically update client-side mana for visual feedback
            mana.setCurrentMana(mana.getCurrentMana() - entry.manaCost());
        }
        return true;
    }
}
