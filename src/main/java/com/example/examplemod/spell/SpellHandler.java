package com.example.examplemod.spell;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public final class SpellHandler {
    private SpellHandler() {}

    public static void castSpell(ServerPlayer player, String spellId, int damage) {
        if (player.level() instanceof ServerLevel level) {
            // Get spell class from ID
            boolean isFireSpell = isFireSpell(spellId);
            boolean isWaterSpell = isWaterSpell(spellId);
            
            // Execute spell effect based on ID
            switch (spellId) {
                // FIRE SPELLS
                case "spark" -> castSpark(player, level, damage);
                case "ember" -> castEmber(player, level, damage);
                case "fire_bolt" -> castFireBolt(player, level, damage);
                case "flame_burst" -> castFlameBurst(player, level, damage);
                case "magma_spike" -> castMagmaSpike(player, level, damage);
                case "blaze_rush" -> castBlazeRush(player, level, damage);
                case "inferno" -> castInferno(player, level, damage);
                case "hellfire" -> castHellfire(player, level, damage);
                case "phoenix_rebirth" -> castPhoenixRebirth(player, level, damage);
                case "solar_eclipse" -> castSolarEclipse(player, level, damage);
                case "soul_fire_ritual" -> castSoulFireRitual(player, level, damage);
                case "phoenix_necromancy" -> castPhoenixNecromancy(player, level, damage);
                
                // WATER SPELLS
                case "water_splash" -> castWaterSplash(player, level, damage);
                case "ice_shard" -> castIceShard(player, level, damage);
                case "aqua_jet" -> castAquaJet(player, level, damage);
                case "frost_nova" -> castFrostNova(player, level, damage);
                case "tidal_wave" -> castTidalWave(player, level, damage);
                case "blizzard" -> castBlizzard(player, level, damage);
                case "crystal_wave" -> castCrystalWave(player, level, damage);
                case "poseidon_wrath" -> castPoseidonWrath(player, level, damage);
                case "glacial_prison" -> castGlacialPrison(player, level, damage);
                case "deep_freeze" -> castDeepFreeze(player, level, damage);
                case "ocean_guardian" -> castOceanGuardian(player, level, damage);
                case "absolute_zero" -> castAbsoluteZero(player, level, damage);
            }
        }
    }

    private static boolean isFireSpell(String spellId) {
        return spellId.equals("spark") || spellId.equals("ember") || spellId.equals("fire_bolt") || 
               spellId.equals("flame_burst") || spellId.equals("magma_spike") || spellId.equals("blaze_rush") ||
               spellId.equals("inferno") || spellId.equals("hellfire") || spellId.equals("phoenix_rebirth") ||
               spellId.equals("solar_eclipse") || spellId.equals("soul_fire_ritual") || 
               spellId.equals("phoenix_necromancy");
    }

    private static boolean isWaterSpell(String spellId) {
        return !isFireSpell(spellId);
    }

    // ===== FIRE SPELLS =====
    
    private static void castSpark(ServerPlayer player, ServerLevel level, int damage) {
        // Простое заклинание: поджигает ближайших врагов
        damageNearbyEntities(player, level, 3.0, damage, true); // Поджигает врагов
        spawnParticles(level, player.position(), ParticleTypes.FLAME, 15);
        level.playSound(null, player.blockPosition(), SoundEvents.FIRE_AMBIENT, SoundSource.PLAYERS, 0.8f, 1.0f);
    }

    private static void castEmber(ServerPlayer player, ServerLevel level, int damage) {
        // Заклинание с эффектом: поджигает и наносит урон по области
        damageNearbyEntities(player, level, 4.0, damage, true);
        // Поджигает блоки вокруг игрока
        setFireToNearbyBlocks(player, level, 3.0);
        spawnParticles(level, player.position(), ParticleTypes.FLAME, 20);
        level.playSound(null, player.blockPosition(), SoundEvents.CAMPFIRE_CRACKLE, SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    private static void castFireBolt(ServerPlayer player, ServerLevel level, int damage) {
        // Дальнобойное заклинание: летит по прямой и взрывается
        Vec3 lookVec = player.getLookAngle();
        Vec3 targetPos = player.position().add(lookVec.scale(12)); // Увеличиваем дальность
        damageInLine(player, level, player.position(), targetPos, 1.5, damage, true);
        // Взрыв в конечной точке
        damageNearbyEntitiesAtPos(level, targetPos, 3.0, damage / 2, true);
        spawnParticlesInLine(level, player.position(), targetPos, ParticleTypes.FLAME, 30);
        spawnParticles(level, targetPos, ParticleTypes.EXPLOSION, 10);
        level.playSound(null, player.blockPosition(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1.2f, 1.0f);
    }

    private static void castFlameBurst(ServerPlayer player, ServerLevel level, int damage) {
        // Мощное заклинание: взрыв с поджогом и отталкиванием
        damageNearbyEntities(player, level, 6.0, damage, true);
        knockbackNearbyEntitiesNew(player, level, 6.0, 1.5); // Отталкивание
        spawnParticles(level, player.position(), ParticleTypes.FLAME, 60);
        spawnParticles(level, player.position(), ParticleTypes.LAVA, 30);
        spawnParticles(level, player.position(), ParticleTypes.EXPLOSION, 15);
        level.playSound(null, player.blockPosition(), SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 1.5f, 1.0f);
    }

    private static void castMagmaSpike(ServerPlayer player, ServerLevel level, int damage) {
        // Заклинание с контролем: замедляет и поджигает
        damageNearbyEntities(player, level, 5.0, damage, true);
        applyEffectToNearbyEntities(player, level, 5.0, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
        applyEffectToNearbyEntities(player, level, 5.0, new MobEffectInstance(MobEffects.WEAKNESS, 60, 1));
        spawnParticles(level, player.position(), ParticleTypes.LAVA, 50);
        spawnParticles(level, player.position(), ParticleTypes.FLAME, 30);
        level.playSound(null, player.blockPosition(), SoundEvents.LAVA_POP, SoundSource.PLAYERS, 2.0f, 0.8f);
    }

    private static void castBlazeRush(ServerPlayer player, ServerLevel level, int damage) {
        damageNearbyEntities(player, level, 7.0, damage, true);
        spawnParticles(level, player.position(), ParticleTypes.FLAME, 80);
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 1));
        level.playSound(null, player.blockPosition(), SoundEvents.BLAZE_AMBIENT, SoundSource.PLAYERS, 1.8f, 1.2f);
    }

    private static void castInferno(ServerPlayer player, ServerLevel level, int damage) {
        damageNearbyEntities(player, level, 8.0, damage, true);
        spawnParticles(level, player.position(), ParticleTypes.FLAME, 100);
        spawnParticles(level, player.position(), ParticleTypes.LAVA, 50);
        applyEffectToNearbyEntities(player, level, 8.0, new MobEffectInstance(MobEffects.WITHER, 80, 1));
        level.playSound(null, player.blockPosition(), SoundEvents.LAVA_EXTINGUISH, SoundSource.PLAYERS, 2.5f, 0.7f);
    }

    private static void castHellfire(ServerPlayer player, ServerLevel level, int damage) {
        damageNearbyEntities(player, level, 9.0, damage, true);
        spawnParticles(level, player.position(), ParticleTypes.SOUL_FIRE_FLAME, 120);
        applyEffectToNearbyEntities(player, level, 9.0, new MobEffectInstance(MobEffects.WITHER, 100, 2));
        level.playSound(null, player.blockPosition(), SoundEvents.WITHER_SHOOT, SoundSource.PLAYERS, 2.2f, 0.8f);
    }

    private static void castPhoenixRebirth(ServerPlayer player, ServerLevel level, int damage) {
        damageNearbyEntities(player, level, 12.0, damage, true);
        spawnParticles(level, player.position(), ParticleTypes.FLAME, 200);
        player.heal(10.0f);
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 2));
        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0));
        level.playSound(null, player.blockPosition(), SoundEvents.ENDER_DRAGON_FLAP, SoundSource.PLAYERS, 3.0f, 1.5f);
    }

    private static void castSolarEclipse(ServerPlayer player, ServerLevel level, int damage) {
        damageNearbyEntities(player, level, 15.0, damage, true);
        spawnParticles(level, player.position(), ParticleTypes.FLAME, 300);
        spawnParticles(level, player.position(), ParticleTypes.END_ROD, 100);
        applyEffectToNearbyEntities(player, level, 15.0, new MobEffectInstance(MobEffects.WITHER, 200, 3));
        applyEffectToNearbyEntities(player, level, 15.0, new MobEffectInstance(MobEffects.WEAKNESS, 200, 2));
        level.playSound(null, player.blockPosition(), SoundEvents.WITHER_DEATH, SoundSource.PLAYERS, 4.0f, 0.5f);
    }

    // ===== WATER SPELLS =====
    
    private static void castWaterSplash(ServerPlayer player, ServerLevel level, int damage) {
        damageNearbyEntities(player, level, 3.0, damage, false);
        knockbackNearbyEntities(player, level, 3.0, 0.5);
        spawnParticles(level, player.position(), ParticleTypes.SPLASH, 15);
        level.playSound(null, player.blockPosition(), SoundEvents.PLAYER_SPLASH, SoundSource.PLAYERS, 0.8f, 1.0f);
    }

    private static void castIceShard(ServerPlayer player, ServerLevel level, int damage) {
        Vec3 lookVec = player.getLookAngle();
        Vec3 targetPos = player.position().add(lookVec.scale(8));
        damageInLine(player, level, player.position(), targetPos, 1.0, damage, false);
        spawnParticlesInLine(level, player.position(), targetPos, ParticleTypes.SNOWFLAKE, 15);
        level.playSound(null, player.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 0.9f, 1.2f);
    }

    private static void castAquaJet(ServerPlayer player, ServerLevel level, int damage) {
        Vec3 lookVec = player.getLookAngle();
        Vec3 targetPos = player.position().add(lookVec.scale(10));
        damageInLine(player, level, player.position(), targetPos, 2.0, damage, false);
        knockbackInLine(player, level, player.position(), targetPos, 2.0, 0.8);
        spawnParticlesInLine(level, player.position(), targetPos, ParticleTypes.SPLASH, 30);
        level.playSound(null, player.blockPosition(), SoundEvents.PLAYER_SWIM, SoundSource.PLAYERS, 1.1f, 1.0f);
    }

    private static void castFrostNova(ServerPlayer player, ServerLevel level, int damage) {
        damageNearbyEntities(player, level, 6.0, damage, false);
        applyEffectToNearbyEntities(player, level, 6.0, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 2));
        spawnParticles(level, player.position(), ParticleTypes.SNOWFLAKE, 60);
        level.playSound(null, player.blockPosition(), SoundEvents.POWDER_SNOW_BREAK, SoundSource.PLAYERS, 1.4f, 1.0f);
    }

    private static void castTidalWave(ServerPlayer player, ServerLevel level, int damage) {
        Vec3 lookVec = player.getLookAngle();
        Vec3 targetPos = player.position().add(lookVec.scale(12));
        damageInLine(player, level, player.position(), targetPos, 3.0, damage, true);
        knockbackInLine(player, level, player.position(), targetPos, 3.0, 1.2);
        spawnParticlesInLine(level, player.position(), targetPos, ParticleTypes.SPLASH, 80);
        level.playSound(null, player.blockPosition(), SoundEvents.PLAYER_SPLASH_HIGH_SPEED, SoundSource.PLAYERS, 1.9f, 0.8f);
    }

    private static void castBlizzard(ServerPlayer player, ServerLevel level, int damage) {
        damageNearbyEntities(player, level, 7.0, damage, true);
        applyEffectToNearbyEntities(player, level, 7.0, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 3));
        applyEffectToNearbyEntities(player, level, 7.0, new MobEffectInstance(MobEffects.WEAKNESS, 100, 1));
        spawnParticles(level, player.position(), ParticleTypes.SNOWFLAKE, 100);
        level.playSound(null, player.blockPosition(), SoundEvents.SNOW_GOLEM_HURT, SoundSource.PLAYERS, 1.7f, 0.8f);
    }

    private static void castPoseidonWrath(ServerPlayer player, ServerLevel level, int damage) {
        damageNearbyEntities(player, level, 9.0, damage, true);
        applyEffectToNearbyEntities(player, level, 9.0, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
        spawnParticles(level, player.position(), ParticleTypes.SPLASH, 100);
        spawnParticles(level, player.position(), ParticleTypes.BUBBLE, 50);
        level.playSound(null, player.blockPosition(), SoundEvents.TRIDENT_THUNDER.value(), SoundSource.PLAYERS, 2.3f, 1.0f);
    }

    private static void castGlacialPrison(ServerPlayer player, ServerLevel level, int damage) {
        damageNearbyEntities(player, level, 8.0, damage, true);
        applyEffectToNearbyEntities(player, level, 8.0, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 5));
        applyEffectToNearbyEntities(player, level, 8.0, new MobEffectInstance(MobEffects.WEAKNESS, 200, 3));
        spawnParticles(level, player.position(), ParticleTypes.SNOWFLAKE, 120);
        level.playSound(null, player.blockPosition(), SoundEvents.GLASS_PLACE, SoundSource.PLAYERS, 2.1f, 0.6f);
    }

    private static void castOceanGuardian(ServerPlayer player, ServerLevel level, int damage) {
        damageNearbyEntities(player, level, 10.0, damage, true);
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 2));
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 150, 1));
        player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 300, 0));
        spawnParticles(level, player.position(), ParticleTypes.BUBBLE, 150);
        level.playSound(null, player.blockPosition(), SoundEvents.ELDER_GUARDIAN_AMBIENT, SoundSource.PLAYERS, 2.8f, 1.0f);
    }

    private static void castAbsoluteZero(ServerPlayer player, ServerLevel level, int damage) {
        damageNearbyEntities(player, level, 15.0, damage, true);
        applyEffectToNearbyEntities(player, level, 15.0, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 300, 10));
        applyEffectToNearbyEntities(player, level, 15.0, new MobEffectInstance(MobEffects.WEAKNESS, 300, 5));
        applyEffectToNearbyEntities(player, level, 15.0, new MobEffectInstance(MobEffects.WITHER, 200, 2));
        spawnParticles(level, player.position(), ParticleTypes.SNOWFLAKE, 300);
        spawnParticles(level, player.position(), ParticleTypes.END_ROD, 100);
        level.playSound(null, player.blockPosition(), SoundEvents.WITHER_DEATH, SoundSource.PLAYERS, 3.5f, 1.5f);
    }

    // ===== UTILITY METHODS =====
    
    private static void damageNearbyEntities(ServerPlayer caster, ServerLevel level, double radius, int damage, boolean setOnFire) {
        AABB area = new AABB(caster.position().subtract(radius, radius, radius), caster.position().add(radius, radius, radius));
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area, 
            entity -> entity != caster && entity.isAlive());
        
        for (LivingEntity entity : entities) {
            entity.hurt(level.damageSources().playerAttack(caster), damage);
            if (setOnFire) {
                entity.setRemainingFireTicks(100);
            }
        }
    }

    private static void knockbackNearbyEntities(ServerPlayer caster, ServerLevel level, double radius, double strength) {
        AABB area = new AABB(caster.position().subtract(radius, radius, radius), caster.position().add(radius, radius, radius));
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area, 
            entity -> entity != caster && entity.isAlive());
        
        for (LivingEntity entity : entities) {
            Vec3 direction = entity.position().subtract(caster.position()).normalize();
            entity.setDeltaMovement(entity.getDeltaMovement().add(direction.scale(strength)));
        }
    }

    private static void applyEffectToNearbyEntities(ServerPlayer caster, ServerLevel level, double radius, MobEffectInstance effect) {
        AABB area = new AABB(caster.position().subtract(radius, radius, radius), caster.position().add(radius, radius, radius));
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area, 
            entity -> entity != caster && entity.isAlive());
        
        for (LivingEntity entity : entities) {
            entity.addEffect(new MobEffectInstance(effect));
        }
    }

    private static void damageInLine(ServerPlayer caster, ServerLevel level, Vec3 start, Vec3 end, double radius, int damage, boolean setOnFire) {
        Vec3 direction = end.subtract(start).normalize();
        double distance = start.distanceTo(end);
        
        for (double d = 0; d < distance; d += 0.5) {
            Vec3 point = start.add(direction.scale(d));
            AABB area = new AABB(point.subtract(radius, radius, radius), point.add(radius, radius, radius));
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area, 
                entity -> entity != caster && entity.isAlive());
            
            for (LivingEntity entity : entities) {
                entity.hurt(level.damageSources().playerAttack(caster), damage);
                if (setOnFire) {
                    entity.setRemainingFireTicks(100);
                }
            }
        }
    }

    private static void knockbackInLine(ServerPlayer caster, ServerLevel level, Vec3 start, Vec3 end, double radius, double strength) {
        Vec3 direction = end.subtract(start).normalize();
        double distance = start.distanceTo(end);
        
        for (double d = 0; d < distance; d += 0.5) {
            Vec3 point = start.add(direction.scale(d));
            AABB area = new AABB(point.subtract(radius, radius, radius), point.add(radius, radius, radius));
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area, 
                entity -> entity != caster && entity.isAlive());
            
            for (LivingEntity entity : entities) {
                entity.setDeltaMovement(entity.getDeltaMovement().add(direction.scale(strength)));
            }
        }
    }

    private static void spawnParticles(ServerLevel level, Vec3 pos, net.minecraft.core.particles.ParticleOptions particle, int count) {
        for (int i = 0; i < count; i++) {
            double offsetX = (level.random.nextDouble() - 0.5) * 2.0;
            double offsetY = (level.random.nextDouble() - 0.5) * 2.0;
            double offsetZ = (level.random.nextDouble() - 0.5) * 2.0;
            level.sendParticles(particle, pos.x + offsetX, pos.y + offsetY + 1.0, pos.z + offsetZ, 1, 0, 0, 0, 0.1);
        }
    }

    private static void spawnParticlesInLine(ServerLevel level, Vec3 start, Vec3 end, net.minecraft.core.particles.ParticleOptions particle, int count) {
        Vec3 direction = end.subtract(start).normalize();
        double distance = start.distanceTo(end);
        
        for (int i = 0; i < count; i++) {
            double t = (double) i / count;
            Vec3 point = start.add(direction.scale(distance * t));
            level.sendParticles(particle, point.x, point.y + 1.0, point.z, 1, 0.1, 0.1, 0.1, 0.05);
        }
    }
    
    // Новые вспомогательные методы для улучшенных заклинаний
    
    private static void setFireToNearbyBlocks(ServerPlayer player, ServerLevel level, double radius) {
        AABB area = new AABB(player.position().subtract(radius, radius, radius), player.position().add(radius, radius, radius));
        for (int x = (int) area.minX; x <= area.maxX; x++) {
            for (int y = (int) area.minY; y <= area.maxY; y++) {
                for (int z = (int) area.minZ; z <= area.maxZ; z++) {
                    var pos = new net.minecraft.core.BlockPos(x, y, z);
                    if (level.getBlockState(pos).isAir() && level.random.nextFloat() < 0.3f) {
                        level.setBlock(pos, net.minecraft.world.level.block.Blocks.FIRE.defaultBlockState(), 3);
                    }
                }
            }
        }
    }
    
    private static void damageNearbyEntitiesAtPos(ServerLevel level, Vec3 pos, double radius, int damage, boolean setOnFire) {
        AABB area = new AABB(pos.subtract(radius, radius, radius), pos.add(radius, radius, radius));
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area, 
            entity -> entity.isAlive());
        
        for (LivingEntity entity : entities) {
            entity.hurt(level.damageSources().magic(), damage);
            if (setOnFire) {
                entity.setRemainingFireTicks(100); // 5 секунд огня (20 тиков = 1 секунда)
            }
        }
    }
    
    private static void knockbackNearbyEntitiesNew(ServerPlayer caster, ServerLevel level, double radius, double strength) {
        AABB area = new AABB(caster.position().subtract(radius, radius, radius), caster.position().add(radius, radius, radius));
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area, 
            entity -> entity != caster && entity.isAlive());
        
        for (LivingEntity entity : entities) {
            Vec3 direction = entity.position().subtract(caster.position()).normalize();
            entity.setDeltaMovement(entity.getDeltaMovement().add(direction.scale(strength)));
        }
    }
    
    // ===== NECROMANCER FIRE SPELLS =====
    
    private static void castSoulFireRitual(ServerPlayer player, ServerLevel level, int damage) {
        // Некромантское заклинание: воскрешает мертвых мобов как союзников
        AABB area = new AABB(player.position().subtract(8.0, 8.0, 8.0), player.position().add(8.0, 8.0, 8.0));
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area, 
            entity -> entity != player && entity.isAlive());
        
        // Наносим урон врагам
        for (LivingEntity entity : entities) {
            entity.hurt(level.damageSources().magic(), damage);
            entity.setRemainingFireTicks(100); // 5 секунд огня
        }
        
        // Воскрешаем мертвых мобов как союзников (имитация)
        spawnSoulFireMinions(player, level, 3);
        
        // Эффекты
        spawnParticles(level, player.position(), ParticleTypes.SOUL_FIRE_FLAME, 50);
        spawnParticles(level, player.position(), ParticleTypes.SOUL, 30);
        level.playSound(null, player.blockPosition(), SoundEvents.WITHER_AMBIENT, SoundSource.PLAYERS, 2.0f, 0.8f);
    }
    
    private static void castPhoenixNecromancy(ServerPlayer player, ServerLevel level, int damage) {
        // Некромантское заклинание: превращает врагов в огненных зомби
        AABB area = new AABB(player.position().subtract(10.0, 10.0, 10.0), player.position().add(10.0, 10.0, 10.0));
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area, 
            entity -> entity != player && entity.isAlive());
        
        // Наносим урон и превращаем в огненных зомби
        for (LivingEntity entity : entities) {
            entity.hurt(level.damageSources().magic(), damage);
            entity.setRemainingFireTicks(200); // 10 секунд огня
            // Применяем эффект "нежить" (слабость + замедление)
            entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 300, 2));
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 300, 1));
        }
        
        // Исцеляем игрока за счет некромантии
        player.heal(damage / 4); // Исцеляем на 25% от урона
        
        // Эффекты
        spawnParticles(level, player.position(), ParticleTypes.SOUL_FIRE_FLAME, 80);
        spawnParticles(level, player.position(), ParticleTypes.SOUL, 40); // Заменяем PHANTOM на SOUL
        spawnParticles(level, player.position(), ParticleTypes.FLAME, 60);
        level.playSound(null, player.blockPosition(), SoundEvents.PHANTOM_AMBIENT, SoundSource.PLAYERS, 2.5f, 1.2f);
    }
    
    private static void spawnSoulFireMinions(ServerPlayer player, ServerLevel level, int count) {
        // Создаем "духов огня" вокруг игрока (имитация союзников)
        for (int i = 0; i < count; i++) {
            double angle = (2 * Math.PI * i) / count;
            double distance = 3.0;
            Vec3 pos = player.position().add(
                Math.cos(angle) * distance,
                1.0,
                Math.sin(angle) * distance
            );
            
            // Создаем эффект "духа огня"
            spawnParticles(level, pos, ParticleTypes.SOUL_FIRE_FLAME, 20);
            spawnParticles(level, pos, ParticleTypes.SOUL, 10);
        }
    }
    
    // Crystal Wave - Кристальная волна (RARE)
    private static void castCrystalWave(ServerPlayer player, ServerLevel level, int damage) {
        // Создаем волну кристальной энергии в направлении взгляда
        Vec3 lookDir = player.getLookAngle();
        Vec3 startPos = player.position().add(0, 1.5, 0);
        
        // Волна движется на 8 блоков вперед
        for (int i = 1; i <= 8; i++) {
            Vec3 pos = startPos.add(lookDir.scale(i));
            
            // Находим врагов в радиусе 2 блоков от каждой точки волны
            AABB area = new AABB(pos.subtract(2.0, 2.0, 2.0), pos.add(2.0, 2.0, 2.0));
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area, 
                entity -> entity != player && entity.isAlive());
            
            for (LivingEntity entity : entities) {
                entity.hurt(level.damageSources().magic(), damage);
                // Кристальная энергия замедляет врагов
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 1));
            }
            
            // Эффекты кристальной волны
            spawnParticles(level, pos, ParticleTypes.END_ROD, 15);
            spawnParticles(level, pos, ParticleTypes.CRIT, 10);
        }
        
        level.playSound(null, player.blockPosition(), SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.PLAYERS, 1.5f, 1.2f);
    }
    
    // Deep Freeze - Глубокое замораживание (EPIC)
    private static void castDeepFreeze(ServerPlayer player, ServerLevel level, int damage) {
        // Мощный замораживающий взрыв в большом радиусе
        AABB area = new AABB(player.position().subtract(8.0, 8.0, 8.0), player.position().add(8.0, 8.0, 8.0));
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area, 
            entity -> entity != player && entity.isAlive());
        
        for (LivingEntity entity : entities) {
            entity.hurt(level.damageSources().magic(), damage);
            // Глубокое замораживание - сильное замедление + слабость
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 3));
            entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 400, 2));
            entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 400, 2));
        }
        
        // Мощные эффекты замораживания
        spawnParticles(level, player.position(), ParticleTypes.SNOWFLAKE, 100);
        spawnParticles(level, player.position(), ParticleTypes.ITEM_SNOWBALL, 50);
        spawnParticles(level, player.position(), ParticleTypes.END_ROD, 30);
        
        level.playSound(null, player.blockPosition(), SoundEvents.POWDER_SNOW_BREAK, SoundSource.PLAYERS, 2.0f, 0.8f);
        level.playSound(null, player.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1.5f, 0.5f);
    }
}
