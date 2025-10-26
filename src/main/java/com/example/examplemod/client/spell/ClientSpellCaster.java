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
        
        // Проверяем, держит ли игрок палочку
        boolean holdingWand = ClientSpellState.isHoldingWand(mc);
        if (!holdingWand) {
            // Без вывода сообщений в чат
            return false;
        }
        
        int index = ClientSpellState.getActiveIndex();
        SpellEntry entry = ClientSpellState.getHotbarEntry(index);
        if (entry == null) {
            // Без вывода сообщений в чат
            return false;
        }
        
        // Проверка маны на клиенте для отзывчивости UI
        var mana = ManaProvider.get(mc.player);
        if (mana.getCurrentMana() < entry.manaCost()) {
            // Без вывода сообщений в чат
            return false;
        }
        
        // Отправляем пакет на сервер для фактического расхода маны
        NetworkHandler.sendToServer(new SpellCastPayload(entry.id(), entry.manaCost()));
        
        // Оптимистично обновляем клиентскую ману
        mana.setCurrentMana(mana.getCurrentMana() - entry.manaCost());
        
        // Без сообщений об успехе в чат
        return true;
    }
}