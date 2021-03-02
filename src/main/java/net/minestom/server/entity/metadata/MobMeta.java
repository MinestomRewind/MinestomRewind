package net.minestom.server.entity.metadata;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class MobMeta extends LivingEntityMeta {

    protected MobMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public boolean isNoAi() {
        return super.metadata.getIndex((byte) 15, false);
    }

    public void setNoAi(boolean value) {
        super.metadata.setIndex((byte) 15, Metadata.Boolean(value));
    }

}
