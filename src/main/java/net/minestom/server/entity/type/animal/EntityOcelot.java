package net.minestom.server.entity.type.animal;

import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.metadata.animal.tameable.OcelotMeta;
import net.minestom.server.entity.type.AgeableCreature;
import net.minestom.server.entity.type.Animal;
import net.minestom.server.utils.Position;

/**
 * @deprecated Use {@link OcelotMeta} instead.
 */
@Deprecated
public class EntityOcelot extends AgeableCreature implements Animal {
    public EntityOcelot(Position spawnPosition) {
        super(EntityType.OZELOT, spawnPosition);
        setBoundingBox(0.6f, 0.7f, 0.6f);
    }

    public byte getType() {
        return metadata.getIndex((byte) 18, (byte) 0);
    }

    public void setType(byte type) {
        this.metadata.setIndex((byte) 18, Metadata.Byte(type));
    }
}
