package com.greffgreff.cauldrons.data;

import com.greffgreff.cauldrons.Main;
import com.greffgreff.cauldrons.blocks.CrossConnectedBlock;
import com.greffgreff.cauldrons.registries.BlockRegistry;
import net.minecraft.data.DataGenerator;
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
        BlockModelBuilder sideModel = getModel("cauldron_bottom_side", "block/cauldron_bottom_side");
        BlockModelBuilder angleModel = getModel("cauldron_bottom_column", "block/cauldron_bottom_column");
        BlockModelBuilder bottomModel = getModel("cauldron_bottom", "block/cauldron_bottom");
        MultiPartBlockStateBuilder builder = getMultipartBuilder(BlockRegistry.CAULDRON.get());

        builder
                .part() // down connected, add bottom
                .   modelFile(bottomModel).addModel()
                .   condition(CrossConnectedBlock.DOWNWARDS_CONNECTED.get(), false)
                .end().part() // north none connected, add side
                .   modelFile(sideModel).addModel()
                .   condition(CrossConnectedBlock.NORTHWARDS_CONNECTED.get(), false)
                .end().part() // north connected, add angle left
                .   modelFile(angleModel).rotationY(90).addModel()
                .   condition(CrossConnectedBlock.NORTHWARDS_CONNECTED.get(), true)
                .   condition(CrossConnectedBlock.EASTWARDS_CONNECTED.get(), false)
                .end().part() // north connected, add angle right
                .   modelFile(angleModel).addModel()
                .   condition(CrossConnectedBlock.NORTHWARDS_CONNECTED.get(), true)
                .   condition(CrossConnectedBlock.WESTWARDS_CONNECTED.get(), false)
                .end().part() // south none connected, add side
                . modelFile(sideModel).rotationY(180).addModel()
                .   condition(CrossConnectedBlock.SOUTHWARDS_CONNECTED.get(), false)
//                .end().part() // south connected, add angle left
//                .   modelFile(angleModel).rotationY(180+90).addModel()
//                .   condition(CrossConnectedBlock.SOUTH_CONNECTED, true)
//                .end().part() // south connected, add angle right
//                .   modelFile(angleModel).rotationY(180).addModel()
//                .   condition(CrossConnectedBlock.SOUTH_CONNECTED, true)
                .end().part() // east none connected, add side
                .   modelFile(sideModel).rotationY(90).addModel()
                .   condition(CrossConnectedBlock.EASTWARDS_CONNECTED.get(), false)
//                .end().part() // east connected, add angle left
//                .   modelFile(angleModel).rotationY(90+90).addModel()
//                .   condition(CrossConnectedBlock.EAST_CONNECTED, true)
//                .end().part() // east connected, add angle right
//                .   modelFile(angleModel).rotationY(90).addModel()
//                .   condition(CrossConnectedBlock.EAST_CONNECTED, true)
                .end().part() // west none connected, add side
                .   modelFile(sideModel).rotationY(270).addModel()
                .   condition(CrossConnectedBlock.WESTWARDS_CONNECTED.get(), false)
//                .end().part() // west connected, add angle left
//                .   modelFile(angleModel).rotationY(270+90).addModel()
//                .   condition(CrossConnectedBlock.WEST_CONNECTED, true)
//                .end().part() // west connected, add angle right
//                .   modelFile(angleModel).rotationY(270).addModel()
//                .   condition(CrossConnectedBlock.WEST_CONNECTED, true)
                .end();
    }

    private BlockModelBuilder getModel(String name, String texture) {
        return models().withExistingParent(name, modLoc(texture));
    }
}
