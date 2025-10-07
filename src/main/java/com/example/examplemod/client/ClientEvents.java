package com.example.examplemod.client;

import com.example.examplemod.client.hud.ManaHudOverlay;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

@EventBusSubscriber(value = net.neoforged.api.distmarker.Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
        // Using RenderGuiEvent directly in overlay, so nothing to add here for now
    }

    @SubscribeEvent
    public static void onConfigLoad(ModConfigEvent event) {
        // no-op for now
    }
}
