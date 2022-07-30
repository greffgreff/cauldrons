package com.greffgreff.cauldrons.models;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.greffgreff.cauldrons.Main;
import com.greffgreff.cauldrons.utils.Console;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.client.model.geometry.IGeometryLoader;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@SuppressWarnings("ConstantConditions")
public final class OBJLoader implements IGeometryLoader<OBJModel> {
    public static final OBJLoader INSTANCE = new OBJLoader();
    private static final ResourceManager MANAGER = Minecraft.getInstance().getResourceManager();
    private static final Pattern MTLLIB = Pattern.compile("^mtllib\\s+(.*)$", Pattern.MULTILINE);
    private static final Pattern USEMTL = Pattern.compile("^usemtl\\s+(.*)$", Pattern.MULTILINE);
    private static final Pattern NEWMTL = Pattern.compile("^newmtl\\s+(.*)$", Pattern.MULTILINE);
    private static final Pattern MAP_KD = Pattern.compile("^map_Kd\\s+(.*)$", Pattern.MULTILINE);

    private OBJLoader() {
    }

    @Override
    public OBJModel read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
        if (!jsonObject.has("model"))
            throw new JsonParseException("OBJ Loader requires a 'model' key that points to a valid .OBJ model.");

        String modelLocation = jsonObject.get("model").getAsString();
        ResourceLocation obj = getBlockResource(modelLocation);

        try {
            String objData_ = IOUtils.toString(getStream(obj));
            ResourceLocation mtl = getResourceFromKey(MTLLIB, objData_);
            String mtlData = IOUtils.toString(getStream(mtl));
            ResourceLocation texture = getResourceFromKey(MAP_KD, mtlData);

            decomposeToProper(getStream(obj)).forEach(Console::debug);

        } catch (Exception e) {
            Console.debug(e.getMessage());
        }
        return null;
    }

    private static ResourceLocation getBlockResource(String location) {
        if (!location.contains(":")) {
            return new ResourceLocation(Main.MOD_ID, "models/block/" + location);
        }
        return new ResourceLocation(location);
    }

    private static InputStream getStream(ResourceLocation path) {
        try {
            return MANAGER.open(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFirstMatch(Pattern pattern, String data) {
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            return matcher.group().split(" ")[1];
        }
        return null;
    }

    private static ResourceLocation getResourceFromKey(Pattern key, String data) {
        String fileName = getFirstMatch(key, data);
        return getBlockResource(fileName);
    }

    private static Stream<Pair<String, String>> decomposeToProper(InputStream input) {
        return new BufferedReader(new InputStreamReader(input))
                .lines()
                .filter(l -> !l.trim().isEmpty() && !l.startsWith("#"))
                .map(l -> {
                    StringTokenizer tokenizer = new StringTokenizer(l);
                    return Pair.of(tokenizer.nextToken(), tokenizer.nextToken());
                });
    }
}
