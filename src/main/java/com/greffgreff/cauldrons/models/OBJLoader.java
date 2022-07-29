package com.greffgreff.cauldrons.models;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.greffgreff.cauldrons.Main;
import com.greffgreff.cauldrons.utils.Console;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.geometry.IGeometryLoader;

public final class OBJLoader implements IGeometryLoader<OBJModel> {
    public static final OBJLoader INSTANCE = new OBJLoader();
    public static final String LOADER_NAME = "fuck_off";
    //   "loader": "forge:obj",

    private OBJLoader() { }

    @Override
    public OBJModel read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
        Console.debug(jsonObject);
        return null;
    }
}
