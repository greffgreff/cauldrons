package com.greffgreff.cauldrons.blocks;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.Pair;
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
    public static final BooleanProperty NORTHWARDS_CONNECTED = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTHWARDS_CONNECTED = BlockStateProperties.SOUTH;
    public static final BooleanProperty WESTWARDS_CONNECTED = BlockStateProperties.WEST;
    public static final BooleanProperty EASTWARDS_CONNECTED = BlockStateProperties.EAST;
    public static final BooleanProperty UPWARDS_CONNECTED = BlockStateProperties.UP;
    public static final BooleanProperty DOWNWARDS_CONNECTED = BlockStateProperties.DOWN;
    public static final BooleanProperty VERTICALLY_SURROUNDED = BooleanProperty.create("vertically_surrounded");
    public static final BooleanProperty HORIZONTALLY_SURROUNDED = BooleanProperty.create("horizontally_surrounded");
    public static final BooleanProperty FULLY_SURROUNDED = BooleanProperty.create("fully_surrounded");
    public static final ImmutableMap<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = new ImmutableMap.Builder<Direction, BooleanProperty>()
            .put(Direction.NORTH, NORTHWARDS_CONNECTED)
            .put(Direction.EAST, EASTWARDS_CONNECTED)
            .put(Direction.SOUTH, SOUTHWARDS_CONNECTED)
            .put(Direction.WEST, WESTWARDS_CONNECTED)
            .put(Direction.UP, UPWARDS_CONNECTED)
            .put(Direction.DOWN, DOWNWARDS_CONNECTED)
            .build();

    public CrossConnectedBlock(Properties properties) {
        super(properties);

        registerDefaultState(stateDefinition.any()
                .setValue(SOUTHWARDS_CONNECTED, false)
                .setValue(EASTWARDS_CONNECTED, false)
                .setValue(WESTWARDS_CONNECTED, false)
                .setValue(NORTHWARDS_CONNECTED, false)
                .setValue(VERTICALLY_SURROUNDED, false)
                .setValue(HORIZONTALLY_SURROUNDED, false)
                .setValue(FULLY_SURROUNDED, false)
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
                BooleanProperty property = getPropertyFromDirection(direction.getOpposite());
                level.setBlock(adjacentPos, adjacentBlockState.setValue(property, true), 3);
            }
        }

        return Objects.requireNonNull(super.getStateForPlacement(context))
                .setValue(SOUTHWARDS_CONNECTED, joints.contains(Direction.SOUTH))
                .setValue(EASTWARDS_CONNECTED, joints.contains(Direction.EAST))
                .setValue(WESTWARDS_CONNECTED, joints.contains(Direction.WEST))
                .setValue(NORTHWARDS_CONNECTED, joints.contains(Direction.NORTH))
                .setValue(UPWARDS_CONNECTED, joints.contains(Direction.UP))
                .setValue(DOWNWARDS_CONNECTED, joints.contains(Direction.DOWN))
                .setValue(VERTICALLY_SURROUNDED, joints.containsAll(Arrays.asList(Direction.DOWN, Direction.UP)))
                .setValue(HORIZONTALLY_SURROUNDED, joints.containsAll(Arrays.asList(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST)))
                .setValue(FULLY_SURROUNDED, joints.containsAll(Arrays.asList(Direction.values())));
    }

    @Override
    public void destroy(@NotNull LevelAccessor level, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        for (Direction direction : Direction.values()) {
            BlockPos adjacentPos = blockPos.relative(direction);
            BlockState adjacentBlockState = level.getBlockState(adjacentPos);

            if (adjacentBlockState.getBlock() instanceof CrossConnectedBlock) {
                BooleanProperty property = getPropertyFromDirection(direction.getOpposite());
                level.setBlock(adjacentPos, adjacentBlockState.setValue(property, false), 3);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(
                SOUTHWARDS_CONNECTED,
                EASTWARDS_CONNECTED,
                WESTWARDS_CONNECTED,
                NORTHWARDS_CONNECTED,
                UPWARDS_CONNECTED,
                DOWNWARDS_CONNECTED,
                VERTICALLY_SURROUNDED,
                HORIZONTALLY_SURROUNDED,
                FULLY_SURROUNDED
        );
    }

    public static BooleanProperty getPropertyFromDirection(Direction direction) {
        return switch (direction) {
            case DOWN -> DOWNWARDS_CONNECTED;
            case UP -> UPWARDS_CONNECTED;
            case NORTH -> NORTHWARDS_CONNECTED;
            case SOUTH -> SOUTHWARDS_CONNECTED;
            case WEST -> WESTWARDS_CONNECTED;
            case EAST -> EASTWARDS_CONNECTED;
        };
    }

    public static Direction[] getHorizontalsProperties() {
        return Arrays.stream(Direction.values()).filter(d -> d != Direction.DOWN && d != Direction.UP).toArray(Direction[]::new);
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
}
