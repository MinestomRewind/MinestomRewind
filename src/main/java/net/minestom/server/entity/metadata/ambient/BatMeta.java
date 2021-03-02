package net.minestom.server.entity.metadata.ambient;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class BatMeta extends AmbientCreatureMeta {


    public BatMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public boolean isHanging() {
        return metadata.getIndex((byte) 16, false);
    }

    public void setHanging(boolean value) {
        metadata.setIndex((byte) 16, Metadata.Boolean(value));
    }

}
