package net.minestom.server.entity.type.animal;

import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.type.Animal;
import net.minestom.server.utils.Position;

public class EntityMooshroom extends EntityCreature implements Animal {

    public EntityMooshroom(Position spawnPosition) {
        super(EntityType.MUSHROOM_COW, spawnPosition);
        setBoundingBox(0.9f, 1.4f, 0.9f);
    }
}
