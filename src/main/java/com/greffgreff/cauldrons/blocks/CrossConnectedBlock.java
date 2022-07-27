package com.greffgreff.cauldrons.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class CrossConnectedBlock extends Block {
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final BooleanProperty NORTH_WEST = BooleanProperty.create("north_west");
    public static final BooleanProperty NORTH_EAST = BooleanProperty.create("north_east");
    public static final BooleanProperty SOUTH_WEST = BooleanProperty.create("south_west");
    public static final BooleanProperty SOUTH_EAST = BooleanProperty.create("south_east");

    public CrossConnectedBlock(Properties properties) {
        super(properties);

        registerDefaultState(stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(EAST, false)
                .setValue(UP, false)
                .setValue(DOWN, false)
                .setValue(NORTH_WEST, false)
                .setValue(NORTH_EAST, false)
                .setValue(SOUTH_WEST, false)
                .setValue(SOUTH_EAST, false)
        );
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos targetPos = context.getClickedPos();
        BlockState blockState = Objects.requireNonNull(super.getStateForPlacement(context));
        if (context.canPlace()) {
            for (Direction direction : Direction.values()) {
                blockState = updateWithAdjacentPair(blockState, targetPos, direction, level, true);
                if (direction != Direction.UP && direction != Direction.DOWN) {
                    blockState = updateWithDiagonalPair(blockState, targetPos, direction, level, true);
                }
            }
        }
        return blockState;
    }

    @Override
    public void destroy(@NotNull LevelAccessor level, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        for (Direction direction : Direction.values()) {
            blockState = updateWithAdjacentPair(blockState, blockPos, direction, level, false);
            if (direction != Direction.UP && direction != Direction.DOWN) {
                blockState = updateWithDiagonalPair(blockState, blockPos, direction, level, false);
            }
        }
    }

    private static BlockState updateWithAdjacentPair(BlockState blockState, BlockPos targetPos, Direction direction, LevelAccessor level, boolean value) {
        BlockPos adjacentPos = targetPos.relative(direction);
        BlockState adjacentBlockState = level.getBlockState(adjacentPos);
        if (adjacentBlockState.getBlock() instanceof CrossConnectedBlock) {
            BooleanProperty property = getPropertyFromDirection(direction);
            BooleanProperty otherProperty = getOppositeProperty(property);
            blockState = blockState.setValue(property, value);
            level.setBlock(adjacentPos, adjacentBlockState.setValue(otherProperty, value), 3);
        }
        return blockState;
    }

    private static BlockState updateWithDiagonalPair(BlockState blockState, BlockPos targetPos, Direction direction, LevelAccessor level, boolean value) {
        BlockPos adjacentPos = targetPos.relative(direction);
        Direction diagonalDirection = direction.getClockWise();
        BlockPos diagonalPos = adjacentPos.relative(diagonalDirection);
        BlockState diagonalBlockState = level.getBlockState(diagonalPos);
        if (diagonalBlockState.getBlock() instanceof CrossConnectedBlock) {
            BooleanProperty property = getAnglePropertyFromDirections(direction, diagonalDirection);
            BooleanProperty otherProperty = getOppositeProperty(property);
            blockState = blockState.setValue(property, value);
            level.setBlock(diagonalPos, diagonalBlockState.setValue(otherProperty, value), 3);
        }
        return blockState;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SOUTH, EAST, WEST, NORTH, UP, DOWN, NORTH_WEST, NORTH_EAST, SOUTH_EAST, SOUTH_WEST);
    }

    public static BooleanProperty getPropertyFromDirection(Direction direction) {
        return switch (direction) {
            case DOWN -> DOWN;
            case UP -> UP;
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            case EAST -> EAST;
        };
    }

    public static BooleanProperty getAnglePropertyFromDirections(Direction direction1, Direction direction2) {
        List<Direction> directions = Arrays.asList(direction1, direction2);
        if (directions.contains(Direction.NORTH) && directions.contains(Direction.EAST)) {
            return NORTH_EAST;
        } else if (directions.contains(Direction.NORTH) && directions.contains(Direction.WEST)) {
            return NORTH_WEST;
        } else if (directions.contains(Direction.SOUTH) && directions.contains(Direction.EAST)) {
            return SOUTH_EAST;
        } else if (directions.contains(Direction.SOUTH) && directions.contains(Direction.WEST)) {
            return SOUTH_WEST;
        }
        return null;
    }

    public static BooleanProperty getOppositeProperty(BooleanProperty property) {
        if (property == CrossConnectedBlock.DOWN) {
            return CrossConnectedBlock.UP;
        } else if (property == CrossConnectedBlock.UP) {
            return CrossConnectedBlock.DOWN;
        } else if (property == CrossConnectedBlock.EAST) {
            return CrossConnectedBlock.WEST;
        } else if (property == CrossConnectedBlock.WEST) {
            return CrossConnectedBlock.EAST;
        } else if (property == CrossConnectedBlock.SOUTH) {
            return CrossConnectedBlock.NORTH;
        } else if (property == CrossConnectedBlock.NORTH) {
            return CrossConnectedBlock.SOUTH;
        } else if (property == CrossConnectedBlock.NORTH_EAST) {
            return CrossConnectedBlock.SOUTH_WEST;
        } else if (property == CrossConnectedBlock.SOUTH_EAST) {
            return CrossConnectedBlock.NORTH_WEST;
        } else if (property == CrossConnectedBlock.NORTH_WEST) {
            return CrossConnectedBlock.SOUTH_EAST;
        } else if (property == CrossConnectedBlock.SOUTH_WEST) {
            return CrossConnectedBlock.NORTH_EAST;
        }
        return null;
    }
}
