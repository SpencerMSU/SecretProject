package com.example.examplemod.client;

import com.example.examplemod.client.hud.AccessorySetHudOverlay;
import com.example.examplemod.client.hud.ManaHudOverlay;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

@EventBusSubscriber(value = net.neoforged.api.distmarker.Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
        // Регистрируем HUD для наборов аксессуаров
        event.registerAboveAll(
            ResourceLocation.fromNamespaceAndPath("examplemod", "accessory_set_hud"),
            new AccessorySetHudOverlay()
        );
    }

    @SubscribeEvent
    public static void onConfigLoad(ModConfigEvent event) {
        // no-op for now
    }
}
