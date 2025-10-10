package com.example.examplemod.data;

import com.example.examplemod.ExampleMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Менеджер для сохранения и загрузки данных игроков
 * Данные сохраняются отдельно для каждого мира
 */
public class PlayerDataManager {
    private static final String DATA_FILE = "player_spell_data.dat";
    private static final Map<String, Map<UUID, PlayerSpellData>> worldData = new HashMap<>();
    
    /**
     * Получить данные игрока для конкретного мира
     */
    public static PlayerSpellData getPlayerData(ServerPlayer player) {
        String worldKey = getWorldKey((ServerLevel) player.level());
        UUID playerId = player.getUUID();
        
        return worldData.computeIfAbsent(worldKey, k -> new HashMap<>())
                       .computeIfAbsent(playerId, k -> new PlayerSpellData(playerId, player.level().dimension()));
    }
    
    /**
     * Сохранить данные игрока
     */
    public static void savePlayerData(ServerPlayer player) {
        PlayerSpellData data = getPlayerData(player);
        saveWorldData((ServerLevel) player.level());
    }
    
    /**
     * Загрузить данные игрока при входе в игру
     */
    public static void loadPlayerData(ServerPlayer player) {
        loadWorldData((ServerLevel) player.level());
    }
    
    private static String getWorldKey(ServerLevel level) {
        return level.dimension().location().toString();
    }
    
    private static File getDataFile(ServerLevel level) {
        Path worldPath = level.getServer().getWorldPath(LevelResource.ROOT);
        File dataDir = worldPath.resolve("data").toFile();
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        return new File(dataDir, DATA_FILE);
    }
    
    private static void saveWorldData(ServerLevel level) {
        String worldKey = getWorldKey(level);
        try {
            Map<UUID, PlayerSpellData> players = worldData.get(worldKey);
            if (players == null || players.isEmpty()) return;
            
            File dataFile = getDataFile(level);
            CompoundTag rootTag = new CompoundTag();
            ListTag playersTag = new ListTag();
            
            for (PlayerSpellData data : players.values()) {
                CompoundTag playerTag = new CompoundTag();
                data.saveNBTData(playerTag);
                playersTag.add(playerTag);
            }
            
            rootTag.put("Players", playersTag);
            rootTag.putString("WorldKey", worldKey);
            
            // Используем правильное сохранение NBT
            try (FileOutputStream fos = new FileOutputStream(dataFile);
                 DataOutputStream dos = new DataOutputStream(fos)) {
                rootTag.write(dos);
            }
            
        } catch (IOException e) {
            System.err.println("Failed to save player data for world: " + worldKey + " - " + e.getMessage());
        }
    }
    
    private static void loadWorldData(ServerLevel level) {
        try {
            String worldKey = getWorldKey(level);
            File dataFile = getDataFile(level);
            
            if (!dataFile.exists()) {
                worldData.put(worldKey, new HashMap<>());
                return;
            }
            
            // Используем правильное чтение NBT
            CompoundTag rootTag;
            try (FileInputStream fis = new FileInputStream(dataFile);
                 DataInputStream dis = new DataInputStream(fis)) {
                rootTag = NbtIo.read(dis);
            }
            
            if (rootTag.contains("Players", 9)) { // TAG_LIST
                ListTag playersTag = rootTag.getList("Players", 10); // TAG_COMPOUND
                Map<UUID, PlayerSpellData> players = new HashMap<>();
                
                for (int i = 0; i < playersTag.size(); i++) {
                    CompoundTag playerTag = playersTag.getCompound(i);
                    PlayerSpellData data = PlayerSpellData.fromNBT(playerTag);
                    players.put(data.getPlayerId(), data);
                }
                
                worldData.put(worldKey, players);
            }
            
        } catch (Exception e) {
            System.err.println("Failed to load player data for world: " + getWorldKey(level) + " - " + e.getMessage());
            worldData.put(getWorldKey(level), new HashMap<>());
        }
    }
    
    /**
     * Очистить данные при выходе из мира
     */
    public static void clearWorldData(ServerLevel level) {
        String worldKey = getWorldKey(level);
        worldData.remove(worldKey);
    }
}
