package net.minestom.server.entity.type.monster;

import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.type.Monster;
import net.minestom.server.utils.Position;

/**
 * @deprecated Use {@link net.minestom.server.entity.metadata.monster.raider.WitchMeta} instead.
 */
@Deprecated
public class EntityWitch extends EntityCreature implements Monster {

    public EntityWitch(Position spawnPosition) {
        super(EntityType.WITCH, spawnPosition);
        setBoundingBox(0.6f, 1.95f, 0.6f);
    }

    public boolean isAggressive() {
        return metadata.getIndex((byte) 21, (byte) 0) == 1;
    }

    public void setAggressive(boolean aggressive) {
        this.metadata.setIndex((byte) 21, Metadata.Byte((byte) (aggressive ? 1 : 0)));
    }
}
