package net.minestom.server.entity.metadata.other;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.metadata.MobMeta;
import org.jetbrains.annotations.NotNull;

public class SlimeMeta extends MobMeta {

    public SlimeMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public int getSize() {
        return super.metadata.getIndex((byte) 16, (byte) 0);
    }

    public void setSize(byte value) {
        float boxSize = 0.51000005f * value;
        setBoundingBox(boxSize, boxSize);
        super.metadata.setIndex((byte) 16, Metadata.Byte(value));
    }

}
