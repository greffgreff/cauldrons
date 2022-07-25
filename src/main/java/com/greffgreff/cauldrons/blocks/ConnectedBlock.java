package com.greffgreff.cauldrons.blocks;

import com.greffgreff.cauldrons.utils.Console;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ConnectedBlock extends Block {
    public static final BooleanProperty SOUTH_CONNECTED = BooleanProperty.create("south_connected");
    public static final BooleanProperty EAST_CONNECTED = BooleanProperty.create("east_connected");
    public static final BooleanProperty WEST_CONNECTED = BooleanProperty.create("west_connected");
    public static final BooleanProperty NORTH_CONNECTED = BooleanProperty.create("north_connected");

    public ConnectedBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE));

        registerDefaultState(stateDefinition.any()
                .setValue(SOUTH_CONNECTED, false)
                .setValue(EAST_CONNECTED, false)
                .setValue(WEST_CONNECTED, false)
                .setValue(NORTH_CONNECTED, false)
        );
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos targetPos = context.getClickedPos();

        List<Direction> joints = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            BlockPos adjacentPos = targetPos.relative(direction);
            BlockState adjacentBlockState = level.getBlockState(adjacentPos);

            if (adjacentBlockState.getBlock() instanceof ConnectedBlock) {
                joints.add(direction);

                switch (direction) { // TODO - streamline this
                    case SOUTH -> level.setBlock(adjacentPos, adjacentBlockState.setValue(ConnectedBlock.SOUTH_CONNECTED, true), 3);
                    case EAST -> level.setBlock(adjacentPos, adjacentBlockState.setValue(ConnectedBlock.EAST_CONNECTED, true), 3);
                    case WEST -> level.setBlock(adjacentPos, adjacentBlockState.setValue(ConnectedBlock.WEST_CONNECTED, true), 3);
                    case NORTH -> level.setBlock(adjacentPos, adjacentBlockState.setValue(ConnectedBlock.NORTH_CONNECTED, true), 3);
                }
            }
        }

        return super.getStateForPlacement(context)
                .setValue(SOUTH_CONNECTED, joints.contains(Direction.SOUTH))
                .setValue(EAST_CONNECTED, joints.contains(Direction.EAST))
                .setValue(WEST_CONNECTED, joints.contains(Direction.WEST))
                .setValue(NORTH_CONNECTED, joints.contains(Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SOUTH_CONNECTED, EAST_CONNECTED, WEST_CONNECTED, NORTH_CONNECTED);
    }
}
