package com.example.examplemod.network;

import com.example.examplemod.mana.ManaProvider;
import com.example.examplemod.client.spell.ClientSpellState;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public final class ClientPayloadHandlers {
    private ClientPayloadHandlers() {}

    public static void handleManaSync(ManaSyncPayload payload) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;
        var mana = ManaProvider.get(player);
        mana.setMaxMana(payload.max());
        mana.setCurrentMana(payload.current());
        mana.setRegenPerSecond(payload.regen());
        mana.clamp();
    }
    
    public static void handlePlayerDataSync(PlayerDataSyncPayload payload) {
        ClientSpellState.loadPlayerData(
            payload.scrollOffset(),
            payload.hotbarSpells(),
            payload.selectedClass()
        );
    }
}
