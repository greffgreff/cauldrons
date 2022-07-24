package com.greffgreff.cauldrons.registries;

import com.greffgreff.cauldrons.Main;
import com.greffgreff.cauldrons.blocks.Cauldron;
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
    public static final DeferredRegister<Block> GAME_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "minecarft");

    public static final RegistryObject<Block> CAULDRON = registerBlock("cauldron", Cauldron::new);

    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
        return block;
    }

    public static <B extends Block> RegistryObject<B> overrideBlock(String name, Supplier<B> supplier) {
        RegistryObject<B> block = GAME_BLOCKS.register(name, supplier);
        ItemRegistry.GAME_ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
        return block;
    }
}