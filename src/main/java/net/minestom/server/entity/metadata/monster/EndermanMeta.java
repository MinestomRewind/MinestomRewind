package net.minestom.server.entity.metadata.monster;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class EndermanMeta extends MonsterMeta {

    public EndermanMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public short getCarriedBlockID() {
        return super.metadata.getIndex((byte) 15, (short) 0);
    }

    public void setCarriedBlockID(short value) {
        super.metadata.setIndex((byte) 15, Metadata.Short(value));
    }

    public byte getCarriedBlockData() {
        return super.metadata.getIndex((byte) 16, (byte) 0);
    }

    public void setCarriedBlockData(byte value) {
        super.metadata.setIndex((byte) 16, Metadata.Byte(value));
    }

    public boolean isScreaming() {
        return super.metadata.getIndex((byte) 18, false);
    }

    public void setScreaming(boolean value) {
        super.metadata.setIndex((byte) 18, Metadata.Boolean(value));
    }

}
