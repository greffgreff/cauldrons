package com.greffgreff.creepvines.registries;

import com.greffgreff.creepvines.Main;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

    public static RegistryObject<Item> registerBlockItem(String name, Block block, CreativeModeTab tab) {
        return ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().tab(tab)));
    }

    public static RegistryObject<Item> registerItem(String name, Item item, CreativeModeTab tab) {
        return null;
    }
}
