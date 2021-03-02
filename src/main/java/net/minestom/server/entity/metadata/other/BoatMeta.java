package net.minestom.server.entity.metadata.other;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.metadata.EntityMeta;
import org.jetbrains.annotations.NotNull;

public class BoatMeta extends EntityMeta {

    public BoatMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public int getTimeSinceLastHit() {
        return super.metadata.getIndex((byte) 17, 0);
    }

    public void setTimeSinceLastHit(int value) {
        super.metadata.setIndex((byte) 17, Metadata.Int(value));
    }

    public int getForwardDirection() {
        return super.metadata.getIndex((byte) 18, 1);
    }

    public void setForwardDirection(int value) {
        super.metadata.setIndex((byte) 18, Metadata.Int(value));
    }

    public float getDamageTaken() {
        return super.metadata.getIndex((byte) 19, 0);
    }

    public void setDamageTaken(float value) {
        super.metadata.setIndex((byte) 19, Metadata.Float(value));
    }

}
