package net.minestom.codegen.stats;

import net.minestom.codegen.BasicEnumGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

// TODO(koesie10): Update for 1.8, can probably use Burger data
public class StatsEnumGenerator extends BasicEnumGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatsEnumGenerator.class);

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

        new StatsEnumGenerator(targetFolder);
    }

    private StatsEnumGenerator(File targetFolder) throws IOException {
        super(targetFolder);
    }

    @Override
    protected String getCategoryID() {
        return "minecraft:custom_stat";
    }

    @Override
    public String getPackageName() {
        return "net.minestom.server.stat";
    }

    @Override
    public String getClassName() {
        return "StatisticType";
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }
}
