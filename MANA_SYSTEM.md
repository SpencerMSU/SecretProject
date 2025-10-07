# Mana System Documentation

## Overview

This mod includes a flexible mana system API that allows developers to create custom mana systems using a builder pattern. The system includes:

- A customizable mana data storage system
- Automatic mana regeneration
- Client-server synchronization
- A beautiful, resolution-adaptive mana bar UI

## Mana System API

### IManaData Interface

The core interface for mana data. Provides methods to:
- Get/set current and maximum mana
- Add/remove mana
- Configure regeneration rates
- Customize mana bar colors

### ManaDataBuilder

Use the builder pattern to create custom mana systems:

```java
// Example: Create a mana system with custom properties
ManaData manaData = ManaDataBuilder.create()
    .setMaxMana(200.0f)           // Set maximum mana to 200
    .setInitialMana(100.0f)       // Start with 100 mana
    .setRegenRate(0.2f)           // Regenerate 0.2 mana per tick
    .setColor(0xFF00FFFF)         // Set cyan color for the bar
    .build();

// Or use RGB values
ManaData manaData = ManaDataBuilder.create()
    .setMaxMana(150.0f)
    .setRegenRate(0.15f)
    .setColor(255, 0, 255)        // RGB: Magenta
    .build();
```

### ManaHelper

Convenience methods for working with player mana:

```java
// Check if player has enough mana
if (ManaHelper.hasEnoughMana(player, 50.0f)) {
    // Player has at least 50 mana
}

// Consume mana
if (ManaHelper.consumeMana(player, 30.0f)) {
    // Successfully consumed 30 mana
    // Perform ability...
}

// Add mana
ManaHelper.addMana(player, 20.0f);

// Get/Set mana values
float current = ManaHelper.getCurrentMana(player);
float max = ManaHelper.getMaxMana(player);
ManaHelper.setMaxMana(player, 150.0f);
```

### Direct Data Attachment Access

Access the mana data attachment directly:

```java
import com.example.examplemod.mana.ModAttachments;
import com.example.examplemod.mana.IManaData;

// Get player's mana data
IManaData manaData = player.getData(ModAttachments.PLAYER_MANA);

// Use the interface methods
float current = manaData.getCurrentMana();
float max = manaData.getMaxMana();
manaData.removeMana(25.0f);
manaData.addMana(10.0f);
```

## Mana Bar UI

The mana bar is automatically rendered on the right side of the screen and includes:

- **Adaptive positioning**: Adjusts to any screen resolution
- **Visual feedback**: Shows current/max mana with a filled bar
- **Color customization**: Bar color matches the configured mana color
- **Text display**: Shows exact mana values (e.g., "75/100")
- **Label**: "Mana" label above the bar

### Customizing Bar Position

To change the mana bar position, modify the constants in `ManaBarOverlay.java`:

```java
private static final int MARGIN_RIGHT = 10;  // Distance from right edge
private static final int MARGIN_TOP = 50;    // Distance from top
```

## Default Configuration

By default, players start with:
- **Maximum Mana**: 100
- **Regeneration Rate**: 0.1 per tick (2 per second)
- **Color**: Blue (#00A0FF)

## Examples

### Creating a Magic Spell

```java
public class FireballSpell {
    private static final float MANA_COST = 25.0f;
    
    public static boolean castFireball(Player player) {
        if (ManaHelper.consumeMana(player, MANA_COST)) {
            // Spawn fireball entity
            // ... spell logic ...
            return true;
        }
        return false; // Not enough mana
    }
}
```

### Creating a Mana Potion

```java
public class ManaPotionItem extends Item {
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            ManaHelper.addMana(player, 50.0f);
            // Play sound, particles, etc.
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
```

### Modifying Mana Properties

```java
// Increase max mana (e.g., level up system)
public static void levelUp(Player player) {
    IManaData mana = player.getData(ModAttachments.PLAYER_MANA);
    mana.setMaxMana(mana.getMaxMana() + 10.0f);
    mana.setCurrentMana(mana.getMaxMana()); // Refill on level up
}

// Change regeneration rate (e.g., buffs/debuffs)
public static void applyManaBoost(Player player) {
    IManaData mana = player.getData(ModAttachments.PLAYER_MANA);
    mana.setRegenRate(0.5f); // Faster regeneration
}

// Change bar color (e.g., different magic schools)
public static void setFireMagic(Player player) {
    IManaData mana = player.getData(ModAttachments.PLAYER_MANA);
    mana.setColor(0xFFFF4500); // Orange-red for fire magic
}
```

## Technical Details

### Data Persistence

Mana data is automatically saved and loaded using NBT serialization through NeoForge's data attachment system.

### Client-Server Synchronization

Mana data is synced from server to client:
- Every 20 ticks (1 second) during gameplay
- When the player logs in
- After respawning

### Events

The system hooks into the following events:
- `PlayerTickEvent.Post`: For mana regeneration and periodic syncing
- `PlayerEvent.Clone`: For preserving mana on respawn
- `PlayerEvent.PlayerLoggedInEvent`: For initial sync on login

## Integration

To use this mana system in your own items, abilities, or systems:

1. Add the mana package imports
2. Use `ManaHelper` for simple operations
3. Or access `ModAttachments.PLAYER_MANA` directly for advanced usage
4. The UI will automatically reflect any changes

The system is designed as a flexible API, not a rigid solution, allowing full customization for your mod's needs!
