package net.minestom.server.particle;

import net.minestom.server.registry.Registries;
import net.minestom.server.utils.NamespaceID;

/**
 * //==============================
 * //  AUTOGENERATED BY EnumGenerator
 * //==============================
 */
@SuppressWarnings({"deprecation"})
public enum Particle {
    EXPLODE("minecraft:explode"),

    LARGEEXPLODE("minecraft:largeexplode"),

    HUGEEXPLOSION("minecraft:hugeexplosion"),

    FIREWORKSSPARK("minecraft:fireworksSpark"),

    BUBBLE("minecraft:bubble"),

    SPLASH("minecraft:splash"),

    WAKE("minecraft:wake"),

    SUSPENDED("minecraft:suspended"),

    DEPTHSUSPEND("minecraft:depthsuspend"),

    CRIT("minecraft:crit"),

    MAGICCRIT("minecraft:magicCrit"),

    SMOKE("minecraft:smoke"),

    LARGESMOKE("minecraft:largesmoke"),

    SPELL("minecraft:spell"),

    INSTANTSPELL("minecraft:instantSpell"),

    MOBSPELL("minecraft:mobSpell"),

    MOBSPELLAMBIENT("minecraft:mobSpellAmbient"),

    WITCHMAGIC("minecraft:witchMagic"),

    DRIPWATER("minecraft:dripWater"),

    DRIPLAVA("minecraft:dripLava"),

    ANGRYVILLAGER("minecraft:angryVillager"),

    HAPPYVILLAGER("minecraft:happyVillager"),

    TOWNAURA("minecraft:townaura"),

    NOTE("minecraft:note"),

    PORTAL("minecraft:portal"),

    ENCHANTMENTTABLE("minecraft:enchantmenttable"),

    FLAME("minecraft:flame"),

    LAVA("minecraft:lava"),

    FOOTSTEP("minecraft:footstep"),

    CLOUD("minecraft:cloud"),

    REDDUST("minecraft:reddust"),

    SNOWBALLPOOF("minecraft:snowballpoof"),

    SNOWSHOVEL("minecraft:snowshovel"),

    SLIME("minecraft:slime"),

    HEART("minecraft:heart"),

    BARRIER("minecraft:barrier"),

    ICONCRACK_("minecraft:iconcrack_"),

    BLOCKCRACK_("minecraft:blockcrack_"),

    BLOCKDUST_("minecraft:blockdust_"),

    DROPLET("minecraft:droplet"),

    TAKE("minecraft:take"),

    MOBAPPEARANCE("minecraft:mobappearance");

    private String namespaceID;

    Particle(String namespaceID) {
        this.namespaceID = namespaceID;
        Registries.particles.put(NamespaceID.from(namespaceID), this);
    }

    public int getId() {
        return ordinal();
    }

    public String getNamespaceID() {
        return namespaceID;
    }

    public static Particle fromId(int id) {
        if (id >= 0 && id < values().length) {
            return values()[id];
        }
        return null;
    }
}
