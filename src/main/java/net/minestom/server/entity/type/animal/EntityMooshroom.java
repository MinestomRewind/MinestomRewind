package net.minestom.server.entity.type.animal;

import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.type.AgeableCreature;
import net.minestom.server.entity.type.Animal;
import net.minestom.server.utils.Position;

/**
 * @deprecated Use {@link net.minestom.server.entity.metadata.animal.MooshroomMeta} instead.
 */
@Deprecated
public class EntityMooshroom extends AgeableCreature implements Animal {

    public EntityMooshroom(Position spawnPosition) {
        super(EntityType.MUSHROOM_COW, spawnPosition);
        setBoundingBox(0.9f, 1.4f, 0.9f);
    }
}
