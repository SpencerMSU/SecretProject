package com.example.examplemod.client;

import com.example.examplemod.ExampleMod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;

@EventBusSubscriber(modid = ExampleMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = net.neoforged.api.distmarker.Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onConfigLoad(ModConfigEvent event) {
        // no-op for now
    }
}