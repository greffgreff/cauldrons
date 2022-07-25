package com.greffgreff.cauldrons.utils;

import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class EnumBooleanProperty<T extends Enum<T>> extends BooleanProperty {

    private final T enumProperty;
    private final boolean defaultValue;

    protected EnumBooleanProperty(String name, T enumProperty, boolean defaultValue) {
        super(name);
        this.enumProperty = enumProperty;
        this.defaultValue = defaultValue;
    }

    public static <T extends Enum<T>> EnumBooleanProperty<T> create(String name, T enumProperty) {
        return new EnumBooleanProperty<>(name, enumProperty, false);
    }

    public static <T extends Enum<T>> EnumBooleanProperty<T> create(String name, T enumProperty, boolean defaultValue) {
        return new EnumBooleanProperty<>(name, enumProperty, defaultValue);
    }

    public T getEnumProperty() {
        return enumProperty;
    }

    public boolean getDefaultValue() {
        return defaultValue;
    }
}
