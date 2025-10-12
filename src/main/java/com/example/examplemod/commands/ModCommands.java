package com.example.examplemod.commands;

import com.example.examplemod.ExampleMod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = ExampleMod.MODID)
public class ModCommands {
    
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        ManaCommand.register(event.getDispatcher());
        CheckModsCommand.register(event.getDispatcher());
        ModListCommand.register(event.getDispatcher());
    }
}
