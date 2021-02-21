package net.minestom.codegen.enchantment;

import net.minestom.codegen.BasicPrismarineEnumGenerator;
import net.minestom.codegen.PrismarinePaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class EnchantmentEnumGenerator extends BasicPrismarineEnumGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnchantmentEnumGenerator.class);

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

        new EnchantmentEnumGenerator(targetVersion, targetFolder);
    }

    private EnchantmentEnumGenerator(String targetVersion, File targetFolder) throws IOException {
        super(targetVersion, targetFolder);
    }

    @Override
    protected File getCategoryFile(PrismarinePaths paths) {
        return paths.getEnchantmentsFile();
    }

    @Override
    public String getPackageName() {
        return "net.minestom.server.item";
    }

    @Override
    public String getClassName() {
        return "Enchantment";
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }
}
