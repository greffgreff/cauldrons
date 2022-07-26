package com.greffgreff.cauldrons.registries;

import com.greffgreff.cauldrons.Main;
import com.greffgreff.cauldrons.blockEntities.CauldronBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("ConstantConditions")
public final class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Main.MOD_ID);

    public static final RegistryObject<BlockEntityType<CauldronBlockEntity>> CAULDRON_BLOCK_ENTITY = BLOCK_ENTITIES.register("cauldron_block_entity", () -> BlockEntityType.Builder.of(CauldronBlockEntity::new, Blocks.ACACIA_LEAVES).build(null));

    public static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> blockEntity, Block ...blocks) {
        return BLOCK_ENTITIES.register(name, () -> BlockEntityType.Builder.of(blockEntity, blocks).build(null));
    }
}