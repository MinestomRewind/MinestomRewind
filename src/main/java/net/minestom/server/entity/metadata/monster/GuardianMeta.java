package net.minestom.server.entity.metadata.monster;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Metadata;
import org.jetbrains.annotations.NotNull;

public class GuardianMeta extends MonsterMeta {

    private final static byte MASK_INDEX = 16;

    private final static byte IS_ELDERLY = 0x01;
    private final static byte IS_RETRACTING_SPIKES = 0x02;

    private Entity target;

    public GuardianMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    public boolean isElderly() {
        return getMaskBit(MASK_INDEX, IS_ELDERLY);
    }

    public void setElderly(boolean value) {
        setMaskBit(MASK_INDEX, IS_ELDERLY, value);
    }

    public boolean isRetractingSpikes() {
        return getMaskBit(MASK_INDEX, IS_RETRACTING_SPIKES);
    }

    public void setRetractingSpikes(boolean value) {
        setMaskBit(MASK_INDEX, IS_RETRACTING_SPIKES, value);
    }

    public Entity getTarget() {
        return this.target;
    }

    public void setTarget(@NotNull Entity target) {
        this.target = target;
        super.metadata.setIndex((byte) 17, Metadata.Int(target.getEntityId()));
    }

}
