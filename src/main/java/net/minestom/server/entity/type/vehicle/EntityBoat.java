package net.minestom.server.entity.type.vehicle;

import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.ObjectEntity;
import net.minestom.server.entity.type.Vehicle;
import net.minestom.server.utils.Position;

/**
 * @deprecated Use {@link net.minestom.server.entity.metadata.other.BoatMeta} instead.
 */
@Deprecated
public class EntityBoat extends ObjectEntity implements Vehicle {

    public EntityBoat(Position spawnPosition) {
        super(EntityType.BOAT, spawnPosition);
        setBoundingBox(1.375f, 0.5625f, 1.375f);
    }

    @Override
    public int getObjectData() {
        return 0;
    }
}
