package net.minestom.server.entity.metadata.monster.zombie;

import net.minestom.server.collision.BoundingBox;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.metadata.monster.MonsterMeta;
import org.jetbrains.annotations.NotNull;

public class ZombieMeta extends MonsterMeta {

    public ZombieMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public boolean isBaby() {
        return super.metadata.getIndex((byte) 12, false);
    }

    public void setBaby(boolean value) {
        if (isBaby() == value) {
            return;
        }
        BoundingBox bb = this.entity.getBoundingBox();
        if (value) {
            setBoundingBox(bb.getWidth() / 2, bb.getHeight() / 2);
        } else {
            setBoundingBox(bb.getWidth() * 2, bb.getHeight() * 2);
        }
        super.metadata.setIndex((byte) 12, Metadata.Boolean(value));
    }

    public boolean isVillager() {
        return super.metadata.getIndex((byte) 13, false);
    }

    public void setVillager(boolean value) {
        super.metadata.setIndex((byte) 13, Metadata.Boolean(value));
    }

    public boolean isConverting() {
        return super.metadata.getIndex((byte) 14, false);
    }

    public void setConverting(boolean value) {
        super.metadata.setIndex((byte) 14, Metadata.Boolean(value));
    }

}
