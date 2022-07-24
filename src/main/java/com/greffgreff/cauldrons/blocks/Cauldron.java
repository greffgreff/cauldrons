package com.greffgreff.cauldrons.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class Cauldron extends Block {

    public Cauldron() {
        super(BlockBehaviour.Properties.of(Material.STONE));
    }
}
