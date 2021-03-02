package net.minestom.server.entity.metadata.other;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.metadata.EntityMeta;
import net.minestom.server.entity.metadata.ObjectDataProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FishingHookMeta extends EntityMeta implements ObjectDataProvider {

    private Entity thrower;

    public FishingHookMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    @Nullable
    public Entity getThrower() {
        return this.thrower;
    }

    public void setThrower(@Nullable Entity value) {
        this.thrower = value;
    }

    @Override
    public int getObjectData() {
        return this.thrower == null ? 0 : this.thrower.getEntityId() + 1;
    }

    @Override
    public boolean requiresVelocityPacketAtSpawn() {
        return true;
    }
}
