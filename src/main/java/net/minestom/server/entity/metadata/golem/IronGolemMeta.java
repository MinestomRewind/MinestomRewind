package net.minestom.server.entity.metadata.golem;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class IronGolemMeta extends AbstractGolemMeta {

    public IronGolemMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public boolean isPlayerCreated() {
        return super.metadata.getIndex((byte) 16, false);
    }

    public void setPlayerCreated(boolean value) {
        super.metadata.setIndex((byte) 16, Metadata.Boolean(value));
    }

}
