package com.greffgreff.cauldrons.utils;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.Arrays;

public enum DirectionalProperty {
    NORTH(Direction.NORTH, BooleanProperty.create("northwards_connected")),
    EAST(Direction.EAST, BooleanProperty.create("eastwards_connected")),
    SOUTH(Direction.SOUTH, BooleanProperty.create("southwards_connected")),
    WEST(Direction.WEST, BooleanProperty.create("westwards_connected")),
    UP(Direction.UP, BooleanProperty.create("upwards_connected")),
    DOWN(Direction.DOWN, BooleanProperty.create("downwards_connected"));

    private final Direction direction;
    private final BooleanProperty property;

    DirectionalProperty(Direction direction, BooleanProperty property) {
        this.direction = direction;
        this.property = property;
    }

    public Direction getDirection() {
        return direction;
    }

    public BooleanProperty get() {
        return property;
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

    public Pair<DirectionalProperty, DirectionalProperty> getAdjacent() {
        return switch (this) {
            case DOWN, UP -> Pair.of(null, null);
            case NORTH -> Pair.of(WEST, EAST);
            case SOUTH -> Pair.of(SOUTH, WEST);
            case WEST -> Pair.of(SOUTH, NORTH);
            case EAST -> Pair.of(NORTH, SOUTH);
        };
    }

    public static DirectionalProperty getFromDirection(Direction direction) {
        return Arrays.stream(DirectionalProperty.values()).filter(p -> p.getDirection() == direction).findFirst().orElse(null);
    }
}
