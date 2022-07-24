package com.greffgreff.cauldrons.events;

import com.greffgreff.cauldrons.blocks.CauldronBlock;
import com.greffgreff.cauldrons.utils.Console;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class CauldronBlockEvent {

//    @SubscribeEvent
//    public static void onBlockPlaceEvent(BlockEvent.EntityPlaceEvent event) {
//        if (event.getPlacedBlock().getBlock() instanceof CauldronBlock) {
//            BlockState blockState = event.getPlacedBlock();
//            blockState.setValue(CauldronBlock.FILL_LEVEL, 1);
//            Console.debug(blockState);
//        }
//    }

    @SubscribeEvent
    public static void onPlayerInteractEvent(PlayerInteractEvent event) {
        BlockState blockState = event.getLevel().getBlockState(event.getPos());
        ItemStack itemStack = event.getItemStack();

        if (blockState.getBlock() instanceof CauldronBlock && itemStack.getItem() instanceof BucketItem bucket) {
            blockState.setValue(CauldronBlock.FILL_LEVEL, 1);

            Console.debug(blockState);
        }
    }
}
