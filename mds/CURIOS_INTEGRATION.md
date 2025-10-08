# Curios API Integration

This mod now includes integration with the Curios API, which provides an accessory system for Minecraft.

## Added Dependencies

- **Curios API**: Version 9.1.1 for Minecraft 1.21.1
  - Added as both compile-time and runtime dependency
  - Required for the mod to function

## Accessory Slots

The mod defines 9 different accessory slots:

1. **ring1** - First ring slot (order: 10)
2. **ring2** - Second ring slot (order: 11)
3. **necklace** - Necklace slot (order: 20)
4. **bracelet** - Bracelet slot (order: 30)
5. **belt** - Belt slot (order: 40)
6. **charm** - Charm slot (order: 50)
7. **back** - Back slot (order: 60)
8. **head** - Head accessory slot (order: 70)
9. **body** - Body accessory slot (order: 80)

Each slot has a size of 1 and uses the default Curios icons.

## Files Modified

- `build.gradle` - Added Curios maven repository and dependencies
- `src/main/templates/META-INF/neoforge.mods.toml` - Added Curios as a required dependency

## Files Created

- `src/main/resources/data/curios/slots/*.json` - 9 slot definition files

## Usage

Players will be able to access these accessory slots through the Curios interface (typically opened with a keybind).
Mod developers can create items that can be equipped in these slots by implementing the appropriate Curios API interfaces.
