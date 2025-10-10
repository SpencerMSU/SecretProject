package com.example.examplemod.data;

import com.example.examplemod.spell.SpellEntry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Данные игрока для системы заклинаний
 * Сохраняются отдельно для каждого мира
 */
public class PlayerSpellData {
    private final UUID playerId;
    private final ResourceKey<Level> dimension;
    private int scrollOffset = 0;
    private final List<String> hotbarSpells = new ArrayList<>();
    private String selectedClass = "FIRE";
    
    public PlayerSpellData(UUID playerId, ResourceKey<Level> dimension) {
        this.playerId = playerId;
        this.dimension = dimension;
    }
    
    public UUID getPlayerId() {
        return playerId;
    }
    
    public ResourceKey<Level> getDimension() {
        return dimension;
    }
    
    public int getScrollOffset() {
        return scrollOffset;
    }
    
    public void setScrollOffset(int scrollOffset) {
        this.scrollOffset = scrollOffset;
    }
    
    public List<String> getHotbarSpells() {
        return hotbarSpells;
    }
    
    public void setHotbarSpells(List<String> spells) {
        this.hotbarSpells.clear();
        this.hotbarSpells.addAll(spells);
    }
    
    public String getSelectedClass() {
        return selectedClass;
    }
    
    public void setSelectedClass(String selectedClass) {
        this.selectedClass = selectedClass;
    }
    
    public void saveNBTData(CompoundTag tag) {
        tag.putUUID("PlayerId", playerId);
        tag.putString("Dimension", dimension.location().toString());
        tag.putInt("ScrollOffset", scrollOffset);
        tag.putString("SelectedClass", selectedClass);
        
        ListTag hotbarTag = new ListTag();
        for (String spellId : hotbarSpells) {
            hotbarTag.add(StringTag.valueOf(spellId));
        }
        tag.put("HotbarSpells", hotbarTag);
    }
    
    public void loadNBTData(CompoundTag tag) {
        if (tag.contains("ScrollOffset")) {
            this.scrollOffset = tag.getInt("ScrollOffset");
        }
        if (tag.contains("SelectedClass")) {
            this.selectedClass = tag.getString("SelectedClass");
        }
        
        this.hotbarSpells.clear();
        if (tag.contains("HotbarSpells", Tag.TAG_LIST)) {
            ListTag hotbarTag = tag.getList("HotbarSpells", Tag.TAG_STRING);
            for (int i = 0; i < hotbarTag.size(); i++) {
                String spellId = hotbarTag.getString(i);
                if (!spellId.isEmpty()) {
                    this.hotbarSpells.add(spellId);
                }
            }
        }
    }
    
    public static PlayerSpellData fromNBT(CompoundTag tag) {
        UUID playerId = tag.getUUID("PlayerId");
        String dimensionStr = tag.getString("Dimension");
        ResourceKey<Level> dimension = Level.OVERWORLD; // Default fallback
        
        try {
            String[] parts = dimensionStr.split(":");
            if (parts.length == 2) {
                dimension = ResourceKey.create(net.minecraft.core.registries.Registries.DIMENSION, 
                    net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(parts[0], parts[1]));
            }
        } catch (Exception e) {
            // Use default dimension
        }
        
        PlayerSpellData data = new PlayerSpellData(playerId, dimension);
        data.loadNBTData(tag);
        return data;
    }
}
