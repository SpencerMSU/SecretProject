package com.example.examplemod;

import com.example.examplemod.accessory.AccessoryConfig;
import com.example.examplemod.accessory.ModDataComponents;
import com.example.examplemod.blocks.ModBlocks;
import com.example.examplemod.effects.ModEffects;
import com.example.examplemod.items.CreativeTabHandler;
import com.example.examplemod.items.ModItems;
import com.example.examplemod.mana.ManaComponent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import com.example.examplemod.network.NetworkHandler;

@Mod(ExampleMod.MODID)
public class ExampleMod {
    public static final String MODID = "examplemod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ExampleMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(NetworkHandler::register);
        NeoForge.EVENT_BUS.register(this);

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModEffects.register(modEventBus);
        ManaComponent.register(modEventBus);
        CreativeTabHandler.register(modEventBus);
        ModDataComponents.register(modEventBus);
        com.example.examplemod.recipe.ModRecipes.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        modContainer.registerConfig(ModConfig.Type.COMMON, AccessoryConfig.SPEC, "examplemod-accessories.toml");
        // Client config (HUD etc.)
        modContainer.registerConfig(ModConfig.Type.CLIENT, com.example.examplemod.client.ClientConfig.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Команды будут зарегистрированы через события
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }

        @SubscribeEvent
        public static void onRegisterKeyMappings(net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent event) {
            event.register(com.example.examplemod.client.input.KeyBindings.OPEN_SPELLBOOK);
        }
    }

    
}
