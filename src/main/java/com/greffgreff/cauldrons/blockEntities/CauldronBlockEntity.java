package com.greffgreff.cauldrons.blockEntities;

import com.greffgreff.cauldrons.registries.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

public class CauldronBlockEntity extends BlockEntity {

    public CauldronBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.CAULDRON_BLOCK_ENTITY.get(), blockPos, blockState);
    }
}
