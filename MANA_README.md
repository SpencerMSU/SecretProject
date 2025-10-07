# 🔮 Mana System - Complete Implementation

## Overview / Обзор

This mod includes a **fully functional, customizable mana system** with a beautiful adaptive UI. The system is built as a **flexible API/library** (constructor pattern), not a rigid solution, allowing you to create custom mana systems for your needs.

Этот мод включает **полностью функциональную, настраиваемую систему маны** с красивым адаптивным UI. Система построена как **гибкое API/библиотека** (паттерн конструктор), а не жесткое решение, позволяя создавать кастомные системы маны под ваши нужды.

---

## 📚 Documentation Files / Файлы Документации

1. **[MANA_QUICK_START.md](MANA_QUICK_START.md)** ⭐ **START HERE!**
   - Quick start guide with real-world examples
   - Руководство по быстрому старту с реальными примерами
   - Perfect for getting started quickly

2. **[MANA_SYSTEM.md](MANA_SYSTEM.md)** 📖 **Complete API Reference**
   - Full API documentation with all methods
   - Полная документация API со всеми методами
   - Usage examples and integration guide

3. **[MANA_BAR_UI.md](MANA_BAR_UI.md)** 🎨 **UI Design Guide**
   - Visual design specifications
   - Спецификации визуального дизайна
   - Customization options

4. **[VISUAL_MOCKUP.md](VISUAL_MOCKUP.md)** 🖼️ **Visual Preview**
   - Detailed visual mockups of the mana bar
   - Подробные визуальные макеты манабара
   - Shows exactly what it looks like in-game

5. **[IMPLEMENTATION_SUMMARY.md](IMPLEMENTATION_SUMMARY.md)** 📊 **Technical Summary**
   - Complete implementation details
   - Полные детали реализации
   - Architecture and technical specifications

---

## ⚡ Quick Start / Быстрый Старт

### Using Mana in Your Code / Использование маны в коде

```java
import com.example.examplemod.mana.ManaHelper;

// Check and consume mana / Проверить и использовать ману
if (ManaHelper.consumeMana(player, 30.0f)) {
    // Cast spell / Использовать заклинание
    player.displayClientMessage(Component.literal("Spell cast!"), true);
}
```

### Creating Custom Mana System / Создание кастомной системы маны

```java
import com.example.examplemod.mana.ManaDataBuilder;

ManaData customMana = ManaDataBuilder.create()
    .setMaxMana(200.0f)      // Max mana / Максимум маны
    .setRegenRate(0.3f)      // Regen speed / Скорость регенерации
    .setColor(0xFF8B00FF)    // Purple color / Фиолетовый цвет
    .build();
```

---

## ✨ Features / Возможности

### 🏗️ Library Features / Возможности Библиотеки
- ✅ **Builder Pattern** - Easy construction of custom mana systems
- ✅ **Interface-based** - Flexible and extensible design
- ✅ **NBT Persistence** - Automatic save/load
- ✅ **Client-Server Sync** - Automatic synchronization
- ✅ **Event System** - Regeneration and lifecycle management

### 🎨 UI Features / Возможности UI
- ✅ **Adaptive Position** - Right side of screen, works on any resolution
- ✅ **Custom Colors** - Different colors for different magic types
- ✅ **Smooth Animation** - Visual feedback for mana changes
- ✅ **Text Display** - Shows current/max mana values
- ✅ **Professional Polish** - Shadows, borders, proper alignment

### 🔧 Testing Tools / Инструменты Тестирования
- ✅ **Test Item** - `ManaTestItem` for demonstration
- ✅ **Debug Commands** - `/mana get|set|add|setmax`
- ✅ **Examples** - Complete code examples in documentation

---

## 📦 What's Included / Что Включено

### Core System / Основная Система
```
mana/
├── IManaData.java              - Core interface
├── ManaData.java               - Implementation
├── ManaDataBuilder.java        - Builder pattern
├── ManaHelper.java             - Utility methods
├── ModAttachments.java         - Data attachment registration
├── PlayerManaProvider.java     - NBT serialization
└── ManaEvents.java             - Event handlers
```

### Client Rendering / Рендеринг Клиента
```
client/
└── ManaBarOverlay.java         - UI overlay renderer
```

### Networking / Сетевое Взаимодействие
```
network/
└── SyncManaDataPacket.java     - Client-server sync packet
```

### Testing / Тестирование
```
items/
└── ManaTestItem.java           - Test item (consumes mana)

commands/
└── ManaCommands.java           - Debug commands
```

---

## 🎮 Testing / Тестирование

### Commands / Команды
```bash
/mana get              # Show current mana / Показать текущую ману
/mana set 50           # Set mana to 50 / Установить ману на 50
/mana add 25           # Add 25 mana / Добавить 25 маны
/mana setmax 200       # Set max to 200 / Установить максимум на 200
```

### Test Item / Тестовый Предмет
```bash
/give @s examplemod:mana_test_item
```
Right-click to consume 20 mana / ПКМ чтобы использовать 20 маны

---

## 🎨 UI Preview / Предварительный Просмотр UI

The mana bar appears on the **right side** of the screen:

Манабар появляется **справа** на экране:

```
                                                    Mana
                                              ┌────────────┐
                                              │████████░░░░│  75/100
                                              └────────────┘
```

**Position / Позиция:**
- 10 pixels from right edge / 10 пикселей от правого края
- 50 pixels from top / 50 пикселей от верха
- Adapts to any screen resolution / Адаптируется к любому разрешению

---

## 🔍 Architecture / Архитектура

```
Your Item/Ability
       ↓
   ManaHelper (Simple API)
       ↓
   IManaData Interface
       ↓
   ManaData Implementation
       ↓
ModAttachments.PLAYER_MANA
       ↓
  ┌────┴────┐
  ↓         ↓
Server    Client
Events    Overlay
```

---

## 🚀 Default Configuration / Конфигурация по Умолчанию

- **Max Mana / Максимум маны:** 100
- **Regen Rate / Скорость регенерации:** 0.1 per tick (2 per second / в секунду)
- **Color / Цвет:** Blue #00A0FF (Синий)

---

## 📖 Usage Examples / Примеры Использования

### Example 1: Magic Wand / Магическая Палочка
```java
if (ManaHelper.consumeMana(player, 25.0f)) {
    // Cast fireball
    LargeFireball fireball = new LargeFireball(...);
    level.addFreshEntity(fireball);
}
```

### Example 2: Mana Potion / Зелье Маны
```java
ManaHelper.addMana(player, 50.0f);
player.displayClientMessage(Component.literal("Restored 50 mana!"), true);
```

### Example 3: Custom Mage Class / Кастомный Класс Мага
```java
IManaData mana = player.getData(ModAttachments.PLAYER_MANA);
mana.setMaxMana(200.0f);
mana.setRegenRate(0.5f);
mana.setColor(0xFF8B00FF); // Purple
```

---

## 📝 Documentation Index / Индекс Документации

| File | Description | Use Case |
|------|-------------|----------|
| [MANA_QUICK_START.md](MANA_QUICK_START.md) | Quick start with examples | Start here for immediate use |
| [MANA_SYSTEM.md](MANA_SYSTEM.md) | Complete API reference | Full documentation |
| [MANA_BAR_UI.md](MANA_BAR_UI.md) | UI design guide | Understanding the UI |
| [VISUAL_MOCKUP.md](VISUAL_MOCKUP.md) | Visual mockups | See what it looks like |
| [IMPLEMENTATION_SUMMARY.md](IMPLEMENTATION_SUMMARY.md) | Technical summary | Implementation details |

---

## 💡 Key Benefits / Ключевые Преимущества

1. **Flexible API** - Builder pattern for easy customization / Паттерн Builder для легкой настройки
2. **Beautiful UI** - Professional, polished mana bar / Профессиональный, отполированный манабар
3. **Adaptive** - Works on any screen resolution / Работает на любом разрешении
4. **Automatic** - Regeneration, sync, persistence all handled / Регенерация, синхронизация, сохранение - всё автоматически
5. **Well Documented** - Complete documentation with examples / Полная документация с примерами
6. **Easy to Use** - Simple API with helper methods / Простое API со вспомогательными методами

---

## 🤝 Integration / Интеграция

This mana system can be used by:
- Magic mods / Моды с магией
- RPG systems / RPG системы
- Ability systems / Системы способностей
- Custom game modes / Кастомные игровые режимы
- Any mod needing resource management / Любой мод требующий управление ресурсами

Simply use `ManaHelper` in your code and the system handles the rest!

Просто используйте `ManaHelper` в коде и система сделает остальное!

---

## ✅ Status / Статус

**Implementation: COMPLETE ✅**

All features are implemented, tested, and documented.

Все функции реализованы, протестированы и задокументированы.

---

Made with ❤️ for the Minecraft modding community
