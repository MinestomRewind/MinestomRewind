package net.minestom.codegen;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.javapoet.*;
import net.minestom.codegen.blocks.PrismarineJSBlock;
import net.minestom.server.registry.Registries;
import net.minestom.server.utils.NamespaceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public abstract class BasicPrismarineEnumGenerator extends MinestomEnumGenerator<BasicPrismarineEnumGenerator.Container> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicPrismarineEnumGenerator.class);

    private final String targetVersion;

    /**
     * Are IDs linear? Biomes are not for instance.
     */
    private final boolean linear;

    /**
     * True if the enum is linear and start by 1 instead of 0
     */
    private boolean incrementOrdinal;

    protected BasicPrismarineEnumGenerator(String targetVersion, File targetFolder, boolean linear, boolean incrementOrdinal) throws IOException {
        this.targetVersion = targetVersion;
        this.linear = linear;
        this.incrementOrdinal = incrementOrdinal;
        generateTo(targetFolder);
    }

    protected BasicPrismarineEnumGenerator(String targetVersion, File targetFolder, boolean linear) throws IOException {
        this(targetVersion, targetFolder, linear, false);
    }

    protected BasicPrismarineEnumGenerator(String targetVersion, File targetFolder) throws IOException {
        this(targetVersion, targetFolder, true);
    }

    @Override
    protected Collection<Container> compile() throws IOException {
        Gson gson = new Gson();

        TreeSet<Container> items = new TreeSet<>();

        // load properties from Prismarine
        LOGGER.debug("Finding path for PrismarineJS blocks");
        JsonObject dataPaths = gson.fromJson(new BufferedReader(new FileReader(PRISMARINE_JS_DATA_PATHS)), JsonObject.class);
        JsonObject pathsJson = dataPaths.getAsJsonObject("pc").getAsJsonObject(targetVersion);

        PrismarinePaths paths = gson.fromJson(pathsJson, PrismarinePaths.class);
        LOGGER.debug("Loading PrismarineJS data");

        final DataItem[] entries = gson.fromJson(new FileReader(getCategoryFile(paths)), DataItem[].class);
        for (var entry : entries) {
            final NamespaceID name = NamespaceID.from("minecraft", entry.name);
            items.add(new Container(entry.id, name));
        }

        return items;
    }

    protected abstract File getCategoryFile(PrismarinePaths paths);

    @Override
    protected void postWrite(EnumGenerator generator) {
        ClassName className = ClassName.get(getPackageName(), getClassName());
        ParameterSpec idParam = ParameterSpec.builder(TypeName.INT, "id").build();
        ParameterSpec[] signature = new ParameterSpec[]{idParam};
        if (linear) {
            final String ordinalIncrementCondition = incrementOrdinal ? " + 1" : "";
            final String ordinalIncrementIndex = incrementOrdinal ? " - 1" : "";
            generator.addStaticMethod("fromId", signature, className, code -> {
                        code.beginControlFlow("if ($N >= 0 && $N < values().length" + ordinalIncrementCondition + ")", idParam, idParam)
                                .addStatement("return values()[$N" + ordinalIncrementIndex + "]", idParam)
                                .endControlFlow()
                                .addStatement("return null");
                    }
            );
        } else {
            generator.addStaticMethod("fromId", signature, className, code -> {
                        code.beginControlFlow("for ($T o : values())", className)
                                .beginControlFlow("if (o.getId() == id)")
                                .addStatement("return o")
                                .endControlFlow()
                                .endControlFlow()
                                .addStatement("return null");
                    }
            );
        }
    }

    protected String identifier(NamespaceID id) {
        return id.getPath().toUpperCase().replace(".", "_"); // block.ambient.cave will be replaced by "BLOCK_AMBIENT_CAVE"
    }

    @Override
    protected List<JavaFile> postGeneration(Collection<Container> items) throws IOException {
        return Collections.emptyList();
    }

    @Override
    protected void prepare(EnumGenerator generator) {
        generator.addClassAnnotation(AnnotationSpec.builder(SuppressWarnings.class).addMember("value", "{$S}", "deprecation").build());
        ClassName registriesClass = ClassName.get(Registries.class);
        if (linear) {
            generator.setParams(ParameterSpec.builder(ClassName.get(String.class), "namespaceID").build());
            generator.addMethod("getId", new ParameterSpec[0], TypeName.INT, code -> code.addStatement("return ordinal()" + (incrementOrdinal ? " + 1" : "")));
        } else {
            generator.setParams(ParameterSpec.builder(ClassName.get(String.class), "namespaceID").build(), ParameterSpec.builder(TypeName.INT, "id").build());
            generator.addMethod("getId", new ParameterSpec[0], TypeName.INT, code -> code.addStatement("return $N", "id"));
        }
        generator.addMethod("getNamespaceID", new ParameterSpec[0], ClassName.get(String.class), code -> code.addStatement("return $N", "namespaceID"));

        generator.appendToConstructor(code -> {
            code.addStatement("$T." + CodeGenerator.decapitalize(getClassName()) + "s.put($T.from($N), this)", registriesClass, NamespaceID.class, "namespaceID");
        });
    }

    @Override
    protected void writeSingle(EnumGenerator generator, Container item) {
        if (linear) {
            generator.addInstance(identifier(item.name), "\"" + item.name.toString() + "\"");
        } else {
            generator.addInstance(identifier(item.name), "\"" + item.name.toString() + "\"", item.id);
        }
    }

    static class Container implements Comparable<Container> {
        private int id;
        private NamespaceID name;

        public Container(int id, NamespaceID name) {
            this.id = id;
            this.name = name;
        }

        public NamespaceID getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        @Override
        public int compareTo(Container o) {
            return Integer.compare(id, o.id);
        }

        @Override
        public String toString() {
            return "Container{" +
                    "id=" + id +
                    ", name=" + name +
                    '}';
        }
    }

    static class DataItem {
        private int id;
        private String name;

        @Override
        public String toString() {
            return "DataItem{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
