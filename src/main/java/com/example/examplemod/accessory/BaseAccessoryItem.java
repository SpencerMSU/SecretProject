package com.example.examplemod.accessory;

import io.wispforest.accessories.api.slot.SlotReference;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.AccessoriesCapability;
import com.example.examplemod.items.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.common.util.Lazy;
import net.minecraft.nbt.ListTag;

import java.util.List;

/**
 * Базовый класс для всех аксессуаров
 * Поддерживает: уровни прокачки (1-10), редкость, NBT данные
 */
public abstract class BaseAccessoryItem extends Item implements Accessory {
    protected final AccessoryType accessoryType;
    protected final AccessoryElement element;
    protected final Lazy<AccessoryStats> baseStats;

    // NBT ключи
    public static final String NBT_LEVEL = "AccessoryLevel";
    public static final String NBT_RARITY = "AccessoryRarity";
    
    // Кэшированные рефлексивные объекты для работы с Accessories mod
    private static Class<?> dataComponentsClass;
    private static net.minecraft.core.component.DataComponentType<?> slotValidationType;
    private static Class<?> slotValidationClass;
    private static java.lang.reflect.Constructor<?> slotValidationConstructor;
    private static boolean reflectionInitialized = false;
    private static boolean reflectionFailed = false;
    
    // Пустой набор для invalidSlots (переиспользуется)
    private static final java.util.Set<String> EMPTY_INVALID_SLOTS = java.util.Collections.emptySet();
    
    /**
     * Инициализирует рефлексивный доступ к классам Accessories мода
     * Вызывается один раз при первом использовании
     */
    private static synchronized void initializeReflection() {
        if (reflectionInitialized || reflectionFailed) {
            return;
        }
        
        try {
            // Получаем класс AccessoriesDataComponents
            dataComponentsClass = Class.forName("io.wispforest.accessories.api.AccessoriesDataComponents");
            
            // Получаем поле SLOT_VALIDATION
            java.lang.reflect.Field slotValidationField = dataComponentsClass.getDeclaredField("SLOT_VALIDATION");
            slotValidationField.setAccessible(true);
            slotValidationType = (net.minecraft.core.component.DataComponentType<?>) slotValidationField.get(null);
            
            // Получаем класс и конструктор SlotValidation
            slotValidationClass = Class.forName("io.wispforest.accessories.api.data.SlotValidation");
            slotValidationConstructor = slotValidationClass.getDeclaredConstructor(java.util.Set.class, java.util.Set.class);
            slotValidationConstructor.setAccessible(true);
            
            reflectionInitialized = true;
            System.out.println("Successfully initialized reflection for Accessories mod");
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: Accessories mod classes not found. Is the mod installed?");
            reflectionFailed = true;
        } catch (NoSuchFieldException | NoSuchMethodException e) {
            System.err.println("ERROR: Accessories mod API changed. Reflection failed: " + e.getMessage());
            reflectionFailed = true;
        } catch (Exception e) {
            System.err.println("ERROR: Failed to initialize reflection for Accessories mod");
            e.printStackTrace();
            reflectionFailed = true;
        }
    }
    
    /**
     * Устанавливает SLOT_VALIDATION DataComponent для ItemStack
     * @param stack ItemStack для установки слотов
     * @param validSlots Набор валидных слотов
     * @return true если успешно, false если произошла ошибка
     */
    protected static boolean setSlotValidation(ItemStack stack, java.util.Set<String> validSlots) {
        if (stack == null) {
            System.err.println("ERROR: Cannot set slot validation - stack is null");
            return false;
        }
        
        if (validSlots == null || validSlots.isEmpty()) {
            System.err.println("ERROR: Cannot set slot validation - validSlots is null or empty");
            return false;
        }
        
        initializeReflection();
        
        if (reflectionFailed) {
            System.err.println("ERROR: Cannot set slot validation - reflection initialization failed");
            return false;
        }
        
        try {
            Object slotValidation = slotValidationConstructor.newInstance(validSlots, EMPTY_INVALID_SLOTS);
            
            @SuppressWarnings("unchecked")
            net.minecraft.core.component.DataComponentType<Object> rawType = 
                (net.minecraft.core.component.DataComponentType<Object>) slotValidationType;
            stack.set(rawType, slotValidation);
            
            return true;
        } catch (Exception e) {
            System.err.println("ERROR: Failed to set SlotValidation component");
            e.printStackTrace();
            return false;
        }
    }

    public BaseAccessoryItem(AccessoryType type, AccessoryElement element, Properties properties) {
        super(applyDefaultNBT(properties.stacksTo(1), type)); // Аксессуары не стакаются
        this.accessoryType = type;
        this.element = element;
        
        System.out.println("========================================");
        System.out.println("BaseAccessoryItem constructor");
        System.out.println("Type: " + type);
        System.out.println("Element: " + element);
        System.out.println("Compatible slots: " + String.join(", ", type.getCompatibleSlots()));
        System.out.println("========================================");
        
        // Ленивая инициализация базовых статов
        this.baseStats = Lazy.of(() -> {
            if (element == AccessoryElement.FIRE) {
                return AccessoryStats.createFireStats(type);
            } else {
                return AccessoryStats.createWaterStats(type);
            }
        });
    }
    
    /**
     * Применить дефолтные настройки к Properties
     * Примечание: Slot validation теперь устанавливается через DataComponent в getDefaultInstance()
     */
    private static Properties applyDefaultNBT(Properties properties, AccessoryType type) {
        System.out.println("Applying default properties for " + type);
        // Аксессуары больше не используют NBT для slot validation
        // Вместо этого используется SLOT_VALIDATION DataComponent
        return properties;
    }
    
    @Override
    public ItemStack getDefaultInstance() {
        System.out.println("========================================");
        System.out.println(">>> getDefaultInstance() CALLED <<<");
        System.out.println("Class: " + this.getClass().getSimpleName());
        System.out.println("Type: " + accessoryType);
        System.out.println("Element: " + element);
        
        ItemStack stack = super.getDefaultInstance();
        
        // ВАЖНО! Accessories мод ищет тег НА УРОВНЕ КОМПОНЕНТОВ, а не в custom_data!
        // Используем специальный DataComponent от мода Accessories
        java.util.Set<String> validSlots = new java.util.HashSet<>();
        for (String slot : accessoryType.getCompatibleSlots()) {
            validSlots.add(slot);
            System.out.println("  Adding valid slot: " + slot);
        }
        
        if (setSlotValidation(stack, validSlots)) {
            System.out.println("  SlotValidation DataComponent successfully set!");
            System.out.println("  Valid slots: " + validSlots);
        } else {
            System.err.println("  WARNING: Failed to set SlotValidation DataComponent");
        }
        
        System.out.println(">>> getDefaultInstance() FINISHED <<<");
        System.out.println("========================================");
        
        return stack;
    }
    

    public AccessoryType getAccessoryType() {
        return accessoryType;
    }

    public AccessoryElement getElement() {
        return element;
    }

    /**
     * Получить уровень аксессуара (1-10)
     */
    public static int getLevel(ItemStack stack) {
        if (stack.isEmpty()) return 1;
        
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        int level = customData.copyTag().getInt(NBT_LEVEL);
        return level > 0 ? level : 1; // По умолчанию 1 если не установлен
    }

    /**
     * Установить уровень аксессуара
     */
    public static void setLevel(ItemStack stack, int level) {
        int finalLevel = Math.clamp(level, 1, 10);
        stack.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY, data -> 
            data.update(tag -> tag.putInt(NBT_LEVEL, finalLevel))
        );
    }

    /**
     * Получить редкость аксессуара
     */
    public static AccessoryRarity getRarity(ItemStack stack) {
        if (stack.isEmpty()) return AccessoryRarity.COMMON;
        
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        int ordinal = customData.copyTag().getInt(NBT_RARITY);
        
        if (ordinal >= 0 && ordinal < AccessoryRarity.values().length) {
            return AccessoryRarity.values()[ordinal];
        }
        return AccessoryRarity.COMMON;
    }

    /**
     * Установить редкость аксессуара
     */
    public static void setRarity(ItemStack stack, AccessoryRarity rarity) {
        stack.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY, data -> 
            data.update(tag -> tag.putInt(NBT_RARITY, rarity.ordinal()))
        );
    }

    /**
     * Получить финальные характеристики с учетом уровня и редкости
     */
    public AccessoryStats getStats(ItemStack stack) {
        int level = getLevel(stack);
        AccessoryRarity rarity = getRarity(stack);
        return baseStats.get().calculate(level, rarity);
    }

    /**
     * Можно ли улучшить этот аксессуар
     */
    public static boolean canUpgrade(ItemStack stack) {
        return getLevel(stack) < 10;
    }

    /**
     * Улучшить аксессуар на 1 уровень
     */
    public static boolean upgrade(ItemStack stack) {
        if (!canUpgrade(stack)) {
            return false;
        }
        setLevel(stack, getLevel(stack) + 1);
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        
        int level = getLevel(stack);
        AccessoryRarity rarity = getRarity(stack);
        AccessoryStats stats = getStats(stack);

        // Уровень и редкость
        tooltipComponents.add(Component.literal("━━━━━━━━━━━━━━━━━━━━").withStyle(ChatFormatting.DARK_GRAY));
        
        tooltipComponents.add(Component.literal("Уровень: ")
            .withStyle(ChatFormatting.GRAY)
            .append(Component.literal(level + "/10")
                .withStyle(level == 10 ? ChatFormatting.GOLD : ChatFormatting.WHITE)));
        
        tooltipComponents.add(Component.literal("Редкость: ")
            .withStyle(ChatFormatting.GRAY)
            .append(Component.literal(rarity.getRussianName())
                .withStyle(style -> style.withColor(rarity.getColor()))));

        tooltipComponents.add(Component.literal("Стихия: ")
            .withStyle(ChatFormatting.GRAY)
            .append(Component.literal(element.getRussianName())
                .withStyle(style -> style.withColor(element.getColor()))));

        // Характеристики
        tooltipComponents.add(Component.literal("━━━━━━━━━━━━━━━━━━━━").withStyle(ChatFormatting.DARK_GRAY));
        
        if (stats.getHealth() > 0) {
            tooltipComponents.add(Component.literal("❤ Здоровье: ")
                .withStyle(ChatFormatting.RED)
                .append(Component.literal("+" + String.format("%.1f", stats.getHealth()))
                    .withStyle(ChatFormatting.WHITE)));
        }
        
        if (stats.getMana() > 0) {
            tooltipComponents.add(Component.literal("✦ Мана: ")
                .withStyle(ChatFormatting.AQUA)
                .append(Component.literal("+" + String.format("%.1f", stats.getMana()))
                    .withStyle(ChatFormatting.WHITE)));
        }
        
        if (stats.getManaRegen() > 0) {
            tooltipComponents.add(Component.literal("↻ Реген маны: ")
                .withStyle(ChatFormatting.BLUE)
                .append(Component.literal("+" + String.format("%.1f", stats.getManaRegen()) + "/сек")
                    .withStyle(ChatFormatting.WHITE)));
        }
        
        if (stats.getDamage() > 0) {
            tooltipComponents.add(Component.literal("⚔ Урон: ")
                .withStyle(ChatFormatting.RED)
                .append(Component.literal("+" + String.format("%.1f", stats.getDamage()))
                    .withStyle(ChatFormatting.WHITE)));
        }
        
        if (stats.getDefense() > 0) {
            tooltipComponents.add(Component.literal("🛡 Защита: ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal("+" + String.format("%.1f", stats.getDefense()))
                    .withStyle(ChatFormatting.WHITE)));
        }
        
        if (stats.getSpeed() > 0) {
            tooltipComponents.add(Component.literal("➤ Скорость: ")
                .withStyle(ChatFormatting.WHITE)
                .append(Component.literal("+" + String.format("%.3f", stats.getSpeed()))
                    .withStyle(ChatFormatting.WHITE)));
        }
        
        if (stats.getElementalPower() > 0) {
            tooltipComponents.add(Component.literal("✨ Сила стихии: ")
                .withStyle(style -> style.withColor(element.getColor()))
                .append(Component.literal("+" + String.format("%.1f", stats.getElementalPower()))
                    .withStyle(ChatFormatting.WHITE)));
        }
        
        if (stats.getElementalResistance() > 0) {
            tooltipComponents.add(Component.literal("⛨ Сопротивление: ")
                .withStyle(style -> style.withColor(element.getColor()))
                .append(Component.literal("+" + String.format("%.1f", stats.getElementalResistance()))
                    .withStyle(ChatFormatting.WHITE)));
        }

        // Подсказки
        tooltipComponents.add(Component.literal("━━━━━━━━━━━━━━━━━━━━").withStyle(ChatFormatting.DARK_GRAY));
        
        if (canUpgrade(stack)) {
            tooltipComponents.add(Component.literal("💎 Можно улучшить камнем заточки")
                .withStyle(ChatFormatting.GREEN));
        } else {
            tooltipComponents.add(Component.literal("⭐ Максимальный уровень!")
                .withStyle(ChatFormatting.GOLD));
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        AccessoryRarity rarity = getRarity(stack);
        int level = getLevel(stack);
        
        String prefix = level > 0 ? "[+" + level + "] " : "";
        
        return Component.literal(prefix)
            .append(super.getName(stack))
            .withStyle(style -> style.withColor(rarity.getColor()));
    }

    // Accessories API методы
    @Override
    public void onEquip(ItemStack stack, SlotReference reference) {
        // Аксессуар экипирован
        System.out.println("onEquip called for " + this.getClass().getSimpleName() + 
                          " in slot " + reference.slotName());
    }
    
    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        // Аксессуар снят
        System.out.println("onUnequip called for " + this.getClass().getSimpleName() + 
                          " in slot " + reference.slotName());
    }
    
    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        // Аксессуар работает в слоте
    }
    
    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        // Временно разрешаем экипировку в любой слот для тестирования
        // Добавляем логирование для отладки
        System.out.println("canEquip called for " + this.getClass().getSimpleName() + 
                          " in slot " + reference.slotName());
        return true;
    }
    
    @Override
    public boolean canUnequip(ItemStack stack, SlotReference reference) {
        return true;
    }

    /**
     * Получить русское название слота
     */
    private String getSlotDisplayName(String slotName) {
        return switch (slotName) {
            case "ring" -> "Кольцо";
            case "necklace" -> "Ожерелье";
            case "bracelet" -> "Браслет";
            case "belt" -> "Пояс";
            case "charm" -> "Амулет";
            case "back" -> "Плащ";
            case "earring" -> "Серьга";
            case "anklet" -> "Ножной браслет";
            case "cape" -> "Накидка";
            default -> slotName;
        };
    }


    /**
     * Получить Item для конкретного типа и стихии аксессуара
     */
    private static Item getAccessoryItem(AccessoryType type, AccessoryElement element) {
        if (element == AccessoryElement.FIRE) {
            return switch (type) {
                case RING -> ModItems.FIRE_RING.get();
                case NECKLACE -> ModItems.FIRE_NECKLACE.get();
                case BRACELET -> ModItems.FIRE_BRACELET.get();
                case BELT -> ModItems.FIRE_BELT.get();
                case CHARM -> ModItems.FIRE_CHARM.get();
                case CLOAK -> ModItems.FIRE_CLOAK.get();
            };
        } else { // WATER
            return switch (type) {
                case RING -> ModItems.WATER_RING.get();
                case NECKLACE -> ModItems.WATER_NECKLACE.get();
                case BRACELET -> ModItems.WATER_BRACELET.get();
                case BELT -> ModItems.WATER_BELT.get();
                case CHARM -> ModItems.WATER_CHARM.get();
                case CLOAK -> ModItems.WATER_CLOAK.get();
            };
        }
    }

    /**
     * Создать ItemStack аксессуара с ОДНИМ конкретным слотом для креативных вкладок
     * Использует SLOT_VALIDATION DataComponent для установки валидных слотов
     */
    public static ItemStack createForCreativeTab(BaseAccessoryItem item) {
        // Используем PRIMARY slot (первый в списке совместимых)
        String primarySlot = item.getAccessoryType().getSlotName();
        return createForCreativeTabWithSlot(item, primarySlot);
    }
    
    /**
     * Создать ItemStack с конкретным слотом
     */
    public static ItemStack createForCreativeTabWithSlot(BaseAccessoryItem item, String specificSlot) {
        System.out.println("========================================");
        System.out.println(">>> createForCreativeTabWithSlot() <<<");
        System.out.println("Item: " + item.getClass().getSimpleName());
        System.out.println("Type: " + item.getAccessoryType());
        System.out.println("Element: " + item.getElement());
        System.out.println("Specific slot: " + specificSlot);
        
        ItemStack stack = new ItemStack(item);
        
        // ВАЖНО! Accessories мод ищет тег НА УРОВНЕ КОМПОНЕНТОВ, а не в custom_data!
        // Используем утилитарный метод для установки SLOT_VALIDATION DataComponent
        java.util.Set<String> validSlots = new java.util.HashSet<>();
        validSlots.add(specificSlot);
        
        if (setSlotValidation(stack, validSlots)) {
            System.out.println("  Adding ONLY slot: " + specificSlot + " via DataComponent!");
            System.out.println("  SlotValidation successfully set!");
        } else {
            System.err.println("  WARNING: Failed to set SlotValidation DataComponent");
        }
        
        System.out.println(">>> createForCreativeTabWithSlot() DONE <<<");
        System.out.println("========================================");
        
        return stack;
    }
}
