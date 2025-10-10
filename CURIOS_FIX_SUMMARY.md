# Исправление интеграции Curios API

## Проблема
Слоты аксессуаров Curios не появлялись в инвентаре при нажатии на кнопку. Команда `/curiostest` показывала, что Curios API не работает.

## Причина
Файлы слотов Curios находились в неправильной папке. Они были в `data/curios/slots/` вместо правильного пути `data/examplemod/curios/slots/`.

Согласно официальной документации Curios (https://docs.illusivesoulworks.com/curios/slots/), файлы слотов должны находиться в:
```
data/<modid>/curios/slots/<slot_name>.json
```

## Выполненные исправления

### 1. Перемещение файлов слотов
Все файлы слотов были перемещены из:
- `src/main/resources/data/curios/slots/` ❌
  
В правильную папку:
- `src/main/resources/data/examplemod/curios/slots/` ✅

### 2. Исправление дублирующих слотов
Были удалены дублирующие файлы `ring1.json` и `ring2.json`. Вместо этого, в файле `ring.json` установлено значение `"size": 2`, что создаёт 2 слота для колец (это правильный способ согласно документации Curios).

### 3. Удаление неиспользуемых слотов
Удалены файлы `head.json` и `body.json`, так как эти типы слотов не определены в коде (в `AccessoryType.java`).

### 4. Добавление переводов слотов
В файлы переводов добавлены названия слотов Curios:

**Русский (ru_ru.json):**
```json
"curios.identifier.ring": "Кольцо",
"curios.identifier.necklace": "Ожерелье",
"curios.identifier.bracelet": "Браслет",
"curios.identifier.belt": "Пояс",
"curios.identifier.charm": "Амулет",
"curios.identifier.back": "Спина"
```

**Английский (en_us.json):**
```json
"curios.identifier.ring": "Ring",
"curios.identifier.necklace": "Necklace",
"curios.identifier.bracelet": "Bracelet",
"curios.identifier.belt": "Belt",
"curios.identifier.charm": "Charm",
"curios.identifier.back": "Back"
```

## Финальная структура слотов

Теперь в моде есть следующие слоты аксессуаров:

| Слот | Количество | Порядок | Иконка |
|------|------------|---------|---------|
| Ring (Кольцо) | 2 | 10 | `curios:slot/empty_ring_slot` |
| Necklace (Ожерелье) | 1 | 20 | `curios:slot/empty_necklace_slot` |
| Bracelet (Браслет) | 1 | 30 | `curios:slot/empty_bracelet_slot` |
| Belt (Пояс) | 1 | 40 | `curios:slot/empty_belt_slot` |
| Charm (Амулет) | 1 | 50 | `curios:slot/empty_charm_slot` |
| Back (Спина/Плащ) | 1 | 60 | `curios:slot/empty_back_slot` |

**Итого: 8 слотов аксессуаров** (2 слота для колец + 6 других слотов)

## Конфигурация зависимостей

Зависимости Curios уже были правильно настроены:

**build.gradle:**
```gradle
compileOnly "top.theillusivec4.curios:curios-neoforge:9.5.1+1.21.1:api"
localRuntime "top.theillusivec4.curios:curios-neoforge:9.5.1+1.21.1"
```

**neoforge.mods.toml:**
```toml
[[dependencies.${mod_id}]]
    modId="curios"
    type="required"
    versionRange="[9.5.1,)"
    ordering="AFTER"
    side="BOTH"
```

## Как проверить

После пересборки мода:

1. Запустите игру
2. Зайдите в мир
3. Откройте инвентарь (E)
4. Нажмите на кнопку Curios (обычно справа вверху от инвентаря)
5. Вы должны увидеть 8 слотов аксессуаров: 2 для колец и по одному для остальных типов
6. Также можете проверить команду `/curiostest` - она должна показать, что Curios API работает и доступно 8 слотов

## Важные замечания

- Всегда следуйте официальной документации Curios: https://docs.illusivesoulworks.com/
- Файлы слотов ДОЛЖНЫ быть в `data/<modid>/curios/slots/`, а не в `data/curios/slots/`
- Для создания нескольких слотов одного типа используйте параметр `"size"`, а не создавайте несколько файлов
- Названия файлов слотов должны соответствовать идентификаторам, используемым в коде
