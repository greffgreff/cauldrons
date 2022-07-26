package com.greffgreff.cauldrons.data.client;

import com.greffgreff.cauldrons.Main;
import com.greffgreff.cauldrons.blocks.CrossConnectedBlock;
import com.greffgreff.cauldrons.registries.BlockRegistry;
import com.greffgreff.cauldrons.utils.DirectionalUtil;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
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
                .   condition(CrossConnectedBlock.DOWNWARDS_CONNECTED, false)
                .end();

        for (Direction direction: DirectionalUtil.getHorizontalDirections()) {
            Pair<Direction, Direction> adjacentSides = DirectionalUtil.getAdjacentSidesByDirection(direction);
            BooleanProperty side = CrossConnectedBlock.getPropertyFromDirection(direction);
            BooleanProperty leftSide = CrossConnectedBlock.getPropertyFromDirection(adjacentSides.left());
            BooleanProperty rightSide = CrossConnectedBlock.getPropertyFromDirection(adjacentSides.right());
            int sideRot = DirectionalUtil.getRelativeRotation(direction);
            int leftSideRot = DirectionalUtil.getRelativeRotation(adjacentSides.left());
            int rightSideRot = DirectionalUtil.getRelativeRotation(adjacentSides.right());

            builder
                    .part() // apply side if not connected
                    .   modelFile(sideModel).rotationY(sideRot).addModel()
                    .   condition(side, false)
                    .end().part() // apply angle if adjacent left side is not connected
                    .   modelFile(angleModel).rotationY(rightSideRot).addModel()
                    .   condition(rightSide, true)
                    .end().part() // apply angle if adjacent right side is not connected
                    .   modelFile(angleModel).rotationY(leftSideRot).addModel()
                    .   condition(leftSide, false)
                    .end();
        }
    }

    private BlockModelBuilder getModel(String name, String texture) {
        return models().withExistingParent(name, modLoc(texture));
    }
}
