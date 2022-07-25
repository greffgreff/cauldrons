package com.greffgreff.cauldrons.data;

import com.greffgreff.cauldrons.Main;
import com.greffgreff.cauldrons.blocks.CrossConnectedBlock;
import com.greffgreff.cauldrons.registries.BlockRegistry;
import com.greffgreff.cauldrons.utils.Console;
import com.greffgreff.cauldrons.utils.DirectionalProperty;
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
                .end();

        for (DirectionalProperty property: DirectionalProperty.values()) {
            Console.debug(property);
            Console.debug(property.get());
            Console.debug(property.getAdjacentSides());

            builder
                    .part()
                    .   modelFile(sideModel).rotationY(property.getRelativeYRotation()).addModel()
                    .   condition(property.get(), false)
                    .end().part()
                    .   modelFile(angleModel).rotationY(property.getRelativeYRotation()+90).addModel()
                    .   condition(property.get(), true)
                    .   condition(property.getAdjacentSides().right().get(), false)
                    .end().part()
                    .   modelFile(angleModel).rotationY(property.getRelativeYRotation()).addModel()
                    .   condition(property.get(), true)
                    .   condition(property.getAdjacentSides().left().get(), false)
                    .end();
        }
    }

    private BlockModelBuilder getModel(String name, String texture) {
        return models().withExistingParent(name, modLoc(texture));
    }
}
