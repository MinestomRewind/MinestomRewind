package net.minestom.server.entity.type.projectile;

import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.ObjectEntity;
import net.minestom.server.entity.type.Projectile;
import net.minestom.server.utils.Position;

public class EntityEyeOfEnder extends ObjectEntity implements Projectile {

    public EntityEyeOfEnder(Position spawnPosition) {
        super(EntityType.EYE_OF_ENDER_SIGNAL, spawnPosition);
    }

    @Override
    public int getObjectData() {
        return 0;
    }
}
