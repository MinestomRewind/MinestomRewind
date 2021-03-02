package net.minestom.server.entity.type.monster;

import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.type.Monster;
import net.minestom.server.utils.Position;
import org.jetbrains.annotations.NotNull;

/**
 * @deprecated Use {@link net.minestom.server.entity.metadata.monster.zombie.ZombieMeta} instead.
 */
@Deprecated
public class EntityZombie extends EntityCreature implements Monster {

    public EntityZombie(@NotNull Position spawnPosition) {
        this(EntityType.ZOMBIE, spawnPosition);
    }

    EntityZombie(@NotNull EntityType entityType, @NotNull Position spawnPosition) {
        super(entityType, spawnPosition);
        setBoundingBox(0.6f, 1.95f, 0.6f);
    }

    public boolean isBaby() {
        return metadata.getIndex((byte) 12, (byte) 0) == 1;
    }

    public void setBaby(boolean baby) {
        this.metadata.setIndex((byte) 12, Metadata.Byte((byte) (baby ? 1 : 0)));
    }

    public boolean isVillager() {
        return metadata.getIndex((byte) 13, (byte) 0) == 1;
    }

    public void setVillager(boolean villager) {
        this.metadata.setIndex((byte) 13, Metadata.Byte((byte) (villager ? 1 : 0)));
    }

    public boolean isConverting() {
        return metadata.getIndex((byte) 14, (byte) 0) == 1;
    }

    public void setConverting(boolean converting) {
        this.metadata.setIndex((byte) 14, Metadata.Byte((byte) (converting ? 1 : 0)));
    }

    @Override
    public double getEyeHeight() {
        return isBaby() ? 0.93 : 1.74;
    }
}
