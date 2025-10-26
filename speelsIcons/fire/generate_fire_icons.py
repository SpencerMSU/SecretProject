from __future__ import annotations
from pathlib import Path
import math

try:
    from PIL import Image, ImageDraw, ImageFilter
except ImportError:
    raise SystemExit("Pillow не установлен. Установите: pip install Pillow")

SIZE = 32
CENTER = (SIZE // 2, SIZE // 2)
ASSETS_REL = Path("src/main/resources/assets/examplemod/textures/spell_icons")

# -------------------- helpers --------------------

def ensure_dir(p: Path):
    p.mkdir(parents=True, exist_ok=True)

def new_canvas() -> Image.Image:
    return Image.new("RGBA", (SIZE, SIZE), (0, 0, 0, 0))

def comp(base: Image.Image, top: Image.Image) -> Image.Image:
    return Image.alpha_composite(base, top)

def ellipse(draw: ImageDraw.ImageDraw, bbox, fill=None, outline=None, width: int = 1):
    draw.ellipse(bbox, fill=fill, outline=outline, width=width)

def line(draw: ImageDraw.ImageDraw, pts, fill, width=1):
    draw.line(pts, fill=fill, width=width)

def poly(draw: ImageDraw.ImageDraw, pts, fill=None, outline=None, width=1):
    if fill is not None:
        draw.polygon(pts, fill=fill)
    if outline is not None:
        draw.line(pts + [pts[0]], fill=outline, width=width)

def polar(cx, cy, r, ang_deg):
    a = math.radians(ang_deg)
    return (cx + r * math.cos(a), cy + r * math.sin(a))

def ring(size: int, inner_r: float, outer_r: float, color: tuple[int, int, int, int], blur: float = 0) -> Image.Image:
    img = new_canvas()
    d = ImageDraw.Draw(img)
    cx, cy = CENTER
    ellipse(d, (cx - outer_r, cy - outer_r, cx + outer_r, cy + outer_r), fill=color)
    ellipse(d, (cx - inner_r, cy - inner_r, cx + inner_r, cy + inner_r), fill=(0, 0, 0, 0))
    if blur > 0:
        img = img.filter(ImageFilter.GaussianBlur(blur))
    return img

def glow_disc(r: float, color: tuple[int, int, int, int], blur=2.0) -> Image.Image:
    img = new_canvas()
    d = ImageDraw.Draw(img)
    cx, cy = CENTER
    ellipse(d, (cx - r, cy - r, cx + r, cy + r), fill=color)
    return img.filter(ImageFilter.GaussianBlur(blur))

def rays(angles, length, width, color) -> Image.Image:
    img = new_canvas()
    d = ImageDraw.Draw(img)
    cx, cy = CENTER
    for a in angles:
        x2, y2 = polar(cx, cy, length, a)
        line(d, (cx, cy, x2, y2), color, width)
    return img

def flame_teardrop(cx, cy, w, h, fill, outline=None) -> Image.Image:
    # Овальная капля пламени
    img = new_canvas()
    d = ImageDraw.Draw(img)
    bbox = (cx - w/2, cy - h/2, cx + w/2, cy + h/2)
    ellipse(d, bbox, fill=fill, outline=outline)
    # Выемка сверху для формы пламени
    notch = (cx - w*0.35, cy - h*0.7, cx + w*0.35, cy)
    ellipse(d, notch, fill=(0, 0, 0, 0))
    return img

# -------------------- fire icons --------------------

def icon_spark() -> Image.Image:
    base = new_canvas()
    # мягкая подсветка
    base = comp(base, glow_disc(12, (255, 160, 0, 90), 2))
    base = comp(base, glow_disc(9, (255, 200, 0, 120), 1.6))
    # лучи
    base = comp(base, rays([0, 90, 180, 270], 12, 2, (255, 190, 0, 230)))
    base = comp(base, rays([45, 135, 225, 315], 9, 2, (255, 230, 100, 230)))
    # ядро
    cx, cy = CENTER
    core = Image.new("RGBA", (SIZE, SIZE), (0, 0, 0, 0))
    d = ImageDraw.Draw(core)
    romb = [(cx, cy - 3), (cx + 3, cy), (cx, cy + 3), (cx - 3, cy)]
    poly(d, romb, fill=(255, 255, 255, 255), outline=(255, 200, 0, 255), width=1)
    base = comp(base, core)
    # лёгкий блюм
    return Image.alpha_composite(base.filter(ImageFilter.GaussianBlur(0.6)), base)

def icon_ember() -> Image.Image:
    base = new_canvas()
    # раскалённый уголёк
    base = comp(base, glow_disc(11, (255, 80, 0, 80), 2.2))
    base = comp(base, glow_disc(8, (255, 120, 0, 120), 1.6))
    base = comp(base, glow_disc(5, (255, 200, 60, 160), 1.2))
    # трещинки
    d = ImageDraw.Draw(base)
    cx, cy = CENTER
    cracks = [
        [(cx - 6, cy - 2), (cx - 2, cy - 1), (cx, cy - 3)],
        [(cx + 5, cy), (cx + 1, cy + 1), (cx - 2, cy + 3)],
        [(cx - 1, cy + 4), (cx + 2, cy + 2), (cx + 4, cy + 5)],
    ]
    for pts in cracks:
        line(d, pts, (60, 20, 5, 220), 1)
    return base

def icon_fire_bolt() -> Image.Image:
    base = new_canvas()
    # хвост болта (комета)
    tail = new_canvas()
    d = ImageDraw.Draw(tail)
    for i in range(6):
        alpha = 60 + i * 25
        line(d, (6 - i, 26 - i, 18 + i, 14 - i), (255, 120 + i*20, 0, alpha), width=4 - i//2)
    base = comp(base, tail.filter(ImageFilter.GaussianBlur(0.8)))
    # головная часть
    head = new_canvas()
    d = ImageDraw.Draw(head)
    poly(d, [(18, 12), (26, 8), (24, 16)], fill=(255, 220, 80, 255), outline=(255, 120, 0, 255))
    base = comp(base, head)
    return base

def icon_flame_burst() -> Image.Image:
    base = new_canvas()
    # кольцевая вспышка
    base = comp(base, ring(SIZE, 9, 12, (255, 120, 0, 140), 1.5))
    base = comp(base, ring(SIZE, 6, 9, (255, 200, 0, 160), 1.0))
    # радиальные языки пламени
    tongues = new_canvas()
    d = ImageDraw.Draw(tongues)
    cx, cy = CENTER
    for a in range(0, 360, 20):
        p1 = polar(cx, cy, 6, a - 6)
        p2 = polar(cx, cy, 6, a + 6)
        p3 = polar(cx, cy, 12, a)
        poly(d, [p1, p2, p3], fill=(255, 140, 0, 190))
    base = comp(base, tongues)
    return base

def icon_magma_spike() -> Image.Image:
    base = new_canvas()
    # базальтовый шип
    spike = new_canvas()
    d = ImageDraw.Draw(spike)
    pts = [(16, 6), (22, 22), (10, 22)]
    poly(d, pts, fill=(30, 30, 36, 255), outline=(10, 10, 12, 255), width=1)
    base = comp(base, spike)
    # лавовые прожилки
    d = ImageDraw.Draw(base)
    veins = [((16, 10), (18, 16)), ((14, 14), (15, 20)), ((19, 14), (17, 19))]
    for a, b in veins:
        line(d, (*a, *b), (255, 80, 0, 220), 1)
    # свечение у основания
    base = comp(base, glow_disc(7, (255, 60, 0, 90), 2))
    return base

def icon_blaze_rush() -> Image.Image:
    base = new_canvas()
    # скоростные линии
    streaks = new_canvas()
    d = ImageDraw.Draw(streaks)
    for dy in (-6, -2, 2, 6):
        line(d, (4, 12 + dy, 22, 12 + dy), (255, 160, 0, 120), 2)
    base = comp(base, streaks.filter(ImageFilter.GaussianBlur(0.8)))
    # стержень ифрита по диагонали
    rod = new_canvas()
    d = ImageDraw.Draw(rod)
    poly(d, [(8, 24), (12, 24), (24, 12), (20, 12)], fill=(255, 200, 0, 255))
    base = comp(base, rod)
    return base

def icon_inferno() -> Image.Image:
    base = new_canvas()
    # мощный красно-оранжевый блюм
    base = comp(base, glow_disc(13, (255, 60, 0, 80), 2.8))
    base = comp(base, glow_disc(10, (255, 110, 0, 110), 2.0))
    base = comp(base, glow_disc(7, (255, 180, 0, 140), 1.4))
    # спиральные дуги
    arcs = new_canvas()
    d = ImageDraw.Draw(arcs)
    cx, cy = CENTER
    for r, col, w in ((12, (255, 100, 0, 160), 2), (9, (255, 160, 0, 180), 2)):
        bbox = (cx - r, cy - r, cx + r, cy + r)
        d.arc(bbox, start=30, end=260, fill=col, width=w)
    base = comp(base, arcs)
    return base

def icon_hellfire() -> Image.Image:
    base = new_canvas()
    # голубые душевные языки пламени + красная основа
    base = comp(base, flame_teardrop(CENTER[0], CENTER[1] + 3, 16, 22, (255, 68, 0, 170)))
    base = comp(base, flame_teardrop(CENTER[0], CENTER[1] - 2, 14, 18, (51, 68, 255, 190)))
    # отблеск
    base = comp(base, glow_disc(9, (70, 120, 255, 100), 1.4))
    return base

def icon_phoenix_rebirth() -> Image.Image:
    base = new_canvas()
    # золотое сияние
    base = comp(base, glow_disc(12, (255, 200, 60, 110), 2.2))
    # крылья феникса (две дуги)
    wings = new_canvas()
    d = ImageDraw.Draw(wings)
    cx, cy = CENTER
    for side in (-1, 1):
        pts = [(cx, cy + 2), (cx + 6*side, cy - 2), (cx + 10*side, cy + 4)]
        poly(d, pts, fill=(255, 150, 0, 200))
    # пламя-восход в центре
    base = comp(base, wings)
    base = comp(base, flame_teardrop(cx, cy + 1, 10, 16, (255, 220, 100, 230)))
    return base

def icon_solar_eclipse() -> Image.Image:
    base = new_canvas()
    # тёмный диск
    disk = new_canvas()
    d = ImageDraw.Draw(disk)
    cx, cy = CENTER
    ellipse(d, (cx - 8, cy - 8, cx + 8, cy + 8), fill=(10, 10, 12, 255))
    base = comp(base, disk)
    # корона
    base = comp(base, ring(SIZE, 8, 12, (255, 200, 40, 140), 1.6))
    # лучи короны
    base = comp(base, rays(range(0, 360, 15), 14, 1, (255, 160, 0, 110)))
    return base

def icon_soul_fire_ritual() -> Image.Image:
    base = new_canvas()
    # ритуальное кольцо с метками
    base = comp(base, ring(SIZE, 9, 12, (60, 200, 255, 120), 1.4))
    ticks = new_canvas()
    d = ImageDraw.Draw(ticks)
    cx, cy = CENTER
    for a in range(0, 360, 30):
        p1 = polar(cx, cy, 10.5, a)
        p2 = polar(cx, cy, 12, a)
        line(d, (*p1, *p2), (80, 230, 255, 200), 1)
    base = comp(base, ticks)
    # душевное пламя
    base = comp(base, flame_teardrop(cx, cy, 10, 16, (80, 200, 255, 210)))
    return base

def icon_phoenix_necromancy() -> Image.Image:
    base = new_canvas()
    # мрачное свечение
    base = comp(base, glow_disc(10, (120, 0, 0, 80), 2.0))
    # тёмные крылья
    wings = new_canvas()
    d = ImageDraw.Draw(wings)
    cx, cy = CENTER
    for side in (-1, 1):
        pts = [(cx, cy + 3), (cx + 7*side, cy), (cx + 9*side, cy + 6)]
        poly(d, pts, fill=(120, 30, 30, 230))
    base = comp(base, wings)
    # алое ядро
    base = comp(base, glow_disc(4, (255, 40, 30, 200), 0.8))
    return base

# -------------------- main --------------------

FIRE_RENDERERS = {
    "spark": icon_spark,
    "ember": icon_ember,
    "fire_bolt": icon_fire_bolt,
    "flame_burst": icon_flame_burst,
    "magma_spike": icon_magma_spike,
    "blaze_rush": icon_blaze_rush,
    "inferno": icon_inferno,
    "hellfire": icon_hellfire,
    "phoenix_rebirth": icon_phoenix_rebirth,
    "solar_eclipse": icon_solar_eclipse,
    "soul_fire_ritual": icon_soul_fire_ritual,
    "phoenix_necromancy": icon_phoenix_necromancy,
}

def main():
    root = Path(__file__).resolve().parents[2]
    out_dir = root / ASSETS_REL
    ensure_dir(out_dir)

    for name, fn in FIRE_RENDERERS.items():
        img = fn()
        out_path = out_dir / f"{name}.png"
        img.save(out_path, format="PNG")
        print(f"✔ {name}.png -> {out_path}")

    print(f"\nГотово. Создано {len(FIRE_RENDERERS)} иконок (32x32). Папка: {out_dir}")

if __name__ == "__main__":
    main()
