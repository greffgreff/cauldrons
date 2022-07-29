package com.greffgreff.cauldrons.events;

import com.greffgreff.cauldrons.models.OBJLoader;
import com.greffgreff.cauldrons.utils.Console;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModelLoaderEvents {

	@SubscribeEvent
	public static void registerModelLoaderEvent(final ModelEvent.RegisterGeometryLoaders event) {
		event.register(OBJLoader.LOADER_NAME, OBJLoader.INSTANCE);
	}
}
