package com.example.examplemod.mana;

import com.example.examplemod.ExampleMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;

/**
 * Attachment for storing mana data on entities (primarily players).
 * Uses NeoForge's data attachment system for clean capability management.
 */
public class PlayerManaProvider implements IAttachmentSerializer<CompoundTag, ManaData> {
    
    @Override
    public ManaData read(IAttachmentHolder holder, CompoundTag tag, HolderLookup.Provider provider) {
        ManaData data = ManaDataBuilder.create()
            .setMaxMana(100.0f)
            .setRegenRate(0.1f)
            .setColor(0xFF00A0FF)
            .build();
        data.loadNBTData(tag);
        return data;
    }

    @Override
    public CompoundTag write(ManaData attachment, HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        attachment.saveNBTData(tag);
        return tag;
    }
}
