# Mana Bar Visual Mockup

## Screenshot Description

When you run the game, you will see the mana bar displayed on the **right side** of the screen like this:

```
┌────────────────────────────────────────────────────────────────┐
│                                                                  │
│  Minecraft HUD                                                   │
│                                                                  │
│                                                    Mana          │
│                                              ┌────────────┐     │
│                                              │████████░░░░│     │
│                                              └────────────┘     │
│                                                 75/100          │
│                                                                  │
│                                                                  │
│  [Player Hotbar]                                                 │
│  [■][■][■][■][■][■][■][■][■]                                   │
└────────────────────────────────────────────────────────────────┘
```

## Detailed Visual Breakdown

### Position
- **Horizontal**: 10 pixels from the right edge
- **Vertical**: 50 pixels from the top
- **Always visible**: Above most HUD elements

### Colors (Default Blue Theme)

1. **Label "Mana"**: White text with black shadow
2. **Border**: White (1px solid line)
3. **Background**: Black (#000000)
4. **Empty Bar**: Dark blue (#002030) - 30% of mana color
5. **Filled Bar**: Bright blue (#00A0FF) - Full brightness
6. **Numbers**: White text with black shadow

### Different States

#### Full Mana (100/100)
```
                                                    Mana
                                              ┌────────────┐
                                              │████████████│
                                              └────────────┘
                                                 100/100
```
Bar is completely filled with bright blue.

#### 75% Mana (75/100)
```
                                                    Mana
                                              ┌────────────┐
                                              │█████████░░░│
                                              └────────────┘
                                                  75/100
```
Bar is 75% filled, showing 3/4 bright blue and 1/4 dark blue.

#### 50% Mana (50/100)
```
                                                    Mana
                                              ┌────────────┐
                                              │██████░░░░░░│
                                              └────────────┘
                                                  50/100
```
Bar is half filled - clear visual indicator of half mana.

#### 25% Mana (25/100) - Low Mana
```
                                                    Mana
                                              ┌────────────┐
                                              │███░░░░░░░░░│
                                              └────────────┘
                                                  25/100
```
Bar is only 1/4 filled - visual warning of low mana.

#### Empty Mana (0/100)
```
                                                    Mana
                                              ┌────────────┐
                                              │░░░░░░░░░░░░│
                                              └────────────┘
                                                   0/100
```
Bar shows only dark blue background - no mana available.

### Color Themes

The system supports different color schemes for different magic types:

#### Fire Magic (Red/Orange)
```
                                                    Mana
                                              ┌────────────┐
                                              │🔥🔥🔥🔥🔥🔥🔥🔥│  (#FF4500)
                                              └────────────┘
```

#### Ice Magic (Cyan)
```
                                                    Mana
                                              ┌────────────┐
                                              │❄️❄️❄️❄️❄️❄️❄️❄️│  (#00FFFF)
                                              └────────────┘
```

#### Nature Magic (Green)
```
                                                    Mana
                                              ┌────────────┐
                                              │🌿🌿🌿🌿🌿🌿🌿🌿│  (#00FF00)
                                              └────────────┘
```

#### Arcane Magic (Purple)
```
                                                    Mana
                                              ┌────────────┐
                                              │✨✨✨✨✨✨✨✨│  (#8B00FF)
                                              └────────────┘
```

## Screen Resolution Adaptation

The mana bar automatically adapts to different screen resolutions:

### 1920x1080 (Full HD)
Position: x=1830, y=50

### 1280x720 (HD)
Position: x=1190, y=50

### 2560x1440 (2K)
Position: x=2470, y=50

### 3840x2160 (4K)
Position: x=3750, y=50

The bar always maintains the same size (80x8 pixels) but adjusts its position to stay 10 pixels from the right edge.

## Animation

When mana changes:
- The filled portion **smoothly updates** to show the new value
- Numbers **update instantly** (e.g., "75/100" → "50/100")
- **No jarring transitions** - smooth visual feedback

## Integration with Game

The mana bar:
- ✅ Appears **on top** of most UI elements
- ✅ Does **not interfere** with vanilla UI
- ✅ **Respects** F1 (hide UI) key
- ✅ Works in **all game modes**
- ✅ **Persists** across dimension changes
- ✅ Updates in **real-time** with server sync

## Visual Polish

1. **Text Shadow**: All text has a 1-pixel black shadow for readability
2. **Border**: Clean white 1-pixel border separates bar from background
3. **Contrast**: High contrast between filled/empty portions
4. **Alignment**: Perfectly centered text both horizontally and vertically
5. **Spacing**: Proper spacing between label, bar, and numbers

This creates a professional, polished look that fits seamlessly with Minecraft's aesthetic while being clearly visible and functional!
