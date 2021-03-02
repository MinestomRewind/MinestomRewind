package net.minestom.codegen.entitytypes;

import com.google.common.base.CaseFormat;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.javapoet.*;
import net.minestom.codegen.*;
import net.minestom.codegen.items.BurgerItem;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntitySpawnType;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.metadata.EntityMeta;
import net.minestom.server.registry.Registries;
import net.minestom.server.utils.NamespaceID;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.BiFunction;

public class EntityTypeEnumGenerator extends MinestomEnumGenerator<EntityTypeContainer> {

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

    /**
     * Extract entity information from Burger (submodule of Minestom)
     *
     * @param gson
     * @param path
     * @return
     * @throws IOException
     */
    private Map<String, BurgerEntity> parseEntitiesFromBurger(Gson gson, String path) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            LOGGER.debug("\tConnection established, reading file");
            JsonObject dictionary = gson.fromJson(bufferedReader, JsonArray.class).get(0).getAsJsonObject();
            JsonObject entitiesMap = dictionary.getAsJsonObject("entities").getAsJsonObject("entity");
            Map<String, BurgerEntity> entities = new TreeMap<>();
            for (var entry : entitiesMap.entrySet()) {
                BurgerEntity entity = gson.fromJson(entry.getValue(), BurgerEntity.class);
                entities.put(entity.name, entity);
            }
            return entities;
        }
    }

    @Override
    protected Collection<EntityTypeContainer> compile() throws IOException {
        Gson gson = new Gson();

        Map<String, BurgerEntity> burgerEntities = parseEntitiesFromBurger(gson, BURGER_URL_BASE_URL + targetVersion + ".json");

        TreeSet<EntityTypeContainer> items = new TreeSet<>();

        // load properties from Prismarine
        LOGGER.debug("Finding path for PrismarineJS entities");
        JsonObject dataPaths = gson.fromJson(new BufferedReader(new FileReader(PRISMARINE_JS_DATA_PATHS)), JsonObject.class);
        JsonObject pathsJson = dataPaths.getAsJsonObject("pc").getAsJsonObject(targetVersion);

        PrismarinePaths paths = gson.fromJson(pathsJson, PrismarinePaths.class);
        LOGGER.debug("Loading PrismarineJS data");

        final DataItem[] entries = gson.fromJson(new FileReader(paths.getEntitiesFile()), DataItem[].class);
        for (var entry : entries) {
            // Abstract entities
            if (entry.name.equals("Mob") || entry.name.equals("Monster")) {
                continue;
            }

            BurgerEntity burgerEntity = burgerEntities.get(entry.name);
            if (entry.name.equals("Fishing Float")) {
                burgerEntity = new BurgerEntity(entry.id, entry.name, 0.25, 0.25);
            } else if (burgerEntity == null) {
                throw new IllegalStateException("Missing Burger entity for " + entry.name);
            }

            final NamespaceID name = NamespaceID.from("minecraft", entry.name.replace(" ", ""));
            items.add(new EntityTypeContainer(entry.internalId, entry.id, name, burgerEntity.width, burgerEntity.height));
        }

        items.add(new EntityTypeContainer(1000, 0, NamespaceID.from("minecraft:Player"), 0.6, 1.8));
        items.add(new EntityTypeContainer(1001, 0, NamespaceID.from("minecraft:Painting"), 0.5, 0.5));
        items.add(new EntityTypeContainer(1002, 0, NamespaceID.from("minecraft:ExperienceOrb"), 0.5, 0.5));

        return items;
    }

    @Override
    protected void postWrite(EnumGenerator generator) {
    }

    @Override
    protected List<JavaFile> postGeneration(Collection<EntityTypeContainer> items) throws IOException {
        return Collections.emptyList();
    }

    @Override
    protected void prepare(EnumGenerator generator) {
        ClassName className = ClassName.get(getPackageName(), getClassName());

        generator.addClassAnnotation(AnnotationSpec.builder(SuppressWarnings.class).addMember("value", "{$S}", "deprecation").build());
        generator.setParams(
                ParameterSpec.builder(ClassName.get(String.class), "namespaceID").build(),
                ParameterSpec.builder(TypeName.INT, "id").build(),
                ParameterSpec.builder(TypeName.BYTE, "protocolId").build(),
                ParameterSpec.builder(TypeName.DOUBLE, "width").build(),
                ParameterSpec.builder(TypeName.DOUBLE, "height").build(),
                ParameterSpec.builder(ParameterizedTypeName.get(
                        BiFunction.class,
                        Entity.class,
                        Metadata.class,
                        EntityMeta.class
                ), "metaConstructor").addAnnotation(NotNull.class).build(),
                ParameterSpec.builder(EntitySpawnType.class, "spawnType").addAnnotation(NotNull.class).build()
        );

        generator.appendToConstructor(code -> {
            code.addStatement("$T.$N.put($T.from(namespaceID), this)", Registries.class, "entityTypes", NamespaceID.class);
        });

        generator.addMethod("getId", new ParameterSpec[0], TypeName.INT, code -> {
            code.addStatement("return $N", "id");
        });
        generator.addMethod("getProtocolId", new ParameterSpec[0], TypeName.BYTE, code -> {
            code.addStatement("return $N", "protocolId");
        });
        generator.addMethod("getNamespaceID", new ParameterSpec[0], ClassName.get(String.class), code -> {
            code.addStatement("return $N", "namespaceID");
        });
        generator.addMethod("getWidth", new ParameterSpec[0], TypeName.DOUBLE, code -> {
            code.addStatement("return this.width");
        });
        generator.addMethod("getHeight", new ParameterSpec[0], TypeName.DOUBLE, code -> {
            code.addStatement("return this.height");
        });
        generator.addMethod("getMetaConstructor", new ParameterSpec[0],
                ParameterizedTypeName.get(
                        BiFunction.class,
                        Entity.class,
                        Metadata.class,
                        EntityMeta.class
                ),
                code -> code.addStatement("return this.metaConstructor")
        );
        generator.addMethod("getSpawnType", new ParameterSpec[0], ClassName.get(EntitySpawnType.class), code -> {
            code.addStatement("return this.spawnType");
        });

        generator.addStaticField(ArrayTypeName.of(ClassName.get(EntityType.class)), "VALUES", "values()");

        // TODO: Cache these values
        generator.addStaticMethod("fromId", new ParameterSpec[]{ParameterSpec.builder(TypeName.SHORT, "id").build()}, className, code -> {
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
    protected void writeSingle(EnumGenerator generator, EntityTypeContainer item) {
        generator.addInstance(
                identifier(item.getName()),
                "\"" + item.getName().toString() + "\"",
                item.getId(),
                "(byte) " + item.getProtocolId(),
                item.getWidth(),
                item.getHeight(),
                new ConstructorLambda(ClassName.get(item.getMetaClass())),
                "EntitySpawnType." + item.getSpawnType().name()
        );
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

    static class DataItem {
        private int internalId;
        private int id;
        private String name;
    }
}