package com.greffgreff.cauldrons.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class CrossConnectedBlock extends Block {
    public static final BooleanProperty NORTH_CONNECTED = BooleanProperty.create("north_connected");
    public static final BooleanProperty SOUTH_CONNECTED = BooleanProperty.create("south_connected");
    public static final BooleanProperty WEST_CONNECTED = BooleanProperty.create("west_connected");
    public static final BooleanProperty EAST_CONNECTED = BooleanProperty.create("east_connected");
    public static final BooleanProperty UP_CONNECTED = BooleanProperty.create("up_connected");
    public static final BooleanProperty DOWN_CONNECTED = BooleanProperty.create("down_connected");
    public static final BooleanProperty VERTICALLY_FULLY_CONNECTED = BooleanProperty.create("vertically_fully_connected");
    public static final BooleanProperty HORIZONTALLY_FULLY_CONNECTED = BooleanProperty.create("horizontally_fully_connected");

    public CrossConnectedBlock(Properties properties) {
        super(properties);

        registerDefaultState(stateDefinition.any()
                .setValue(SOUTH_CONNECTED, false)
                .setValue(EAST_CONNECTED, false)
                .setValue(WEST_CONNECTED, false)
                .setValue(NORTH_CONNECTED, false)
                .setValue(VERTICALLY_FULLY_CONNECTED, false)
                .setValue(HORIZONTALLY_FULLY_CONNECTED, false)
        );
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos targetPos = context.getClickedPos();

        List<Direction> joints = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            BlockPos adjacentPos = targetPos.relative(direction);
            BlockState adjacentBlockState = level.getBlockState(adjacentPos);

            if (adjacentBlockState.getBlock() instanceof CrossConnectedBlock) {
                joints.add(direction);
                level.setBlock(adjacentPos, adjacentBlockState.setValue(getPropertyFromDirection(direction.getOpposite()), true), 3);
            }
        }

        return Objects.requireNonNull(super.getStateForPlacement(context))
                .setValue(SOUTH_CONNECTED, joints.contains(Direction.SOUTH))
                .setValue(EAST_CONNECTED, joints.contains(Direction.EAST))
                .setValue(WEST_CONNECTED, joints.contains(Direction.WEST))
                .setValue(NORTH_CONNECTED, joints.contains(Direction.NORTH))
                .setValue(UP_CONNECTED, joints.contains(Direction.UP))
                .setValue(DOWN_CONNECTED, joints.contains(Direction.DOWN))
                .setValue(VERTICALLY_FULLY_CONNECTED, joints.containsAll(Arrays.asList(Direction.DOWN, Direction.UP)))
                .setValue(VERTICALLY_FULLY_CONNECTED, joints.containsAll(Arrays.asList(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST)));
    }

    @Override
    public void destroy(@NotNull LevelAccessor level, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        for (Direction direction: Direction.values()) {
            BlockPos adjacentPos = blockPos.relative(direction);
            BlockState adjacentBlockState = level.getBlockState(adjacentPos);

            if (adjacentBlockState.getBlock() instanceof CrossConnectedBlock) {
                level.setBlock(adjacentPos, adjacentBlockState.setValue(getPropertyFromDirection(direction.getOpposite()), false), 3);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SOUTH_CONNECTED, EAST_CONNECTED, WEST_CONNECTED, NORTH_CONNECTED, UP_CONNECTED, DOWN_CONNECTED, VERTICALLY_FULLY_CONNECTED, HORIZONTALLY_FULLY_CONNECTED);
    }

//    public abstract <T extends CrossConnectedBlock> T getBlockToCheck();

    public static BooleanProperty getPropertyFromDirection(Direction direction) {
        return switch (direction) {
            case DOWN -> CrossConnectedBlock.DOWN_CONNECTED;
            case UP -> CrossConnectedBlock.UP_CONNECTED;
            case NORTH -> CrossConnectedBlock.NORTH_CONNECTED;
            case SOUTH -> CrossConnectedBlock.SOUTH_CONNECTED;
            case WEST -> CrossConnectedBlock.WEST_CONNECTED;
            case EAST -> CrossConnectedBlock.EAST_CONNECTED;
        };
    }
}
