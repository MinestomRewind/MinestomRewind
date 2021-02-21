package net.minestom.codegen.sounds;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterSpec;
import net.minestom.codegen.CodeGenerator;
import net.minestom.codegen.EnumGenerator;
import net.minestom.codegen.MinestomEnumGenerator;
import net.minestom.server.registry.Registries;
import net.minestom.server.utils.NamespaceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SoundEnumGenerator extends MinestomEnumGenerator<SoundEnumGenerator.SoundContainer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SoundEnumGenerator.class);

    private final String targetVersion;

    public static void main(String[] args) throws IOException {
        String targetVersion;
        if(args.length < 1) {
            System.err.println("Usage: <MC version> [target folder]");
            return;
        }

        targetVersion = args[0];

        String targetPart = DEFAULT_TARGET_PATH;
        if(args.length >= 2) {
            targetPart = args[1];
        }

        File targetFolder = new File(targetPart);
        if(!targetFolder.exists()) {
            targetFolder.mkdirs();
        }

        new SoundEnumGenerator(targetVersion, targetFolder);
    }

    private SoundEnumGenerator(String targetVersion, File targetFolder) throws IOException {
        this.targetVersion = targetVersion;
        generateTo(targetFolder);
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected Collection<SoundContainer> compile() throws IOException {
        Gson gson = new Gson();

        JsonObject soundsJson = downloadSoundsJson(gson);

        List<SoundContainer> sounds = new ArrayList<>();

        for (String soundName : soundsJson.keySet()) {
            sounds.add(new SoundContainer(NamespaceID.from("minecraft", soundName)));
        }

        return sounds;
    }

    @Override
    protected void postWrite(EnumGenerator generator) {

    }

    @Override
    protected List<JavaFile> postGeneration(Collection<SoundContainer> items) throws IOException {
        return Collections.emptyList();
    }

    private JsonObject downloadSoundsJson(Gson gson) throws IOException {
        // Mojang's version manifest is located at https://launchermeta.mojang.com/mc/game/version_manifest.json
        // If we query this (it's a json object), we can then search for the id we want.
        InputStream versionManifestStream = new URL("https://launchermeta.mojang.com/mc/game/version_manifest.json").openStream();
        LOGGER.debug("Successfully queried Mojang's version_manifest.json.");

        JsonObject versionManifestJson = gson.fromJson(new InputStreamReader(versionManifestStream), JsonObject.class);
        LOGGER.debug("Successfully read Mojang's version_manifest.json into a json object.");

        JsonArray versionArray = versionManifestJson.getAsJsonArray("versions");
        LOGGER.debug("Iterating over the version manifest to find a version with the id {}.", targetVersion);

        JsonObject versionEntry = null;
        for (JsonElement element : versionArray) {
            if (element.isJsonObject()) {
                JsonObject entry = element.getAsJsonObject();
                if (entry.get("id").getAsString().equals(targetVersion)) {
                    LOGGER.debug("Successfully found a version with the id {}.", targetVersion);
                    versionEntry = entry;
                    break;
                }
            }
        }
        if (versionEntry == null) {
            throw new IOException("Could not find " + targetVersion + " in Mojang's official list of minecraft versions.");
        }

        // We now have the entry we want and it gives us access to the json file containing the downloads.
        String versionUrl = versionEntry.get("url").getAsString();
        InputStream versionStream = new URL(versionUrl).openStream();
        LOGGER.debug("Successfully queried {}.json.", targetVersion);

        JsonObject versionJson = gson.fromJson(new InputStreamReader(versionStream), JsonObject.class);
        LOGGER.debug("Successfully read {}.json into a json object.", targetVersion);

        // Now we need to navigate to "assetIndex.url"
        String assetIndexUrl = versionJson.getAsJsonObject("assetIndex").get("url").getAsString();

        InputStream assetIndexStream = new URL(assetIndexUrl).openStream();
        LOGGER.debug("Successfully queried Mojang's asset index.");
        JsonObject assetIndexJson = gson.fromJson(new InputStreamReader(assetIndexStream), JsonObject.class);
        LOGGER.debug("Successfully read Mojang's asset index into a json object.");

        // Now we we need to navigate to "objects.minecraft/sounds.json.hash"
        String soundsJsonHash = assetIndexJson.getAsJsonObject("objects").getAsJsonObject("minecraft/sounds.json").get("hash").getAsString();
        String soundsJsonUrl = "http://resources.download.minecraft.net/" + soundsJsonHash.substring(0, 2) + "/" + soundsJsonHash;
        InputStream soundsJsonStream = new URL(soundsJsonUrl).openStream();
        LOGGER.debug("Successfully queried sounds.json");

        JsonObject soundsObject = gson.fromJson(new InputStreamReader(soundsJsonStream), JsonObject.class);
        LOGGER.debug("Successfully read sounds.json into a json object.");

        return soundsObject;
    }

    @Override
    protected void prepare(EnumGenerator generator) {
        generator.addClassAnnotation(AnnotationSpec.builder(SuppressWarnings.class).addMember("value", "{$S}", "deprecation").build());
        ClassName registriesClass = ClassName.get(Registries.class);
        generator.setParams(ParameterSpec.builder(ClassName.get(String.class), "namespaceID").build());
        generator.addMethod("getNamespaceID", new ParameterSpec[0], ClassName.get(String.class), code -> code.addStatement("return $N", "namespaceID"));

        generator.appendToConstructor(code -> {
            code.addStatement("$T." + CodeGenerator.decapitalize(getClassName()) + "s.put($T.from($N), this)", registriesClass, NamespaceID.class, "namespaceID");
        });
    }

    @Override
    protected void writeSingle(EnumGenerator generator, SoundContainer item) {
        generator.addInstance(identifier(item.name), "\"" + item.name.toString() + "\"");
    }

    private String identifier(NamespaceID id) {
        return id.getPath().toUpperCase().replace(".", "_"); // block.ambient.cave will be replaced by "BLOCK_AMBIENT_CAVE"
    }

    @Override
    public String getPackageName() {
        return "net.minestom.server.sound";
    }

    @Override
    public String getClassName() {
        return "Sound";
    }

    static class SoundContainer implements Comparable<SoundContainer> {
        private NamespaceID name;

        private SoundContainer(NamespaceID name) {
            this.name = name;
        }

        public NamespaceID getName() {
            return name;
        }

        @Override
        public int compareTo(SoundContainer o) {
            return name.toString().compareTo(o.name.toString());
        }
    }
}
