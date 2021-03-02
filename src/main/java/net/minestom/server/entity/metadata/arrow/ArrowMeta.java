package net.minestom.server.entity.metadata.arrow;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.metadata.EntityMeta;
import net.minestom.server.entity.metadata.ObjectDataProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArrowMeta extends EntityMeta implements ObjectDataProvider {

    private Entity shooter;

    public ArrowMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    @Nullable
    public Entity getShooter() {
        return shooter;
    }

    public void setShooter(@Nullable Entity shooter) {
        this.shooter = shooter;
    }

    public boolean isCritical() {
        return super.metadata.getIndex((byte) 16, false);
    }

    public void setCritical(boolean value) {
        super.metadata.setIndex((byte) 16, Metadata.Boolean(value));
    }

    @Override
    public int getObjectData() {
        return this.shooter == null ? 0 : this.shooter.getEntityId() + 1;
    }

    @Override
    public boolean requiresVelocityPacketAtSpawn() {
        return true;
    }

}
