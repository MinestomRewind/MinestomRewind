package net.minestom.server.entity.type.other;

import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.type.Constructable;
import net.minestom.server.utils.Position;

public class EntitySnowman extends EntityCreature implements Constructable {

    public EntitySnowman(Position spawnPosition) {
        super(EntityType.SNOW_MAN, spawnPosition);
        setBoundingBox(0.7f, 1.9f, 0.7f);
    }
}
