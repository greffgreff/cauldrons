package com.greffgreff.cauldrons.blocks;

import com.greffgreff.cauldrons.utils.Console;
import com.greffgreff.cauldrons.utils.DirectionalUtil;
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

        for (Direction direction : Direction.values()) {
            BlockPos adjacentPos = targetPos.relative(direction);
            BlockState adjacentBlockState = level.getBlockState(adjacentPos);

            // handling adjacent block
            if (adjacentBlockState.getBlock() instanceof CrossConnectedBlock) {
                blockState = blockState.setValue(getPropertyFromDirection(direction), true);
                BooleanProperty property = getPropertyFromDirection(direction.getOpposite());
                level.setBlock(adjacentPos, adjacentBlockState.setValue(property, true), 3);
            }

            // handling diagonal block
            if (direction != Direction.UP && direction != Direction.DOWN) {
                BlockPos diagonalPos = adjacentPos.relative(direction.getClockWise());
                BlockState diagonalBlockState = level.getBlockState(diagonalPos);

                if (diagonalBlockState.getBlock() instanceof CrossConnectedBlock) {
                    blockState = blockState.setValue(getAnglePropertyFromDirections(direction, direction.getClockWise()), true);
                    BooleanProperty property = getAnglePropertyFromDirections(direction.getOpposite(), direction.getOpposite().getClockWise());
                    level.setBlock(diagonalPos, diagonalBlockState.setValue(property, true), 3);
                }
            }
        }

        return blockState;
    }

    @Override
    public void destroy(@NotNull LevelAccessor level, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {
//        for (Direction direction : Direction.values()) {
//            BlockPos adjacentPos = blockPos.relative(direction);
//            BlockState adjacentBlockState = level.getBlockState(adjacentPos);
//            if (!(adjacentBlockState.getBlock() instanceof CrossConnectedBlock)) continue;
//
//            BooleanProperty property = getPropertyFromDirection(direction.getOpposite());
//            level.setBlock(adjacentPos, adjacentBlockState.setValue(property, false), 3);
//        }
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
        if ((direction1 == Direction.NORTH && direction2 == Direction.EAST) || (direction1 == Direction.EAST && direction2 == Direction.NORTH)) {
            return NORTH_EAST;
        } else if ((direction1 == Direction.NORTH && direction2 == Direction.WEST) || (direction1 == Direction.WEST && direction2 == Direction.EAST)) {
            return NORTH_WEST;
        } else if ((direction1 == Direction.SOUTH && direction2 == Direction.EAST) || (direction1 == Direction.NORTH && direction2 == Direction.SOUTH)) {
            return SOUTH_EAST;
        } else if ((direction1 == Direction.SOUTH && direction2 == Direction.WEST) || (direction1 == Direction.WEST && direction2 == Direction.SOUTH)) {
            return SOUTH_WEST;
        }
        return null;
    }
}
