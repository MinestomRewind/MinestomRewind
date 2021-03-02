package net.minestom.server.entity.metadata.animal.tameable;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.metadata.animal.AnimalMeta;
import org.jetbrains.annotations.NotNull;

public class OcelotMeta extends TameableAnimalMeta {

    public OcelotMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    @NotNull
    public Type getType() {
        return Type.VALUES[super.metadata.getIndex((byte) 10, (byte) 0)];
    }

    public void setType(@NotNull Type value) {
        super.metadata.setIndex((byte) 10, Metadata.Byte((byte) value.ordinal()));
    }

    public enum Type {
        NORMAL,
        BLACK,
        RED,
        SIAMESE;

        private final static Type[] VALUES = values();
    }
}
