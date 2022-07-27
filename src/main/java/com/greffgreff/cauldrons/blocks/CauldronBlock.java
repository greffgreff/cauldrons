package com.greffgreff.cauldrons.blocks;

import com.greffgreff.cauldrons.utils.DirectionalUtil;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashMap;
import java.util.Map;

import static com.greffgreff.cauldrons.utils.DirectionalUtil.getAdjacentSides;
import static com.greffgreff.cauldrons.utils.DirectionalUtil.getRelativeRotation;

@SuppressWarnings({"NullableProblems", "ConstantConditions"})
public class CauldronBlock extends CrossConnectedBlock {
    public static final IntegerProperty FILL_LEVEL = IntegerProperty.create("fill_level", 0, 10);
    private static final VoxelShape NORTH_ANGLE = Block.box(0, 3, 0, 2, 16, 2);
    private static final VoxelShape EAST_ANGLE = Block.box(14, 3, 0, 16, 16, 2);
    private static final VoxelShape SOUTH_ANGLE = Block.box(14, 3, 14, 16, 16, 16);
    private static final VoxelShape WEST_ANGLE = Block.box(0, 3, 14, 2, 16, 16);
    private static final VoxelShape NORTH_SIDE = Block.box(2, 3, 0, 14, 16, 2);
    private static final VoxelShape EAST_SIDE = Block.box(14, 3, 2, 16, 16, 14);
    private static final VoxelShape SOUTH_SIDE = Block.box(2, 3, 14, 14, 16, 16);
    private static final VoxelShape WEST_SIDE = Block.box(0, 3, 2, 2, 16, 14);
    private static final VoxelShape BOTTOM = Shapes.join(Block.box(0, 2, 0, 16, 3, 16), Shapes.join(Shapes.join(Block.box(11, 0, 15, 15, 2, 16), Block.box(15, 0, 11, 16, 2, 16), BooleanOp.OR), Shapes.join(Shapes.join(Block.box(15, 0, 1, 16, 2, 5), Block.box(11, 0, 0, 16, 2, 1), BooleanOp.OR), Shapes.join(Shapes.join(Block.box(1, 0, 0, 5, 2, 1), Block.box(0, 0, 0, 1, 2, 5), BooleanOp.OR), Shapes.join(Block.box(0, 0, 11, 1, 2, 15), Block.box(0, 0, 15, 5, 2, 16), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR), BooleanOp.OR);
    private static final Map<BlockState, VoxelShape> SHAPES_BY_BLOCKSTATE = new HashMap<>();

    public CauldronBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE));

        this.stateDefinition.getPossibleStates().forEach(state -> {
            VoxelShape shape = Shapes.empty();
            if (!state.getValue(DOWN)) {
                shape = Shapes.join(shape, BOTTOM, BooleanOp.OR);
            }
            for (Direction direction : DirectionalUtil.getHorizontalDirections()) {
                if (!state.getValue(getProperty(direction))) {
                    shape = Shapes.join(shape, getSideByDirection(direction), BooleanOp.OR);
                    // FIXME - fix angle hit box
                    if (!state.getValue(getAngleProperty(direction, direction.getClockWise()))) {
                        shape = Shapes.join(shape, getAngleByDirection(direction), BooleanOp.OR);
                    }
                    if (!state.getValue(getAngleProperty(direction, direction.getClockWise()))) {
                        shape = Shapes.join(shape, getAngleByDirection(direction.getClockWise()), BooleanOp.OR);
                    }
                }
            }
            SHAPES_BY_BLOCKSTATE.put(state, shape);
        });
    }

    @Override
    protected boolean canConnect(BlockState blockState) {
        return blockState.getBlock() instanceof CauldronBlock;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter getter, BlockPos blockPos, CollisionContext context) {
        return SHAPES_BY_BLOCKSTATE.get(blockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SOUTH, EAST, WEST, NORTH, UP, DOWN, NORTH_WEST, NORTH_EAST, SOUTH_EAST, SOUTH_WEST, FILL_LEVEL);
    }

    private VoxelShape getSideByDirection(Direction direction) {
        return switch (direction) {
            case SOUTH -> SOUTH_SIDE;
            case NORTH -> NORTH_SIDE;
            case WEST -> WEST_SIDE;
            case EAST -> EAST_SIDE;
            case UP, DOWN -> null;
        };
    }

    private VoxelShape getAngleByDirection(Direction direction) {
        return switch (direction) {
            case SOUTH -> SOUTH_ANGLE;
            case NORTH -> NORTH_ANGLE;
            case WEST -> WEST_ANGLE;
            case EAST -> EAST_ANGLE;
            case UP, DOWN -> null;
        };
    }
}
