package com.greffgreff.cauldrons.registries;

import com.greffgreff.cauldrons.Main;
import com.greffgreff.cauldrons.blocks.BigBertaBlock;
import com.greffgreff.cauldrons.blocks.CauldronBlock;
import com.greffgreff.cauldrons.blocks.PyramidBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);

    public static final RegistryObject<Block> CAULDRON = register("cauldron", CauldronBlock::new);
    public static final RegistryObject<Block> PYRAMID = register("pyramid", PyramidBlock::new);
    public static final RegistryObject<Block> BIG_BERTA = register("big_berta", BigBertaBlock::new);

    public static <B extends Block> RegistryObject<B> register(String name, Supplier<B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
        return block;
    }
}