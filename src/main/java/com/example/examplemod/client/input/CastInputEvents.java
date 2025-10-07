package com.example.examplemod.client.input;

import com.example.examplemod.client.spell.ClientSpellCaster;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;

@EventBusSubscriber(value = net.neoforged.api.distmarker.Dist.CLIENT)
public class CastInputEvents {
    @SubscribeEvent
    public static void onUseKey(InputEvent.InteractionKeyMappingTriggered event) {
        if (event.isUseItem()) {
            Minecraft mc = Minecraft.getInstance();
            boolean cast = ClientSpellCaster.tryCastActive(mc);
            if (cast) {
                event.setSwingHand(true);
                event.setCanceled(true);
            }
        }
    }
}
