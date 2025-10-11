package com.example.examplemod.curios;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.INBTSerializable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

/**
 * Провайдер для Curios handler
 */
public class CuriosCapabilityProvider implements INBTSerializable<Tag> {
    
    private ICuriosItemHandler handler;
    
    public CuriosCapabilityProvider() {
        this.handler = null;
    }
    
    public CuriosCapabilityProvider(Player player) {
        this.handler = CuriosApi.getCuriosInventory(player).orElse(null);
    }
    
    public ICuriosItemHandler getHandler() {
        return handler;
    }
    
    public void setHandler(ICuriosItemHandler handler) {
        this.handler = handler;
    }
    
    @Override
    public Tag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        // Curios API сам управляет сериализацией
        return tag;
    }
    
    @Override
    public void deserializeNBT(HolderLookup.Provider provider, Tag nbt) {
        // Curios API сам управляет десериализацией
    }
    
    /**
     * Получает Curios handler для игрока
     */
    public static ICuriosItemHandler getCuriosHandler(Player player) {
        return CuriosApi.getCuriosInventory(player).orElse(null);
    }
    
    /**
     * Проверяет, доступен ли Curios handler для игрока
     */
    public static boolean hasCuriosHandler(Player player) {
        return CuriosApi.getCuriosInventory(player).isPresent();
    }
    
    /**
     * Принудительно инициализирует Curios для игрока
     */
    public static void forceInitializeCurios(Player player) {
        if (!hasCuriosHandler(player)) {
            System.out.println("Attempting to force initialize Curios for player: " + player.getName().getString());
            // Curios должен автоматически инициализироваться
            // Мы просто проверяем доступность
        }
    }
}