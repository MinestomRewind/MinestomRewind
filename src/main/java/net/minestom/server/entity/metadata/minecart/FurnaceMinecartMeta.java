package net.minestom.server.entity.metadata.minecart;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class FurnaceMinecartMeta extends AbstractMinecartMeta {

    public FurnaceMinecartMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public boolean isPowered() {
        return super.metadata.getIndex((byte) 16, false);
    }

    public void setPowered(boolean value) {
        super.metadata.setIndex((byte) 16, Metadata.Boolean(value));
    }

    @Override
    public int getObjectData() {
        return 2;
    }

}
