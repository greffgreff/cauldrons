package com.greffgreff.cauldrons.data.client;

import com.greffgreff.cauldrons.Main;
import com.greffgreff.cauldrons.blocks.CrossConnectedBlock;
import com.greffgreff.cauldrons.registries.BlockRegistry;
import com.greffgreff.cauldrons.utils.DirectionalUtil;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.greffgreff.cauldrons.blocks.CrossConnectedBlock.*;
import static com.greffgreff.cauldrons.utils.DirectionalUtil.getAdjacentSides;
import static com.greffgreff.cauldrons.utils.DirectionalUtil.getRelativeRotation;

@SuppressWarnings("ConstantConditions")
public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(DataGenerator generator, ExistingFileHelper exFileHelper) {
        super(generator, Main.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        handleCauldronBlockStates();
    }

    private void handleCauldronBlockStates() {
        BlockModelBuilder sideModel = getModel("cauldron_bottom_side", "block/cauldron_bottom_side");
        BlockModelBuilder angleModel = getModel("cauldron_bottom_column", "block/cauldron_bottom_column");
        BlockModelBuilder bottomModel = getModel("cauldron_bottom", "block/cauldron_bottom");
        MultiPartBlockStateBuilder builder = getMultipartBuilder(BlockRegistry.CAULDRON.get());

        builder
                .part() // down connected, add bottom
                .   modelFile(bottomModel).addModel()
                .   condition(CrossConnectedBlock.DOWN, false)
                .end();

        for (Direction direction: DirectionalUtil.getHorizontalDirections()) {
            Pair<Direction, Direction> adjacentSides = getAdjacentSides(direction);

            builder
                    .part()
                    .   modelFile(sideModel).rotationY(getRelativeRotation(direction)).addModel()
                    .   condition(getProperty(direction), false)
                    .end().part()
                    .   modelFile(angleModel).rotationY(getRelativeRotation(adjacentSides.right())).addModel()
                    .   condition(getAngleProperty(adjacentSides.right(), direction), false)
                    .   condition(getProperty(direction), true)
                    .   condition(getProperty(adjacentSides.right()), true)
                    .end().part()
                    .   modelFile(angleModel).rotationY(getRelativeRotation(adjacentSides.left()) + 90).addModel()
                    .   condition(getAngleProperty(adjacentSides.left(), direction), false)
                    .   condition(getProperty(direction), true)
                    .   condition(getProperty(adjacentSides.left()), true)
                    .end().part()
                    .   modelFile(angleModel).rotationY(getRelativeRotation(adjacentSides.right())).addModel()
                    .   condition(getProperty(direction), false)
                    .end().part()
                    .   modelFile(angleModel).rotationY(getRelativeRotation(adjacentSides.left()) + 90).addModel()
                    .   condition(getProperty(direction), false)
                    .end();
        }
    }

    private BlockModelBuilder getModel(String name, String texture) {
        return models().withExistingParent(name, modLoc(texture));
    }
}
