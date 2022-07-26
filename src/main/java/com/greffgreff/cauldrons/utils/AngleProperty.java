package com.greffgreff.cauldrons.utils;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public enum AngleProperty {
    NORTH_EAST(Direction.NORTH, Direction.EAST, BooleanProperty.create("north_east_angled")),
    NORTH_WEST(Direction.NORTH, Direction.WEST, BooleanProperty.create("north_west_angled")),
    NORTH_UP(Direction.NORTH, Direction.UP, BooleanProperty.create("north_up_angled")),
    NORTH_DOWN(Direction.NORTH, Direction.DOWN, BooleanProperty.create("north_down_angled")),
    SOUTH_EAST(Direction.SOUTH, Direction.EAST, BooleanProperty.create("south_east_angled")),
    SOUTH_WEST(Direction.SOUTH, Direction.WEST, BooleanProperty.create("south_west_angled")),
    SOUTH_UP(Direction.SOUTH, Direction.UP, BooleanProperty.create("south_up_angled")),
    SOUTH_DOWN(Direction.SOUTH, Direction.DOWN, BooleanProperty.create("south_down_angled")),
    WEST_UP(Direction.WEST, Direction.UP, BooleanProperty.create("west_up_angled")),
    WEST_DOWN(Direction.WEST, Direction.DOWN, BooleanProperty.create("west_down_angled")),
    EAST_UP(Direction.EAST, Direction.UP, BooleanProperty.create("east_up_angled")),
    EAST_DOWN(Direction.EAST, Direction.DOWN, BooleanProperty.create("east_down_angled"));

    private final Direction firstDirection;
    private final Direction secondDirection;
    private final BooleanProperty property;

    AngleProperty(Direction firstDirection, Direction secondDirection, BooleanProperty property) {
        this.firstDirection = firstDirection;
        this.secondDirection = secondDirection;
        this.property = property;
    }

    public Direction getFirstDirection() {
        return firstDirection;
    }

    public Direction getSecondDirection() {
        return secondDirection;
    }

    public BooleanProperty get() {
        return property;
    }
}
