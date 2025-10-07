package com.example.examplemod.mana;

import com.example.examplemod.network.ManaSyncPayload;
import com.example.examplemod.network.NetworkHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public final class ManaApi {
    private ManaApi() {}

    public static IMana get(Player player) {
        return ManaProvider.get(player);
    }

    public static boolean tryConsume(Player player, int amount) {
        IMana mana = get(player);
        boolean ok = mana.consumeMana(amount);
        if (ok && player instanceof ServerPlayer sp) {
            sync(sp);
        }
        return ok;
    }

    public static void grant(ServerPlayer player, int amount) {
        IMana mana = get(player);
        mana.addMana(amount);
        sync(player);
    }

    public static void setMax(ServerPlayer player, int max) {
        IMana mana = get(player);
        mana.setMaxMana(max);
        mana.clamp();
        sync(player);
    }

    public static void setRegen(ServerPlayer player, int regenPerSecond) {
        IMana mana = get(player);
        mana.setRegenPerSecond(regenPerSecond);
        sync(player);
    }

    public static void sync(ServerPlayer player) {
        IMana mana = get(player);
        NetworkHandler.sendToPlayer(player, new ManaSyncPayload(
                mana.getCurrentMana(),
                mana.getMaxMana(),
                mana.getRegenPerSecond()
        ));
    }
}
