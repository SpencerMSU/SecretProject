# Исправление валидации слотов для аксессуаров

## Проблема

NBT теги добавлялись к аксессуарам через `CustomData` компонент, но они не работали так, как если бы аксессуары добавлялись через команду. Причина: мод **Accessories** ожидает валидацию слотов через специальный **DataComponent**, а не через NBT теги в `CustomData`.

## Решение

Использовать правильный подход с `SLOT_VALIDATION` DataComponent из мода Accessories:

### Что было (НЕПРАВИЛЬНО):
```java
// Создание NBT тегов в CustomData
CompoundTag mainTag = new CompoundTag();
CompoundTag slotValidation = new CompoundTag();
ListTag validSlots = new ListTag();
validSlots.add(StringTag.valueOf("anklet"));
slotValidation.put("valid_slots", validSlots);
mainTag.put("accessories:slot_validation", slotValidation);
CustomData customData = CustomData.of(mainTag);
stack.set(DataComponents.CUSTOM_DATA, customData);
```

### Что стало (ПРАВИЛЬНО):
```java
// Использование SLOT_VALIDATION DataComponent через рефлексию
Class<?> dataComponentsClass = Class.forName("io.wispforest.accessories.api.AccessoriesDataComponents");
java.lang.reflect.Field slotValidationField = dataComponentsClass.getDeclaredField("SLOT_VALIDATION");
slotValidationField.setAccessible(true);
DataComponentType<Object> slotValidationType = (DataComponentType<Object>) slotValidationField.get(null);

// Создание SlotValidation объекта
Set<String> validSlots = new HashSet<>();
validSlots.add("anklet");
Set<String> invalidSlots = new HashSet<>();

Class<?> slotValidationClass = Class.forName("io.wispforest.accessories.api.data.SlotValidation");
Constructor<?> constructor = slotValidationClass.getDeclaredConstructor(Set.class, Set.class);
constructor.setAccessible(true);
Object slotValidation = constructor.newInstance(validSlots, invalidSlots);

// Установка компонента
stack.set((DataComponentType) slotValidationType, slotValidation);
```

## Измененные файлы

### 1. `BaseAccessoryItem.java`
- **Метод `getDefaultInstance()`**: Переписан для использования `SLOT_VALIDATION` DataComponent вместо NBT тегов
- **Метод `applyDefaultNBT()`**: Удалена логика добавления NBT тегов для slot validation

### 2. `TestAnkletItem.java`
- **Метод `getDefaultInstance()`**: Обновлен для использования `SLOT_VALIDATION` DataComponent

### 3. `CreativeTabHandler.java`
- **Метод `onBuildCreativeTab()`**: Удалена логика добавления NBT тегов (теперь это делается в `getDefaultInstance()`)

## Почему это работает

1. **DataComponent vs NBT**: Мод Accessories использует систему DataComponent от Minecraft, а не NBT теги в CustomData для валидации слотов
2. **Правильная структура**: `SlotValidation` объект содержит два `Set<String>`: `validSlots` и `invalidSlots`
3. **Рефлексия**: Используется рефлексия для доступа к классам и полям мода Accessories, так как они не всегда доступны напрямую в compile-time
4. **Согласованность**: Теперь аксессуары, созданные в коде, работают так же, как аксессуары, добавленные через команды

## Тестирование

После этих изменений аксессуары должны:
1. ✅ Корректно показываться в creative tabs
2. ✅ Быть экипируемыми только в правильные слоты
3. ✅ Работать идентично аксессуарам, добавленным через команды
4. ✅ Иметь правильную валидацию слотов при проверке мода Accessories

## Дополнительные замечания

- Метод `createForCreativeTabWithSlot()` в `BaseAccessoryItem` уже использовал правильный подход и был взят за основу
- CustomData все еще используется для хранения уровня (`AccessoryLevel`) и редкости (`AccessoryRarity`) аксессуаров
- Debug логи сохранены для отладки работы системы
