package com.greffgreff.cauldrons.events;

import com.greffgreff.cauldrons.blocks.CauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class CauldronBlockEvent {

    @SubscribeEvent
    public static void onBlockPlaceEvent(BlockEvent.EntityPlaceEvent event) {

        if (event.getPlacedBlock().getBlock() instanceof CauldronBlock) {
            BlockState blockState = event.getPlacedBlock();

            blockState.setValue(CauldronBlock.FILL_LEVEL, 1);
        }
    }

}
