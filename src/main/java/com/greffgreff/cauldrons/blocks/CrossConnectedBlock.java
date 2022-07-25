package com.greffgreff.cauldrons.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CrossConnectedBlock extends Block {
    public static final BooleanProperty NORTH_CONNECTED = BooleanProperty.create("north_connected");
    public static final BooleanProperty SOUTH_CONNECTED = BooleanProperty.create("south_connected");
    public static final BooleanProperty WEST_CONNECTED = BooleanProperty.create("west_connected");
    public static final BooleanProperty EAST_CONNECTED = BooleanProperty.create("east_connected");
    public static final BooleanProperty UP_CONNECTED = BooleanProperty.create("up_connected");
    public static final BooleanProperty DOWN_CONNECTED = BooleanProperty.create("down_connected");

    public CrossConnectedBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE));

        registerDefaultState(stateDefinition.any()
                .setValue(SOUTH_CONNECTED, false)
                .setValue(EAST_CONNECTED, false)
                .setValue(WEST_CONNECTED, false)
                .setValue(NORTH_CONNECTED, false)
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
                .setValue(DOWN_CONNECTED, joints.contains(Direction.DOWN));
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
        builder.add(SOUTH_CONNECTED, EAST_CONNECTED, WEST_CONNECTED, NORTH_CONNECTED, UP_CONNECTED, DOWN_CONNECTED);
    }

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
