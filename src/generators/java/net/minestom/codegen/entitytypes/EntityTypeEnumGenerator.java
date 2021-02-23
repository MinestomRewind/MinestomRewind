package net.minestom.codegen.entitytypes;

import com.google.common.base.CaseFormat;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.javapoet.*;
import net.minestom.codegen.*;
import net.minestom.server.registry.Registries;
import net.minestom.server.utils.NamespaceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public class EntityTypeEnumGenerator extends MinestomEnumGenerator<EntityTypeEnumGenerator.EntityTypeContainer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityTypeEnumGenerator.class);

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

        new EntityTypeEnumGenerator(targetVersion, targetFolder);
    }

    private final String targetVersion;

    private EntityTypeEnumGenerator(String targetVersion, File targetFolder) throws IOException {
        this.targetVersion = targetVersion;
        generateTo(targetFolder);
    }

    @Override
    protected Collection<EntityTypeContainer> compile() throws IOException {
        Gson gson = new Gson();

        TreeSet<EntityTypeContainer> items = new TreeSet<>();

        // load properties from Prismarine
        LOGGER.debug("Finding path for PrismarineJS entities");
        JsonObject dataPaths = gson.fromJson(new BufferedReader(new FileReader(PRISMARINE_JS_DATA_PATHS)), JsonObject.class);
        JsonObject pathsJson = dataPaths.getAsJsonObject("pc").getAsJsonObject(targetVersion);

        PrismarinePaths paths = gson.fromJson(pathsJson, PrismarinePaths.class);
        LOGGER.debug("Loading PrismarineJS data");

        final DataItem[] entries = gson.fromJson(new FileReader(paths.getEntitiesFile()), DataItem[].class);
        for (var entry : entries) {
            final NamespaceID name = NamespaceID.from("minecraft", entry.name);
            items.add(new EntityTypeContainer(entry.internalId, entry.id, name));
        }

        return items;
    }

    @Override
    protected void postWrite(EnumGenerator generator) {
        ClassName className = ClassName.get(getPackageName(), getClassName());
        ParameterSpec idParam = ParameterSpec.builder(TypeName.INT, "id").build();
        ParameterSpec[] signature = new ParameterSpec[]{idParam};
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

    @Override
    protected List<JavaFile> postGeneration(Collection<EntityTypeContainer> items) throws IOException {
        return Collections.emptyList();
    }

    @Override
    protected void prepare(EnumGenerator generator) {
        generator.addClassAnnotation(AnnotationSpec.builder(SuppressWarnings.class).addMember("value", "{$S}", "deprecation").build());
        ClassName registriesClass = ClassName.get(Registries.class);
        generator.setParams(
                ParameterSpec.builder(ClassName.get(String.class), "namespaceID").build(),
                ParameterSpec.builder(TypeName.INT, "id").build(),
                ParameterSpec.builder(TypeName.BYTE, "protocolId").build()
        );
        generator.addMethod("getId", new ParameterSpec[0], TypeName.INT, code -> code.addStatement("return $N", "id"));
        generator.addMethod("getProtocolId", new ParameterSpec[0], TypeName.BYTE, code -> code.addStatement("return $N", "protocolId"));
        generator.addMethod("getNamespaceID", new ParameterSpec[0], ClassName.get(String.class), code -> code.addStatement("return $N", "namespaceID"));

        generator.appendToConstructor(code -> {
            code.addStatement("$T." + CodeGenerator.decapitalize(getClassName()) + "s.put($T.from($N), this)", registriesClass, NamespaceID.class, "namespaceID");
        });
    }

    @Override
    protected void writeSingle(EnumGenerator generator, EntityTypeContainer item) {
        generator.addInstance(identifier(item.name), "\"" + item.name.toString() + "\"", item.id, "(byte) " + item.protocolId);
    }

    protected String identifier(NamespaceID id) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, id.getPath().replace(" ", "")); // CaveSpider will be replaced by "CAVE_SPIDER"
    }

    @Override
    public String getPackageName() {
        return "net.minestom.server.entity";
    }

    @Override
    public String getClassName() {
        return "EntityType";
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    static class EntityTypeContainer implements Comparable<EntityTypeContainer> {
        private int id;
        private int protocolId;
        private NamespaceID name;

        public EntityTypeContainer(int id, int protocolId, NamespaceID name) {
            this.id = id;
            this.protocolId = protocolId;
            this.name = name;
        }

        public NamespaceID getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public int getProtocolId() {
            return protocolId;
        }

        @Override
        public int compareTo(EntityTypeContainer o) {
            return Integer.compare(id, o.id);
        }
    }

    static class DataItem {
        private int internalId;
        private int id;
        private String name;
    }
}
