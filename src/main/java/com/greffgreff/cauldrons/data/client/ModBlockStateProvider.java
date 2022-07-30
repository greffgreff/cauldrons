package com.greffgreff.cauldrons.data.client;

import com.greffgreff.cauldrons.Main;
import com.greffgreff.cauldrons.blocks.CrossConnectedBlock;
import com.greffgreff.cauldrons.registries.BlockRegistry;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.greffgreff.cauldrons.blocks.CrossConnectedBlock.*;

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
        BlockModelBuilder angleModel = getModel("cauldron_bottom_angle", "block/cauldron_bottom_angle");
        BlockModelBuilder bottomModel = getModel("cauldron_bottom", "block/cauldron_bottom");
        MultiPartBlockStateBuilder builder = getMultipartBuilder(BlockRegistry.CAULDRON.get());

        builder
                .part()
                .   modelFile(bottomModel).addModel()
                .   condition(CrossConnectedBlock.DOWN, false)
                .end();

        for (Direction direction: Direction.values()) {
            if (direction == Direction.DOWN || direction == Direction.UP) {
                continue;
            }

            Direction right = direction.getClockWise();
            Direction left = direction.getCounterClockWise();

            builder
                    .part()
                    .   modelFile(sideModel).rotationY((int) direction.getOpposite().toYRot()).addModel()
                    .   condition(getProperty(direction), false)
                    .end().part()
                    .   modelFile(angleModel).rotationY((int) right.getOpposite().toYRot()).addModel()
                    .   condition(getAngleProperty(right, direction), false)
                    .   condition(getProperty(direction), true)
                    .   condition(getProperty(right), true)
                    .end().part()
                    .   modelFile(angleModel).rotationY((int) left.getOpposite().toYRot() + 90).addModel()
                    .   condition(getAngleProperty(left, direction), false)
                    .   condition(getProperty(direction), true)
                    .   condition(getProperty(left), true)
                    .end().part()
                    .   modelFile(angleModel).rotationY((int) right.getOpposite().toYRot()).addModel()
                    .   condition(getProperty(direction), false)
                    .end().part()
                    .   modelFile(angleModel).rotationY((int) left.getOpposite().toYRot() + 90).addModel()
                    .   condition(getProperty(direction), false)
                    .end();
        }
    }

    private BlockModelBuilder getModel(String name, String texture) {
        return models().withExistingParent(name, modLoc(texture));
    }
}
