package net.minestom.server.entity.type.other;

import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.ObjectEntity;
import net.minestom.server.utils.Position;
import org.jetbrains.annotations.NotNull;

/**
 * @deprecated Use {@link net.minestom.server.entity.metadata.other.EndCrystalMeta} instead.
 */
@Deprecated
public class EntityEndCrystal extends ObjectEntity {

    public EntityEndCrystal(@NotNull Position spawnPosition) {
        super(EntityType.ENDER_CRYSTAL, spawnPosition);

        setBoundingBox(2f, 2f, 2f);
    }

    @Override
    public int getObjectData() {
        return 0;
    }
}
