from pathlib import Path
import math

try:
    from PIL import Image, ImageDraw, ImageFilter
except ImportError as e:
    print("Pillow (PIL) не установлен. Установите: pip install Pillow")
    raise

# Конфигурация
SIZE = 32
CENTER = (SIZE // 2, SIZE // 2)
OUTPUT_REL = Path("src/main/resources/assets/examplemod/textures/spell_icons/fire/spark.png")


def ensure_dir(path: Path):
    path.parent.mkdir(parents=True, exist_ok=True)


def polar_to_cart(cx, cy, r, angle_deg):
    ang = math.radians(angle_deg)
    return (cx + r * math.cos(ang), cy + r * math.sin(ang))


def draw_glow_layer(size: int) -> Image.Image:
    glow = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    d = ImageDraw.Draw(glow)
    # Мягкая подсветка несколькими концентрическими эллипсами
    d.ellipse((4, 4, size - 4, size - 4), fill=(255, 140, 0, 90))
    d.ellipse((6, 6, size - 6, size - 6), fill=(255, 185, 0, 110))
    d.ellipse((8, 8, size - 8, size - 8), fill=(255, 220, 0, 130))
    return glow.filter(ImageFilter.GaussianBlur(2))


def draw_ray(draw: ImageDraw.ImageDraw, cx: int, cy: int, length: float, width: int, angle: float, color):
    x2, y2 = polar_to_cart(cx, cy, length, angle)
    draw.line((cx, cy, x2, y2), fill=color, width=width, joint=None)


def render_spark(size: int = SIZE) -> Image.Image:
    img = Image.new("RGBA", (size, size), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    # Подсветка (блюм)
    glow = draw_glow_layer(size)
    img = Image.alpha_composite(img, glow)

    cx, cy = CENTER

    # Длинные лучи
    long_color = (255, 170, 0, 220)   # янтарный
    for a in (0, 90, 180, 270):
        draw_ray(draw, cx, cy, length=size * 0.42, width=2, angle=a, color=long_color)

    # Короткие диагональные лучи
    short_color = (255, 220, 80, 230)
    for a in (45, 135, 225, 315):
        draw_ray(draw, cx, cy, length=size * 0.30, width=2, angle=a, color=short_color)

    # Ядро искры (звёздочка-ромб)
    core = [
        (cx, cy - 3),
        (cx + 3, cy),
        (cx, cy + 3),
        (cx - 3, cy),
    ]
    draw.polygon(core, fill=(255, 255, 255, 255))

    # Тёплый контур ядра
    draw.polygon(core, outline=(255, 190, 0, 255))

    # Дополнительный мягкий блюм поверх
    bloom = img.filter(ImageFilter.GaussianBlur(0.8))
    img = Image.alpha_composite(bloom, img)

    return img


def main():
    # Корень проекта = папка super (две директории вверх от этого файла)
    root = Path(__file__).resolve().parents[2]
    out_path = root / OUTPUT_REL
    ensure_dir(out_path)

    icon = render_spark(SIZE)
    icon.save(out_path, format="PNG")

    print(f"Иконка сохранена: {out_path}")


if __name__ == "__main__":
    main()