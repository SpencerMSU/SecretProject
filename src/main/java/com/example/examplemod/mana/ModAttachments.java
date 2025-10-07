package com.example.examplemod.mana;

import com.example.examplemod.ExampleMod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

/**
 * Registry for data attachments including mana data.
 */
public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = 
        DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ExampleMod.MODID);

    public static final Supplier<AttachmentType<ManaData>> PLAYER_MANA = ATTACHMENT_TYPES.register(
        "player_mana", () -> AttachmentType.serializable(() -> 
            ManaDataBuilder.create()
                .setMaxMana(100.0f)
                .setRegenRate(0.1f)
                .setColor(0xFF00A0FF)
                .build()
        ).serializer(new PlayerManaProvider()).build()
    );
}
