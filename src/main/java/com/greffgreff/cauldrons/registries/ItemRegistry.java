package com.greffgreff.cauldrons.registries;

import com.greffgreff.cauldrons.Main;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);
    public static final DeferredRegister<Item> GAME_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");

    public static RegistryObject<Item> registerBlockItem(String name, Block block, CreativeModeTab tab) {
        return ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().tab(tab)));
    }

    public static RegistryObject<Item> overrideBlockItem(String name, Block block, CreativeModeTab tab) {
        return GAME_ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().tab(tab)));
    }

    public static RegistryObject<Item> registerItem(String name, Item item, CreativeModeTab tab) {
        return null;
    }
}
