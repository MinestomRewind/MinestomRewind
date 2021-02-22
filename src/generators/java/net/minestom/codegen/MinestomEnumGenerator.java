package net.minestom.codegen;

import com.squareup.javapoet.JavaFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @param <Container> the type of container to pass between the extraction and generation phases
 */
public abstract class MinestomEnumGenerator<Container> implements CodeGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MinestomEnumGenerator.class);
    public static final String DEFAULT_TARGET_PATH = "src/autogenerated/java";
    public static final String PRISMARINE_JS_DATA_PATHS = "prismarine-minecraft-data/data/dataPaths.json";
    public static final String BURGER_URL_BASE_URL = "data/";

    /**
     * Package name with '.' replaced by '/'
     * @return
     */
    protected String getRelativeFolderPath() {
        return getPackageName().replace(".", "/");
    }

    @Override
    public List<JavaFile> generate() throws IOException {
        EnumGenerator generator = new EnumGenerator(getPackageName(), getClassName());
        prepare(generator);
        Collection<Container> items = compile();
        for(Container c : items) {
            System.out.println(c);
            writeSingle(generator, c);
        }

        postWrite(generator);
        List<JavaFile> list = new LinkedList<>(generator.generate());
        list.addAll(postGeneration(items));
        return list;
    }

    /**
     * Called after writing all compiled items into the enum generator
     * @param generator
     */
    protected abstract void postWrite(EnumGenerator generator);

    /**
     * Called after code generation (only if generated through a {@link #generateTo(File)} call). Can be used to generate additional files
     * @param items
     */
    protected abstract List<JavaFile> postGeneration(Collection<Container> items) throws IOException;

    /**
     * Package in which to generate the enum
     * @return
     */
    public abstract String getPackageName();

    /**
     * Name of the enum to generate
     * @return
     */
    public abstract String getClassName();

    /**
     * Extracts and gather information about the enum we want to generate.
     * @throws IOException if an error occurred during data gathering
     * @return
     */
    protected abstract Collection<Container> compile() throws IOException;

    /**
     * Prepare the EnumGenerator (package name, class names, imports, constructor...)
     * @param generator
     */
    protected abstract void prepare(EnumGenerator generator);

    /**
     * Write an entry to the generator. The order in which items are provided (via different call) is considered to be
     * the order in which they will presented in the enum
     * @param generator
     * @param item
     */
    protected abstract void writeSingle(EnumGenerator generator, Container item);

    /**
     * Converts a snake case identifier (some_long_name) to a capitalized camel case identifier (SomeLongName)
     * @param identifier
     * @return
     */
    protected String snakeCaseToCapitalizedCamelCase(String identifier) {
        boolean capitalizeNext = true;
        StringBuilder result = new StringBuilder();
        char[] chars = identifier.toCharArray();
        for (int i = 0; i < identifier.length(); i++) {
            char currentCharacter = chars[i];
            if(capitalizeNext) {
                result.append(Character.toUpperCase(currentCharacter));
                capitalizeNext = false;
            } else if(currentCharacter == '_') {
                capitalizeNext = true;
            } else {
                result.append(currentCharacter);
            }
        }
        return result.toString();
    }
}
