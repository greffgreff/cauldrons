package com.greffgreff.cauldrons.blocks;

import com.greffgreff.cauldrons.utils.DirectionalProperty;
import com.greffgreff.cauldrons.utils.EnumBooleanProperty;
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

import java.util.*;

public abstract class CrossConnectedBlock extends Block {
    public static final DirectionalProperty NORTHWARDS_CONNECTED = DirectionalProperty.NORTH;
    public static final DirectionalProperty SOUTHWARDS_CONNECTED = DirectionalProperty.SOUTH;
    public static final DirectionalProperty WESTWARDS_CONNECTED = DirectionalProperty.WEST;
    public static final DirectionalProperty EASTWARDS_CONNECTED = DirectionalProperty.EAST;
    public static final DirectionalProperty UPWARDS_CONNECTED = DirectionalProperty.UP;
    public static final DirectionalProperty DOWNWARDS_CONNECTED = DirectionalProperty.DOWN;
    public static final BooleanProperty VERTICALLY_SURROUNDED = BooleanProperty.create("vertically_surrounded");
    public static final BooleanProperty HORIZONTALLY_SURROUNDED = BooleanProperty.create("horizontally_surrounded");
    public static final BooleanProperty FULLY_SURROUNDED = EnumBooleanProperty.create("fully_surrounded");

    public CrossConnectedBlock(Properties properties) {
        super(properties);

        registerDefaultState(stateDefinition.any()
                .setValue(SOUTHWARDS_CONNECTED.get(), false)
                .setValue(EASTWARDS_CONNECTED.get(), false)
                .setValue(WESTWARDS_CONNECTED.get(), false)
                .setValue(NORTHWARDS_CONNECTED.get(), false)
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
                BooleanProperty property = DirectionalProperty.getFromDirection(direction.getOpposite()).get();
                level.setBlock(adjacentPos, adjacentBlockState.setValue(property, true), 3);
            }
        }

        return Objects.requireNonNull(super.getStateForPlacement(context))
                .setValue(SOUTHWARDS_CONNECTED.get(), joints.contains(Direction.SOUTH))
                .setValue(EASTWARDS_CONNECTED.get(), joints.contains(Direction.EAST))
                .setValue(WESTWARDS_CONNECTED.get(), joints.contains(Direction.WEST))
                .setValue(NORTHWARDS_CONNECTED.get(), joints.contains(Direction.NORTH))
                .setValue(UPWARDS_CONNECTED.get(), joints.contains(Direction.UP))
                .setValue(DOWNWARDS_CONNECTED.get(), joints.contains(Direction.DOWN))
                .setValue(VERTICALLY_SURROUNDED, joints.containsAll(Arrays.asList(Direction.DOWN, Direction.UP)))
                .setValue(VERTICALLY_SURROUNDED, joints.containsAll(Arrays.asList(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST)))
                .setValue(FULLY_SURROUNDED, joints.containsAll(Arrays.asList(Direction.values())));
    }

    @Override
    public void destroy(@NotNull LevelAccessor level, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        for (Direction direction: Direction.values()) {
            BlockPos adjacentPos = blockPos.relative(direction);
            BlockState adjacentBlockState = level.getBlockState(adjacentPos);

            if (adjacentBlockState.getBlock() instanceof CrossConnectedBlock) {
                BooleanProperty property = DirectionalProperty.getFromDirection(direction.getOpposite()).get();
                level.setBlock(adjacentPos, adjacentBlockState.setValue(property, false), 3);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(
                SOUTHWARDS_CONNECTED.get(),
                EASTWARDS_CONNECTED.get(),
                WESTWARDS_CONNECTED.get(),
                NORTHWARDS_CONNECTED.get(),
                UPWARDS_CONNECTED.get(),
                DOWNWARDS_CONNECTED.get(),
                VERTICALLY_SURROUNDED,
                HORIZONTALLY_SURROUNDED,
                FULLY_SURROUNDED
        );
    }

//    public abstract <T extends CrossConnectedBlock> T getBlockToCheck();
}
