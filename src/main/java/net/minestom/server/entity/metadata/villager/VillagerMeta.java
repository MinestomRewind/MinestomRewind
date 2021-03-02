package net.minestom.server.entity.metadata.villager;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.metadata.AgeableMobMeta;
import org.jetbrains.annotations.NotNull;

public class VillagerMeta extends AgeableMobMeta {

    public VillagerMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    @NotNull
    public Profession getProfession() {
        return Profession.VALUES[super.metadata.getIndex((byte) 16, (byte) 0)];
    }

    public void setProfession(@NotNull Profession value) {
        super.metadata.setIndex((byte) 16, Metadata.Byte((byte) value.ordinal()));
    }

    public enum Profession {
        FARMER,
        LIBRARIAN,
        PRIEST,
        BLACKSMITH,
        BUTCHER;

        public final static Profession[] VALUES = values();
    }

}
