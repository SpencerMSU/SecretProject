# Accessories API Integration

This mod now uses the **Accessories API** (from the creators of OwO lib) instead of Curios API for the accessory system.

## What Changed

### Dependency
- **Old**: Curios API (version 9.5.1+1.21.1)
- **New**: Accessories API (version 1.1.0-beta.52+1.21.1)

### Maven Repository
- **Old**: `https://maven.theillusivec4.top/`
- **New**: `https://maven.wispforest.io`

### Mod ID
- **Old**: `curios`
- **New**: `accessories`

## Accessory Slots

The mod defines 6 different accessory slots:

1. **ring** - Ring slot (2 slots, order: 10)
2. **necklace** - Necklace slot (1 slot, order: 20)
3. **bracelet** - Bracelet slot (1 slot, order: 30)
4. **belt** - Belt slot (1 slot, order: 40)
5. **charm** - Charm slot (1 slot, order: 50)
6. **back** - Back/Cloak slot (1 slot, order: 60)

## Technical Changes

### Slot Definitions
Slot definitions moved from `data/examplemod/curios/slots/` to `data/examplemod/accessories/slot/`

### Entity Bindings
Entity bindings moved from `data/examplemod/curios/entities/` to `data/examplemod/accessories/entity/`

### Code Changes
- `BaseAccessoryItem` now implements `Accessory` instead of `ICurioItem`
- `SlotContext` replaced with `SlotReference`
- API method signatures updated to match Accessories API
- `AccessorySetManager` updated to use `AccessoriesCapability` instead of `CuriosApi`

## Removed Components

The following test components were removed:
- `TestCuriosItems` - Test accessory items
- `CuriosTestCommand` - Test command for Curios
- `GiveTestCuriosCommand` - Command to give test items
- `InitCuriosCommand` - Curios initialization command
- `CheckSlotsCommand` - Command to check Curios slots
- All files in `curios` package (CuriosInitializer, CuriosEventHandler, etc.)

## Benefits of Accessories API

- Modern API from trusted developers (OwO lib creators)
- Better integration with NeoForge
- More features and flexibility
- Active development and support

## Installation

To use this mod, you need:
1. NeoForge 1.21.1
2. Accessories API (version 1.1.0-beta.52+1.21.1 or newer)

The Accessories mod will be automatically downloaded when you install this mod if using a modpack manager.
