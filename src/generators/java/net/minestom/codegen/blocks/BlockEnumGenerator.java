package net.minestom.codegen.blocks;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.javapoet.*;
import net.minestom.codegen.EnumGenerator;
import net.minestom.codegen.MinestomEnumGenerator;
import net.minestom.codegen.PrismarinePaths;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockAlternative;
import net.minestom.server.instance.block.BlockVariation;
import net.minestom.server.registry.Registries;
import net.minestom.server.utils.NamespaceID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.Modifier;
import java.io.*;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Generates a Block enum containing all data about blocks
 */
public class BlockEnumGenerator extends MinestomEnumGenerator<BlockContainer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockEnumGenerator.class);

    private final String targetVersion;
    private final File targetFolder;


    public static void main(String[] args) throws IOException {
        String targetVersion;
        if (args.length < 1) {
            System.err.println("Usage: <MC version> [target folder]");
            return;
        }

        targetVersion = args[0];

        String targetPart = DEFAULT_TARGET_PATH;
        if (args.length >= 2) {
            targetPart = args[1];
        }

        File targetFolder = new File(targetPart);
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }

        new BlockEnumGenerator(targetVersion, targetFolder);
    }

    private BlockEnumGenerator(String targetVersion, File targetFolder) throws IOException {
        this.targetVersion = targetVersion;
        this.targetFolder = targetFolder;
        generateTo(targetFolder);
    }

    /**
     * Compiles all block information in a single location
     *
     * @param prismarineJSBlocks
     * @param burgerBlocks
     */
    private Collection<BlockContainer> compile(List<PrismarineJSBlock> prismarineJSBlocks, List<BurgerBlock> burgerBlocks) {
        TreeSet<BlockContainer> blocks = new TreeSet<>(BlockContainer::compareTo);
        // ensure the 3 list have the same length and order
        prismarineJSBlocks.sort(Comparator.comparing(block -> NamespaceID.from(block.name).toString()));
        burgerBlocks.sort(Comparator.comparing(block -> NamespaceID.from(block.text_id).toString()));

        // if one of these tests fail, you probably forgot to clear the minecraft_data cache before launching this program
        if (prismarineJSBlocks.size() != burgerBlocks.size()) {
            throw new Error("Burger's block count is different from PrismarineJS count! Try clearing the minecraft_data cache");
        }

        for (int i = 0; i < prismarineJSBlocks.size(); i++) {
            PrismarineJSBlock prismarine = prismarineJSBlocks.get(i);
            BurgerBlock burger = burgerBlocks.get(i);

            List<BlockContainer.BlockVariation> variations = null;
            if (prismarine.variations != null) {
                variations = new LinkedList<>();
                for (PrismarineJSBlock.Variation s : prismarine.variations) {
                    variations.add(new BlockContainer.BlockVariation(s.metadata, s.displayName));
                }
            }

            NamespaceID name = NamespaceID.from("minecraft", prismarine.name);

            BlockContainer block = new BlockContainer(prismarine.id, name, prismarine.hardness, burger.resistance, burger.blockEntity == null ? null : NamespaceID.from(burger.blockEntity.name), variations);
            if (!"empty".equals(prismarine.boundingBox)) {
                block.setSolid();
            }
            if (name.equals(NamespaceID.from("minecraft:water")) || name.equals(NamespaceID.from("minecraft:flowing_water")) || name.equals(NamespaceID.from("minecraft:lava")) || name.equals(NamespaceID.from("minecraft:flowing_lava"))) {
                block.setLiquid();
            }
            boolean isAir = name.equals(NamespaceID.from("minecraft:air"));
            if (isAir) {
                block.setAir();
            }

            blocks.add(block);
        }

        return blocks;
    }

    /**
     * Extracts block information from Burger
     *
     * @param gson
     * @param path
     * @return
     * @throws IOException
     */
    private List<BurgerBlock> parseBlocksFromBurger(Gson gson, String path) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            JsonObject dictionary = gson.fromJson(bufferedReader, JsonArray.class).get(0).getAsJsonObject();
            JsonObject tileEntityMap = dictionary.getAsJsonObject("tileentity").getAsJsonObject("tileentities");

            Map<String, BurgerTileEntity> block2entityMap = new HashMap<>();
            for (var entry : tileEntityMap.entrySet()) {
                BurgerTileEntity te = gson.fromJson(entry.getValue(), BurgerTileEntity.class);
                if (te.blocks != null) {
                    for (String block : te.blocks) {
                        block2entityMap.put(block, te);
                    }
                }
            }

            JsonObject blockMap = dictionary.getAsJsonObject("blocks").getAsJsonObject("block");

            LOGGER.debug("\tExtracting blocks");
            List<BurgerBlock> blocks = new LinkedList<>();
            for (var entry : blockMap.entrySet()) {
                BurgerBlock block = gson.fromJson(entry.getValue(), BurgerBlock.class);
                block.blockEntity = block2entityMap.get(block.text_id);
                blocks.add(block);
            }

            return blocks;
        }
    }

    /**
     * Extract block information from PrismarineJS (submodule of Minestom)
     *
     * @param gson
     * @param blockFile
     * @return
     * @throws IOException
     */
    private List<PrismarineJSBlock> parseBlocksFromPrismarineJS(Gson gson, File blockFile) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(blockFile))) {
            PrismarineJSBlock[] blocks = gson.fromJson(bufferedReader, PrismarineJSBlock[].class);
            return Arrays.asList(blocks);
        }
    }

    private static final Pattern NONLATIN = Pattern.compile("[^\\w_]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("_");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toUpperCase(Locale.ENGLISH);
    }

    @Override
    public String getPackageName() {
        return "net.minestom.server.instance.block";
    }

    @Override
    public String getClassName() {
        return "Block";
    }

    @Override
    protected Collection<BlockContainer> compile() throws IOException {
        Gson gson = new Gson();

        // load properties from Prismarine
        LOGGER.debug("Finding path for PrismarineJS blocks");
        JsonObject dataPaths = gson.fromJson(new BufferedReader(new FileReader(PRISMARINE_JS_DATA_PATHS)), JsonObject.class);
        JsonObject pathsJson = dataPaths.getAsJsonObject("pc").getAsJsonObject(targetVersion);

        PrismarinePaths paths = gson.fromJson(pathsJson, PrismarinePaths.class);
        LOGGER.debug("Loading PrismarineJS blocks data");
        List<PrismarineJSBlock> prismarineJSBlocks = parseBlocksFromPrismarineJS(gson, paths.getBlockFile());

        LOGGER.debug("Loading Burger blocks data (requires Internet connection)");
        List<BurgerBlock> burgerBlocks = parseBlocksFromBurger(gson, BURGER_URL_BASE_URL + targetVersion + ".json");

        LOGGER.debug("Compiling information");
        return compile(prismarineJSBlocks, burgerBlocks);
    }

    @Override
    protected void prepare(EnumGenerator generator) {
        ClassName className = ClassName.get(getPackageName(), getClassName());
        generator.addClassAnnotation(AnnotationSpec.builder(SuppressWarnings.class).addMember("value", "{$S}", "deprecation").build());

        generator.setParams(
                ParameterSpec.builder(String.class, "namespaceID").addAnnotation(NotNull.class).build(),
                ParameterSpec.builder(TypeName.SHORT, "id").build(),
                ParameterSpec.builder(TypeName.DOUBLE, "hardness").build(),
                ParameterSpec.builder(TypeName.DOUBLE, "resistance").build(),
                ParameterSpec.builder(TypeName.BOOLEAN, "isAir").build(),
                ParameterSpec.builder(TypeName.BOOLEAN, "isSolid").build(),
                ParameterSpec.builder(NamespaceID.class, "blockEntity").addAnnotation(Nullable.class).build(),
                ParameterSpec.builder(ParameterizedTypeName.get(List.class, BlockVariation.class), "variations").addAnnotation(Nullable.class).build(),
                ParameterSpec.builder(BlockVariation[].class, "variationsArray").addAnnotation(Nullable.class).build()
        );

        generator.addMethod("getBlockId", new ParameterSpec[0], TypeName.SHORT, code -> code.addStatement("return id"));
        generator.addMethod("getName", new ParameterSpec[0], ClassName.get(String.class), code -> code.addStatement("return namespaceID"));
        generator.addMethod("isAir", new ParameterSpec[0], TypeName.BOOLEAN, code -> code.addStatement("return isAir"));
        generator.addMethod("hasBlockEntity", new ParameterSpec[0], TypeName.BOOLEAN, code -> code.addStatement("return blockEntity != null"));
        generator.addMethod("getBlockEntityName", new ParameterSpec[0], ClassName.get(NamespaceID.class), code -> code.addStatement("return blockEntity"));
        generator.addMethod("isSolid", new ParameterSpec[0], TypeName.BOOLEAN, code -> code.addStatement("return isSolid"));
        generator.addMethod("isLiquid", new ParameterSpec[0], TypeName.BOOLEAN, code -> code.addStatement("return this == WATER || this == LAVA"));
        generator.addMethod("getHardness", new ParameterSpec[0], TypeName.DOUBLE, code -> code.addStatement("return hardness"));
        generator.addMethod("getResistance", new ParameterSpec[0], TypeName.DOUBLE, code -> code.addStatement("return resistance"));
        generator.addMethod("breaksInstantaneously", new ParameterSpec[0], TypeName.BOOLEAN, code -> code.addStatement("return hardness == 0"));

        generator.addMethod("getVariation", new ParameterSpec[]{ParameterSpec.builder(TypeName.BYTE, "metadata").build()}, ClassName.get(BlockVariation.class), code -> {
            code.beginControlFlow("if(metadata < 0 || metadata > 15 || variations == null)")
                    .addStatement("return null")
                    .endControlFlow()
                    .addStatement("return $N[$N]", "variationsArray", "metadata");
        });
        generator.addMethod("getVariations", new ParameterSpec[0], ParameterizedTypeName.get(List.class, BlockVariation.class), code -> code.addStatement("return variations"));
        generator.addMethod("toStateId", new ParameterSpec[]{ParameterSpec.builder(TypeName.BYTE, "metadata").build()}, TypeName.SHORT, code -> code.addStatement("return (short) ((id << 4) | (metadata & 15))"));
        generator.addStaticMethod("fromStateId", new ParameterSpec[]{ParameterSpec.builder(TypeName.SHORT, "blockStateId").build()}, className, code -> code.addStatement("return $T.blocks[blockStateId >> 4]", ClassName.get("net.minestom.server.instance.block", "BlockArray")));
        generator.addStaticMethod("toMetadata", new ParameterSpec[]{ParameterSpec.builder(TypeName.SHORT, "blockStateId").build()}, TypeName.BYTE, code -> code.addStatement("return (byte) (blockStateId & 0xFF)"));
        generator.appendToConstructor(code -> {
            code
                    .addStatement("$T.blocks[id] = this", ClassName.get("net.minestom.server.instance.block", "BlockArray"))
                    .addStatement("$T.blocks.put($T.from(namespaceID), this)", Registries.class, NamespaceID.class);
        });
    }

    @Override
    protected void writeSingle(EnumGenerator generator, BlockContainer block) {
        String blockName = snakeCaseToCapitalizedCamelCase(block.getId().getPath());
        blockName = blockName.replace("_", "");
        ClassName blockClass = ClassName.get(getPackageName() + ".states", blockName);

        String instanceName = block.getId().getPath().toUpperCase();

        StringBuilder format = new StringBuilder("$L, $L, $L, $L, $L, $L, $L");

        List<Object> arguments = new ArrayList<>(Arrays.asList(
                "\"" + block.getId().toString() + "\"",
                "(short) " + block.getOrdinal(),
                block.getHardness(),
                block.getResistance(),
                block.isAir(),
                block.isSolid(),
                block.getBlockEntityName() != null ? "NamespaceID.from(\"" + block.getBlockEntityName() + "\")" : "null"
        ));

        if (block.getVariations() != null) {
            format.append(", $T.$N, $T.$N");
            arguments.add(blockClass);
            arguments.add("variations");
            arguments.add(blockClass);
            arguments.add("variationsArray");
        } else {
            format.append(", $L, $L");
            arguments.add(null);
            arguments.add(null);
        }

        generator.addInstance(instanceName, TypeSpec.anonymousClassBuilder(format.toString(), arguments.toArray()).build());
    }

    @Override
    protected List<JavaFile> postGeneration(Collection<BlockContainer> items) throws IOException {
        List<JavaFile> additionalFiles = new LinkedList<>();

        TypeSpec blockArrayClass = TypeSpec.classBuilder("BlockArray")
                .addModifiers(Modifier.FINAL)
                .addField(FieldSpec.builder(Block[].class, "blocks").initializer("new Block[Short.MAX_VALUE]").addModifiers(Modifier.STATIC, Modifier.FINAL).build())
                .build();
        additionalFiles.add(JavaFile.builder(getPackageName(), blockArrayClass).indent("    ").skipJavaLangImports(true).build());

        LOGGER.debug("Writing subclasses for block alternatives...");

        for (BlockContainer block : items) {
            if (block.getVariations() == null) {
                continue;
            }

            String blockName = snakeCaseToCapitalizedCamelCase(block.getId().getPath());
            blockName = blockName.replace("_", "");
            TypeSpec.Builder subclass = TypeSpec.classBuilder(blockName)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

            ParameterizedTypeName blockVariationListType = ParameterizedTypeName.get(List.class, BlockVariation.class);
            FieldSpec.Builder variationsField = FieldSpec.builder(blockVariationListType, "variations")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC);

            FieldSpec.Builder variationsArrayField = FieldSpec.builder(BlockVariation[].class, "variationsArray")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .initializer("new $T[16]", BlockVariation.class);

            CodeBlock.Builder staticBlock = CodeBlock.builder()
                    .addStatement("$T list = new $T($L)", blockVariationListType, ParameterizedTypeName.get(ArrayList.class, BlockVariation.class), block.getVariations().size());

            for (BlockContainer.BlockVariation variation : block.getVariations()) {
                String fieldName = toSlug(variation.getDisplayName());
                FieldSpec.Builder field = FieldSpec.builder(BlockVariation.class, fieldName)
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .initializer("new $T($L, $S)", BlockVariation.class, "(byte) " + variation.getMetadata(), variation.getDisplayName());

                staticBlock.addStatement("$N.add($N)", "list", fieldName);
                staticBlock.addStatement("$N[$L]= $N", "variationsArray", variation.getMetadata(), fieldName);

                subclass.addField(field.build());
            }

            staticBlock.addStatement("$N = $T.$N($N)", "variations", Collections.class, "unmodifiableList", "list");

            subclass.addField(variationsField.build());
            subclass.addField(variationsArrayField.build());
            subclass.addStaticBlock(staticBlock.build());

            additionalFiles.add(JavaFile.builder(getPackageName() + ".states", subclass.build())
                    .indent("    ")
                    .skipJavaLangImports(true)
                    .build());
        }

        return additionalFiles;
    }

    @Override
    protected void postWrite(EnumGenerator generator) {
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }
}
