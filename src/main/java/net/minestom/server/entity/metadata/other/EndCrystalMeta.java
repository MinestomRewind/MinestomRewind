package net.minestom.server.entity.metadata.other;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.metadata.EntityMeta;
import net.minestom.server.utils.BlockPosition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EndCrystalMeta extends EntityMeta {

    public EndCrystalMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public int getHealth() {
        return this.metadata.getIndex((byte) 8, (short) 0);
    }

    public void setHealth(int value) {
        this.metadata.setIndex((byte) 8, Metadata.Int(value));
    }

}
