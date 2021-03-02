package net.minestom.server.entity.type.other;

import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.type.Constructable;
import net.minestom.server.utils.Position;

/**
 * @deprecated Use {@link net.minestom.server.entity.metadata.golem.IronGolemMeta} instead.
 */
@Deprecated
public class EntityIronGolem extends EntityCreature implements Constructable {

    public EntityIronGolem(Position spawnPosition) {
        super(EntityType.VILLAGER_GOLEM, spawnPosition);
        setBoundingBox(1.4f, 2.7f, 1.4f);
    }

    public boolean isPlayerCreated() {
        return metadata.getIndex((byte) 16, (byte) 0) == 1;
    }

    public void setPlayerCreated(boolean playerCreated) {
        this.metadata.setIndex((byte) 16, Metadata.Byte((byte) (playerCreated ? 1 : 0)));
    }
}
