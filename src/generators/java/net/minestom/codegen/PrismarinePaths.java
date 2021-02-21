package net.minestom.codegen;

import java.io.File;

public class PrismarinePaths {

    private String blocks;
    private String biomes;
    private String enchantments;
    private String effects;
    private String items;
    private String recipes;
    private String instruments;
    private String materials;
    private String entities;
    private String particles;
    private String protocol;
    private String windows;
    private String version;
    private String language;

    public File getBlockFile() {
        return getFile(blocks, "blocks");
    }

    public File getItemsFile() {
        return getFile(items, "items");
    }

    public File getBiomesFile() {
        return getFile(biomes, "biomes");
    }

    public File getEnchantmentsFile() {
        return getFile(enchantments, "enchantments");
    }

    public File getEffectsFile() {
        return getFile(effects, "effects");
    }

    public File getEntitiesFile() {
        return getFile(entities, "entities");
    }

    public File getParticlesFile() {
        return getFile(particles, "particles");
    }

    public File getFile(String path, String type) {
        return new File("prismarine-minecraft-data/data/" + path + "/" + type + ".json");
    }
}
