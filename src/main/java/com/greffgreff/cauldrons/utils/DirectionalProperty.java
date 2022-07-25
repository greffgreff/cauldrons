package com.greffgreff.cauldrons.utils;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.Arrays;

public enum DirectionalProperty {
    NORTH(Direction.NORTH, BooleanProperty.create("northwards_connected"), 0, 0),
    SOUTH(Direction.SOUTH, BooleanProperty.create("southwards_connected"), 180, 0),
    EAST(Direction.EAST, BooleanProperty.create("eastwards_connected"), 90, 0),
    WEST(Direction.WEST, BooleanProperty.create("westwards_connected"), 270, 0),
    UP(Direction.UP, BooleanProperty.create("upwards_connected"), 0, 0),
    DOWN(Direction.DOWN, BooleanProperty.create("downwards_connected"), 0, 180);

    private final Direction direction;
    private final BooleanProperty property;
    private final int relativeYRotation;
    private final int relativeXRotation;

    DirectionalProperty(Direction direction, BooleanProperty property, int relativeYRotation, int relativeXRotation) {
        this.direction = direction;
        this.property = property;
        this.relativeYRotation = relativeYRotation;
        this.relativeXRotation = relativeXRotation;
    }

    public Direction getDirection() {
        return direction;
    }

    public BooleanProperty get() {
        return property;
    }

    public int getRelativeYRotation() {
        return relativeYRotation;
    }

    public int getRelativeXRotation() {
        return relativeXRotation;
    }

    public DirectionalProperty getOpposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case EAST -> WEST;
            case SOUTH -> NORTH;
            case WEST -> EAST;
            case UP -> DOWN;
            case DOWN -> UP;
        };
    }

    public Pair<DirectionalProperty, DirectionalProperty> getAdjacentSides() {
        return switch (this) {
            case DOWN, UP -> Pair.of(null, null);
            case NORTH -> Pair.of(WEST, EAST);
            case SOUTH -> Pair.of(EAST, WEST);
            case WEST -> Pair.of(SOUTH, NORTH);
            case EAST -> Pair.of(NORTH, SOUTH);
        };
    }

    public static DirectionalProperty getFromDirection(Direction direction) {
        return Arrays.stream(DirectionalProperty.values()).filter(p -> p.getDirection() == direction).findFirst().orElse(null);
    }
}
