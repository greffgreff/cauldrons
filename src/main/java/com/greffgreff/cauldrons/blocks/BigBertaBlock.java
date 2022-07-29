package com.greffgreff.cauldrons.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("NullableProblems")
public class BigBertaBlock extends Block {
    private static final VoxelShape SHAPE = Shapes.box(0, 0, 0, 10, 10, 10);

    public BigBertaBlock() {
        super(Properties.of(Material.STONE));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter getter, BlockPos blockPos, CollisionContext context) {
        return SHAPE;
    }
}
