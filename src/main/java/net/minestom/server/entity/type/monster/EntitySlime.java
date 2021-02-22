package net.minestom.server.entity.type.monster;

import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.type.Monster;
import net.minestom.server.utils.Position;

public class EntitySlime extends EntityCreature implements Monster {

    public EntitySlime(Position spawnPosition) {
        super(EntityType.SLIME, spawnPosition);
        setSize((byte) 1);
    }

    public byte getSize() {
        return metadata.getIndex((byte) 16, (byte) 1);
    }

    public void setSize(byte size) {
        final float boxSize = 0.51000005f * size;
        setBoundingBox(boxSize, boxSize, boxSize);
        this.metadata.setIndex((byte) 16, Metadata.Byte(size));
    }
}
