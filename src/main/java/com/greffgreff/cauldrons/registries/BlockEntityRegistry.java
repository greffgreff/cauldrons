package com.greffgreff.cauldrons.registries;

import com.greffgreff.cauldrons.Main;
import com.greffgreff.cauldrons.blockEntities.CauldronBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("ConstantConditions")
public final class BlockEntityRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Main.MOD_ID);

    public static final RegistryObject<BlockEntityType<CauldronBlockEntity>> CAULDRON_BLOCK_ENTITY = BLOCK_ENTITIES.register("cauldron_block_entity", () -> BlockEntityType.Builder.of(CauldronBlockEntity::new, BlockRegistry.CAULDRON.get()).build(null));
}