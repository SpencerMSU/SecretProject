# Curios API Slot Binding Fix

## Проблема (Problem)
Слоты Curios были созданы и инициализировались, но Handler не был найден. Проблема: слоты загружены, но не привязаны к игроку.

Curios slots were created and initialized, but the Handler was not found. The problem: slots were loaded but not bound to the player.

## Причина (Root Cause)
Согласно официальной документации Curios API (https://docs.illusivesoulworks.com/curios/entities/), для корректной работы слотов необходимо создать два типа файлов:

According to the official Curios API documentation, for slots to work correctly, two types of files are required:

1. **Slot Definitions** (`data/<modid>/curios/slots/<slot_name>.json`) ✅
   - Определяют доступные типы слотов, их размер, иконки и порядок
   - Define available slot types, their size, icons, and order
   - Эти файлы уже существовали в проекте
   - These files already existed in the project

2. **Entity Bindings** (`data/<modid>/curios/entities/<entity_type>.json`) ❌
   - Связывают слоты с конкретными типами сущностей (например, игрок)
   - Bind slots to specific entity types (e.g., player)
   - **Этот файл отсутствовал и был причиной проблемы!**
   - **This file was missing and was the cause of the problem!**

## Решение (Solution)

Создан файл `data/examplemod/curios/entities/player.json` со следующим содержимым:

Created file `data/examplemod/curios/entities/player.json` with the following content:

```json
{
  "slots": [
    "ring",
    "necklace",
    "bracelet",
    "belt",
    "charm",
    "back"
  ]
}
```

Этот файл явно указывает Curios API, что все определенные слоты должны быть привязаны к сущности игрока (player).

This file explicitly tells Curios API that all defined slots should be bound to the player entity.

## Структура файлов (File Structure)

```
src/main/resources/data/examplemod/curios/
├── entities/
│   └── player.json          ← Новый файл (New file)
└── slots/
    ├── back.json
    ├── belt.json
    ├── bracelet.json
    ├── charm.json
    ├── necklace.json
    └── ring.json
```

## Результат (Result)

После добавления файла `player.json`:
- ✅ Curios API теперь корректно привязывает слоты к игроку
- ✅ ICuriosItemHandler будет доступен через `CuriosApi.getCuriosInventory(player)`
- ✅ Все 8 слотов (3 ring + 5 других типов: necklace, bracelet, belt, charm, back) будут доступны в инвентаре
- ✅ Команда `/curiostest` должна показать успешную инициализацию

After adding the `player.json` file:
- ✅ Curios API now correctly binds slots to the player
- ✅ ICuriosItemHandler will be available via `CuriosApi.getCuriosInventory(player)`
- ✅ All 8 slots (3 ring + 5 other types: necklace, bracelet, belt, charm, back) will be available in inventory
- ✅ The `/curiostest` command should show successful initialization

## Важные замечания (Important Notes)

1. Без файла entities/player.json, Curios API не знает, к какой сущности привязывать слоты
   Without the entities/player.json file, Curios API doesn't know which entity to bind the slots to

2. Имя файла должно соответствовать типу сущности: `player.json` для игроков
   The file name must match the entity type: `player.json` for players

3. Список слотов в player.json должен соответствовать файлам в папке slots/
   The slots list in player.json must match the files in the slots/ folder

## Ссылки (References)

- Curios API Entity Documentation: https://docs.illusivesoulworks.com/curios/entities/
- Curios API Slot Documentation: https://docs.illusivesoulworks.com/curios/slots/
