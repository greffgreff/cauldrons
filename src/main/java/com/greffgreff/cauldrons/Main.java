package com.greffgreff.cauldrons;

import com.greffgreff.cauldrons.registries.BlockEntityRegistry;
import com.greffgreff.cauldrons.registries.BlockRegistry;
import com.greffgreff.cauldrons.registries.ItemRegistry;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Main.MOD_ID)
public class Main {
    public static final String MOD_ID = "cauldrons";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Main() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BlockRegistry.BLOCKS.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        BlockEntityRegistry.BLOCK_ENTITIES.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }
}
