package net.minestom.server.entity.metadata;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class AgeableMobMeta extends PathfinderMobMeta {

    protected AgeableMobMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public byte getAge() {
        return super.metadata.getIndex((byte) 12, (byte) 0);
    }

    public void setAge(byte age) {
        super.metadata.setIndex((byte) 12, Metadata.Byte(age));
    }

}
