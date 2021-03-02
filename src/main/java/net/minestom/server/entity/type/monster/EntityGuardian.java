package net.minestom.server.entity.type.monster;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.type.Monster;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.binary.BitmaskUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @deprecated Use {@link net.minestom.server.entity.metadata.monster.GuardianMeta} instead.
 */
@Deprecated
public class EntityGuardian extends EntityCreature implements Monster {

    private Entity target;

    public EntityGuardian(@NotNull Position spawnPosition) {
        super(EntityType.GUARDIAN, spawnPosition);
        setBoundingBox(0.85f, 0.85f, 0.85f);
    }

    public EntityGuardian(@NotNull Position spawnPosition, @Nullable Instance instance) {
        super(EntityType.GUARDIAN, spawnPosition, instance);
        setBoundingBox(0.85f, 0.85f, 0.85f);
    }

    EntityGuardian(@NotNull EntityType entityType, @NotNull Position spawnPosition) {
        super(entityType, spawnPosition);
    }

    EntityGuardian(@NotNull EntityType entityType, @NotNull Position spawnPosition, @Nullable Instance instance) {
        super(entityType, spawnPosition, instance);
    }

    public boolean isElderly() {
        return (metadata.getIndex((byte) 16, (byte) 0) & 0x02) != 0;
    }

    public void setElderly(boolean elderly) {
        final byte state = BitmaskUtil.changeBit(metadata.getIndex((byte) 16, (byte) 0), (byte) 0x02, (byte) (elderly ? 1 : 0), (byte) 1);
        this.metadata.setIndex((byte) 16, Metadata.Byte(state));
    }

    public boolean isRetractingSpikes() {
        return (metadata.getIndex((byte) 16, (byte) 0) & 0x04) != 0;
    }

    public void setRetractingSpikes(boolean retractingSpikes) {
        final byte state = BitmaskUtil.changeBit(metadata.getIndex((byte) 16, (byte) 0), (byte) 0x04, (byte) (retractingSpikes ? 1 : 0), (byte) 2);
        this.metadata.setIndex((byte) 16, Metadata.Byte(state));
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(@NotNull Entity target) {
        this.target = target;
        this.metadata.setIndex((byte) 17, Metadata.Int(target.getEntityId()));
    }
}
