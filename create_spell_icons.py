#!/usr/bin/env python3
from PIL import Image, ImageDraw
import os

SIZE = 16
ICONS_DIR = "src/main/resources/assets/examplemod/textures/spell_icons"

def px(img, x, y, c):
    """Set pixel at x,y to color c"""
    img.putpixel((x, y), c)

def create_simple_icon(name, draw_func):
    """Create icon with custom draw function"""
    img = Image.new('RGBA', (SIZE, SIZE), (0, 0, 0, 0))
    draw_func(img)
    img.save(f"{ICONS_DIR}/{name}.png")
    print(f"Created {name}.png")

# Fire Icons
def draw_spark(img):
    # Flint and steel
    for y in range(6, 12):
        for x in range(4, 11):
            intensity = 100 + (y-6) * 20 + abs(7.5-x) * -10
            px(img, x, y, (int(intensity), int(intensity), int(intensity), 255))
    # Orange sparks
    px(img, 11, 2, (255, 200, 0, 255))
    px(img, 12, 4, (255, 150, 0, 255))
    px(img, 10, 5, (255, 100, 0, 255))

def draw_ember(img):
    # Torch stick
    for y in range(7, 12):
        px(img, 7, y, (139, 90, 43, 255))
        px(img, 8, y, (139, 90, 43, 255))
    # Flame
    colors = [(255, 60, 0), (255, 100, 0), (255, 140, 0), (255, 180, 0), (255, 220, 0), (255, 240, 0)]
    for i, color in enumerate(colors):
        y = 6 - i
        if y >= 1:
            px(img, 7, y, (*color, 255))
            px(img, 8, y, (*color, 255))

def draw_fire_bolt(img):
    # Fire powder particle cloud
    for y in range(4, 12):
        for x in range(2, 13):
            dist = abs(7.5 - x) + abs(7.5 - y)
            if dist < 5:
                intensity = int(255 - dist * 20)
                px(img, x, y, (255, intensity, 0, 255))

def draw_flame_burst(img):
    # Fireball
    for y in range(2, 14):
        for x in range(3, 13):
            dx, dy = x - 7.5, y - 8
            dist = (dx*dx + dy*dy) ** 0.5
            if dist < 4:
                intensity = int(255 - dist * 30)
                px(img, x, y, (255, max(0, intensity), 0, 255))

def draw_magma_spike(img):
    # Magma block texture
    for y in range(4, 13):
        for x in range(3, 13):
            base = 100 + (hash((x, y)) % 140)
            px(img, x, y, (base, int(base * 0.3), 0, 255))

def draw_blaze_rush(img):
    # Blaze rod
    for y in range(2, 14):
        intensity = 255 - (y - 2) * 10
        px(img, 7, y, (255, intensity, 0, 255))
        px(img, 8, y, (255, min(255, intensity + 20), 0, 255))

def draw_inferno(img):
    # Lava bucket
    # Bucket
    for y in range(10, 15):
        for x in range(4, 12):
            if y == 14 and (x == 5 or x == 10):
                continue
            intensity = 80 + (14 - y) * 20
            px(img, x, y, (intensity, intensity, intensity, 255))
    # Lava
    for y in range(5, 10):
        for x in range(6, 10):
            intensity = 100 + (9 - y) * 20
            px(img, x, y, (255, intensity, 0, 255))

def draw_hellfire(img):
    # Netherrack texture
    for y in range(3, 13):
        for x in range(3, 13):
            base = 100 + (hash((x * y)) % 40)
            px(img, x, y, (base, int(base * 0.35), int(base * 0.35), 255))

def draw_phoenix_rebirth(img):
    # Golden fire powder with wings
    for y in range(2, 11):
        for x in range(3, 13):
            dist = abs(7.5 - x) + abs(6 - y)
            if dist < 5:
                intensity = int(200 + (5 - dist) * 10)
                px(img, x, y, (255, intensity, 0, 255))
    # Wing sparkles
    px(img, 2, 5, (255, 140, 0, 180))
    px(img, 12, 5, (255, 140, 0, 180))
    px(img, 1, 7, (255, 120, 0, 150))
    px(img, 13, 7, (255, 120, 0, 150))

def draw_solar_eclipse(img):
    # Nether star
    for y in range(1, 15):
        for x in range(1, 15):
            dx, dy = x - 7.5, y - 7.5
            dist = (dx*dx + dy*dy) ** 0.5
            if dist < 6:
                intensity = int(255 - dist * 15)
                px(img, x, y, (255, 255, max(200, intensity), 255))

# Water Icons
def draw_water_splash(img):
    # Water bottle
    for y in range(8, 14):
        for x in range(6, 10):
            if y < 10 or (x > 6 and x < 9):
                px(img, x, y, (100, 100, 100, 255))
    # Water inside
    for y in range(9, 13):
        for x in range(7, 9):
            px(img, x, y, (102, 204, 255, 255))
    # Neck
    px(img, 7, 7, (80, 80, 80, 255))

def draw_ripple(img):
    # Concentric water circles
    for r in [2, 3, 4, 5]:
        for angle in range(0, 360, 30):
            import math
            x = int(7.5 + r * math.cos(math.radians(angle)))
            y = int(7.5 + r * math.sin(math.radians(angle)))
            if 0 <= x < SIZE and 0 <= y < SIZE:
                intensity = int(150 + (5 - r) * 20)
                px(img, x, y, (51, intensity, 255, 255))

def draw_water_bolt(img):
    # Water bucket
    for y in range(10, 15):
        for x in range(4, 12):
            if y == 14 and (x == 5 or x == 10):
                continue
            intensity = 80 + (14 - y) * 20
            px(img, x, y, (intensity, intensity, intensity, 255))
    # Water
    for y in range(6, 10):
        for x in range(6, 10):
            px(img, x, y, (0, 102, 255, 255))

def draw_whirlpool(img):
    # Heart of the sea - cyan gem
    for y in range(4, 12):
        for x in range(4, 12):
            dx, dy = x - 7.5, y - 8
            dist = (dx*dx + dy*dy) ** 0.5
            if dist < 4:
                intensity = int(200 - dist * 30)
                px(img, x, y, (0, intensity, 204, 255))

def draw_ice_spike(img):
    # Packed ice - light blue
    for y in range(3, 13):
        for x in range(3, 13):
            base = 180 + (hash((x + y)) % 60)
            px(img, x, y, (base, base, 255, 255))

def draw_tsunami_wave(img):
    # Prismarine crystal - cyan crystal
    for y in range(2, 14):
        width = 6 - abs(y - 8) // 2
        for x in range(8 - width, 8 + width):
            intensity = 150 + (6 - abs(y - 8)) * 15
            px(img, x, y, (102, intensity, intensity, 255))

def draw_frost_storm(img):
    # Blue ice texture with snowflakes
    for y in range(3, 13):
        for x in range(3, 13):
            base = 200 + (hash((x * 7 + y)) % 40)
            px(img, x, y, (150, 150, base, 255))
    # Snowflakes
    for x in [5, 10]:
        for y in [5, 10]:
            px(img, x, y, (255, 255, 255, 255))

def draw_hydro_blast(img):
    # Conduit - cyan ring
    for y in range(3, 13):
        for x in range(3, 13):
            dx, dy = x - 7.5, y - 8
            dist = (dx*dx + dy*dy) ** 0.5
            if 2.5 < dist < 4.5:
                px(img, x, y, (0, 204, 204, 255))
            elif dist < 2:
                px(img, x, y, (102, 255, 255, 255))

def draw_leviathan_wrath(img):
    # Trident
    # Prongs
    for x in [5, 7, 9]:
        for y in range(2, 6):
            px(img, x, y, (102, 178, 255, 255))
    # Handle
    for y in range(6, 14):
        px(img, 7, y, (102, 178, 255, 255))
    # Top decoration
    px(img, 7, 1, (51, 153, 255, 255))

def draw_oceanic_abyss(img):
    # Dark eye - ender eye style but water themed
    # Outer ring
    for y in range(3, 13):
        for x in range(3, 13):
            dx, dy = x - 7.5, y - 8
            dist = (dx*dx + dy*dy) ** 0.5
            if dist < 4.5:
                if dist > 2:
                    px(img, x, y, (0, 17, 34, 255))
                else:
                    intensity = int(50 + (2 - dist) * 50)
                    px(img, x, y, (0, intensity, intensity * 2, 255))

# Create all icons
fire_spells = [
    ("spark", draw_spark),
    ("ember", draw_ember),
    ("fire_bolt", draw_fire_bolt),
    ("flame_burst", draw_flame_burst),
    ("magma_spike", draw_magma_spike),
    ("blaze_rush", draw_blaze_rush),
    ("inferno", draw_inferno),
    ("hellfire", draw_hellfire),
    ("phoenix_rebirth", draw_phoenix_rebirth),
    ("solar_eclipse", draw_solar_eclipse),
]

water_spells = [
    ("water_splash", draw_water_splash),
    ("ripple", draw_ripple),
    ("water_bolt", draw_water_bolt),
    ("whirlpool", draw_whirlpool),
    ("ice_spike", draw_ice_spike),
    ("tsunami_wave", draw_tsunami_wave),
    ("frost_storm", draw_frost_storm),
    ("hydro_blast", draw_hydro_blast),
    ("leviathan_wrath", draw_leviathan_wrath),
    ("oceanic_abyss", draw_oceanic_abyss),
]

print("Creating spell icons...")
print("\n🔥 Fire Spells:")
for name, draw_func in fire_spells:
    create_simple_icon(name, draw_func)

print("\n💧 Water Spells:")
for name, draw_func in water_spells:
    create_simple_icon(name, draw_func)

print(f"\n✅ Successfully created {len(fire_spells) + len(water_spells)} spell icons!")
print(f"📁 Location: {ICONS_DIR}/")
