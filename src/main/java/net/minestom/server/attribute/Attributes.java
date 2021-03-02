package net.minestom.server.attribute;

/**
 * The Minecraft, vanilla, standards attributes.
 */
public final class Attributes {

    public static final Attribute MAX_HEALTH = (new Attribute("generic.maxHealth", true, 20, Float.MAX_VALUE)).register();
    public static final Attribute FOLLOW_RANGE = (new Attribute("generic.followRange", true, 32, 2048)).register();
    public static final Attribute KNOCKBACK_RESISTANCE = (new Attribute("generic.knockbackResistance", true, 0, 1)).register();
    public static final Attribute MOVEMENT_SPEED = (new Attribute("generic.movementSpeed", true, 0.7f, Float.MAX_VALUE)).register();
    public static final Attribute ATTACK_DAMAGE = (new Attribute("generic.attackDamage", true, 2, Float.MAX_VALUE)).register();
    public static final Attribute HORSE_JUMP_STRENGTH = (new Attribute("horse.jumpStrength", true, 0.7f, 2)).register();
    public static final Attribute ZOMBIE_SPAWN_REINFORCEMENTS = (new Attribute("zombie.spawnReinforcements", true, 0, 1)).register();

    private Attributes() throws IllegalAccessException {
        throw new IllegalAccessException("Cannot instantiate a static class");
    }

    protected static void init() {
        // Empty, here to register all the vanilla attributes
    }
}
