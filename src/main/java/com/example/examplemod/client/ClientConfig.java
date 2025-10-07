package com.example.examplemod.client;

import com.example.examplemod.ExampleMod;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.fml.config.ModConfig;

public final class ClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue SHOW_MANA = BUILDER
            .comment("Show mana HUD")
            .define("hud.showMana", true);

    public static final ModConfigSpec.IntValue RIGHT_PADDING = BUILDER
            .comment("Right padding in pixels from screen edge")
            .defineInRange("hud.mana.rightPadding", 6, 0, 200);

    public static final ModConfigSpec.IntValue BAR_HEIGHT = BUILDER
            .comment("Mana bar height in pixels")
            .defineInRange("hud.mana.barHeight", 80, 20, 200);

    public static final ModConfigSpec.IntValue BAR_WIDTH = BUILDER
            .comment("Mana bar width in pixels")
            .defineInRange("hud.mana.barWidth", 8, 4, 24);

    public static final ModConfigSpec SPEC = BUILDER.build();

    private ClientConfig() {}
}
