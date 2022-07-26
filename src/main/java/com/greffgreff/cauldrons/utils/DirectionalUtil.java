package com.greffgreff.cauldrons.utils;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Direction;

import java.util.Arrays;

public final class DirectionalUtil {

    public static int getRelativeRotation(Direction direction) {
        return (direction.get2DDataValue()) * 90;
    }

    public static Pair<Direction, Direction> getAdjacentSidesByDirection(Direction direction) {
        return switch (direction) {
            case DOWN, UP -> Pair.of(null, null);
            case NORTH -> Pair.of(Direction.WEST, Direction.EAST);
            case SOUTH -> Pair.of(Direction.EAST, Direction.WEST);
            case WEST -> Pair.of(Direction.SOUTH, Direction.NORTH);
            case EAST -> Pair.of(Direction.NORTH, Direction.SOUTH);
        };
    }

    public static Direction[] getHorizontalDirections() {
        return Arrays.stream(Direction.values()).filter(d -> d != Direction.DOWN && d != Direction.UP).toArray(Direction[]::new);
    }

    public static Direction[] getVerticalDirections() {
        return Arrays.stream(Direction.values()).filter(d -> d == Direction.DOWN || d == Direction.UP).toArray(Direction[]::new);
    }

}
