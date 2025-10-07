# Mana Bar UI Preview

The mana bar is displayed on the right side of the screen with the following visual design:

```
                                                    Mana
                                              ┌──────────┐
                                              │██████████│  100/100
                                              └──────────┘
```

## Visual Elements

1. **Label**: "Mana" text displayed above the bar
2. **Border**: White border (1px) around the bar
3. **Background**: Dark semi-transparent black background
4. **Empty Bar**: Darker version of the mana color (30% brightness)
5. **Filled Bar**: Full brightness mana color (default: blue #00A0FF)
6. **Text**: Current/Max mana values below the bar with shadow

## Examples at Different Mana Levels

### Full Mana (100%)
```
                                                    Mana
                                              ┌──────────┐
                                              │██████████│  100/100
                                              └──────────┘
```

### Half Mana (50%)
```
                                                    Mana
                                              ┌──────────┐
                                              │█████     │   50/100
                                              └──────────┘
```

### Low Mana (25%)
```
                                                    Mana
                                              ┌──────────┐
                                              │██        │   25/100
                                              └──────────┘
```

### Empty Mana (0%)
```
                                                    Mana
                                              ┌──────────┐
                                              │          │    0/100
                                              └──────────┘
```

## Position

- **Horizontal**: 10 pixels from the right edge of the screen
- **Vertical**: 50 pixels from the top of the screen
- **Size**: 80 pixels wide × 8 pixels tall
- **Adaptive**: Automatically adjusts position based on screen resolution

## Customization

Different mana colors create different visual themes:
- **Blue** (#00A0FF) - Default magical energy
- **Green** (#00FF00) - Nature magic
- **Red** (#FF0000) - Fire/blood magic
- **Purple** (#8B00FF) - Dark/arcane magic
- **Cyan** (#00FFFF) - Ice/water magic
- **Orange** (#FF8C00) - Lightning/energy magic

The bar will automatically use the configured color and adjust the empty bar to be a darker shade.
