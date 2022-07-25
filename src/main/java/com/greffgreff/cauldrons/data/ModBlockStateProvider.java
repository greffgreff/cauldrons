package com.greffgreff.cauldrons.data;

import com.greffgreff.cauldrons.Main;
import com.greffgreff.cauldrons.blocks.CrossConnectedBlock;
import com.greffgreff.cauldrons.registries.BlockRegistry;
import com.greffgreff.cauldrons.utils.Console;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(DataGenerator generator, ExistingFileHelper exFileHelper) {
        super(generator, Main.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        BlockModelBuilder sideModel = models().withExistingParent("cauldron_side", modLoc("block/cauldron_side"));
        BlockModelBuilder bottomModel = models().withExistingParent("cauldron_bottom", modLoc("block/cauldron_bottom"));
        MultiPartBlockStateBuilder builder = getMultipartBuilder(BlockRegistry.CAULDRON.get());

        Console.debug("generator hit");

        builder
                .part().modelFile(bottomModel).addModel().condition(CrossConnectedBlock.DOWN_CONNECTED, false).end()
                .part().modelFile(sideModel).addModel().condition(CrossConnectedBlock.NORTH_CONNECTED, false).end()
                .part().modelFile(sideModel).rotationY(90).addModel().condition(CrossConnectedBlock.EAST_CONNECTED, false).end()
                .part().modelFile(sideModel).rotationY(180).addModel().condition(CrossConnectedBlock.SOUTH_CONNECTED, false).end()
                .part().modelFile(sideModel).rotationY(270).addModel().condition(CrossConnectedBlock.WEST_CONNECTED, false).end();
    }
}
