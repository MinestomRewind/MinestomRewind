package net.minestom.server.entity.metadata.monster;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class WitchMeta extends MonsterMeta {

    public WitchMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public boolean isAggressive() {
        return super.metadata.getIndex((byte) 21, false);
    }

    public void setAggressive(boolean value) {
        super.metadata.setIndex((byte) 21, Metadata.Boolean(value));
    }

}
