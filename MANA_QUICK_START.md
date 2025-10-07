# Mana System Quick Start Guide

## Basic Usage in 3 Steps

### 1. Check if Player Has Mana

```java
import com.example.examplemod.mana.ManaHelper;

if (ManaHelper.hasEnoughMana(player, 30.0f)) {
    // Player has at least 30 mana
}
```

### 2. Consume Mana

```java
if (ManaHelper.consumeMana(player, 30.0f)) {
    // Successfully consumed 30 mana
    // Execute your ability/spell here
} else {
    // Not enough mana
    player.displayClientMessage(Component.literal("Not enough mana!"), true);
}
```

### 3. That's It!

The mana bar will automatically update on the screen showing the new mana value.

## Complete Example: Creating a Fireball Spell

```java
package com.example.yourmod.items;

import com.example.examplemod.mana.ManaHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FireballWandItem extends Item {
    private static final float MANA_COST = 40.0f;
    
    public FireballWandItem(Properties properties) {
        super(properties);
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        
        if (!level.isClientSide()) {
            // Check and consume mana
            if (ManaHelper.consumeMana(player, MANA_COST)) {
                // Cast fireball
                LargeFireball fireball = new LargeFireball(
                    level,
                    player,
                    player.getLookAngle().x,
                    player.getLookAngle().y,
                    player.getLookAngle().z,
                    1
                );
                fireball.setPos(
                    player.getX() + player.getLookAngle().x * 2,
                    player.getEyeY(),
                    player.getZ() + player.getLookAngle().z * 2
                );
                level.addFreshEntity(fireball);
                
                player.displayClientMessage(
                    Component.literal("Cast Fireball! Mana: " + 
                        (int)ManaHelper.getCurrentMana(player) + "/" + 
                        (int)ManaHelper.getMaxMana(player)), 
                    true
                );
                
                return InteractionResultHolder.success(itemstack);
            } else {
                player.displayClientMessage(
                    Component.literal("§cNot enough mana! Need: " + MANA_COST), 
                    true
                );
                return InteractionResultHolder.fail(itemstack);
            }
        }
        
        return InteractionResultHolder.pass(itemstack);
    }
}
```

## Advanced: Custom Mana Configuration

### Creating a Mage Class with Extra Mana

```java
import com.example.examplemod.mana.IManaData;
import com.example.examplemod.mana.ModAttachments;

public class MageClassItem extends Item {
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            // Upgrade player to mage class with more mana
            IManaData mana = player.getData(ModAttachments.PLAYER_MANA);
            mana.setMaxMana(200.0f);
            mana.setRegenRate(0.3f); // Faster regeneration
            mana.setColor(0xFF8B00FF); // Purple for mage
            mana.setCurrentMana(200.0f); // Fill to max
            
            player.displayClientMessage(
                Component.literal("§5You became a Mage! Max mana increased to 200!"),
                false
            );
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
```

### Creating Different Magic Schools

```java
public enum MagicSchool {
    FIRE(0xFFFF4500, "Fire Magic"),      // Orange-Red
    ICE(0xFF00FFFF, "Ice Magic"),        // Cyan
    NATURE(0xFF00FF00, "Nature Magic"),  // Green
    ARCANE(0xFF8B00FF, "Arcane Magic"),  // Purple
    SHADOW(0xFF4B0082, "Shadow Magic");  // Indigo
    
    private final int color;
    private final String name;
    
    MagicSchool(int color, String name) {
        this.color = color;
        this.name = name;
    }
    
    public void applyToPlayer(Player player) {
        IManaData mana = player.getData(ModAttachments.PLAYER_MANA);
        mana.setColor(this.color);
        player.displayClientMessage(
            Component.literal("§eYou now practice " + this.name + "!"),
            false
        );
    }
}

// Usage:
MagicSchool.FIRE.applyToPlayer(player);
```

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                     Your Mod/Item                           │
│                                                               │
│  if (ManaHelper.consumeMana(player, 50)) {                   │
│      // Use ability                                          │
│  }                                                           │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│                    ManaHelper API                            │
│  (Convenient wrapper methods)                                │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│               IManaData / ManaData                           │
│  (Core mana storage & logic)                                 │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────────┐
│              ModAttachments.PLAYER_MANA                      │
│  (NeoForge Data Attachment - stores on player)               │
└──────────────────────┬──────────────────────────────────────┘
                       │
       ┌───────────────┴────────────────┐
       ▼                                ▼
┌────────────────┐              ┌───────────────┐
│  ManaEvents    │              │ ManaBarOverlay│
│  (Server sync) │              │ (Client UI)   │
└────────────────┘              └───────────────┘
```

## Tips & Best Practices

1. **Always check on server side**: Perform mana checks in `!level.isClientSide()` blocks
2. **Use ManaHelper**: It's the easiest way to work with mana
3. **Provide feedback**: Show messages when mana is consumed or insufficient
4. **Customize colors**: Different abilities/schools should have different colors
5. **Balance carefully**: Default regen is 2 mana/second, adjust costs accordingly

## Common Patterns

### Ability with Cooldown

```java
private int cooldown = 0;

@Override
public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
    if (cooldown > 0) cooldown--;
}

@Override
public InteractionResultHolder<ItemStack> use(...) {
    if (cooldown > 0) {
        player.displayClientMessage(Component.literal("Cooldown: " + (cooldown/20) + "s"), true);
        return InteractionResultHolder.fail(itemstack);
    }
    
    if (ManaHelper.consumeMana(player, 30.0f)) {
        // Cast spell
        cooldown = 100; // 5 seconds
        return InteractionResultHolder.success(itemstack);
    }
    return InteractionResultHolder.fail(itemstack);
}
```

### Continuous Mana Drain

```java
@Override
public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
    if (entity instanceof Player player && !level.isClientSide() && selected) {
        // Drain 0.5 mana per second when held
        if (player.tickCount % 20 == 0) {
            ManaHelper.consumeMana(player, 0.5f);
        }
    }
}
```

### Mana-Based Enchantment

```java
public class ManaEnchantment extends Enchantment {
    @Override
    public void doPostAttack(LivingEntity attacker, Entity target, int level) {
        if (attacker instanceof Player player && !player.level().isClientSide()) {
            if (ManaHelper.consumeMana(player, 5.0f * level)) {
                // Apply bonus damage or effect
                if (target instanceof LivingEntity living) {
                    living.hurt(player.damageSources().magic(), 4.0f * level);
                }
            }
        }
    }
}
```
