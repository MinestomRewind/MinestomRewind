package net.minestom.server.entity.type.monster;

import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.type.Monster;
import net.minestom.server.utils.Position;
import org.jetbrains.annotations.NotNull;

/**
 * @deprecated Use {@link net.minestom.server.entity.metadata.monster.CreeperMeta} instead.
 */
@Deprecated
public class EntityCreeper extends EntityCreature implements Monster {

    public EntityCreeper(Position spawnPosition) {
        super(EntityType.CREEPER, spawnPosition);
        setBoundingBox(0.6f, 1.7f, 0.6f);
    }

    @NotNull
    public CreeperState getCreeperState() {
        final byte state = metadata.getIndex((byte) 16, (byte) -1);
        return CreeperState.fromState(state);
    }

    public void setCreeperState(@NotNull CreeperState creeperState) {
        this.metadata.setIndex((byte) 16, Metadata.Byte(creeperState.getState()));
    }

    public boolean isPowered() {
        return metadata.getIndex((byte) 17, (byte) 0) == 1;
    }

    public void setPowered(boolean powered) {
        this.metadata.setIndex((byte) 17, Metadata.Byte((byte) (powered ? 1 : 0)));
    }

    public enum CreeperState {
        IDLE((byte) -1),
        FUSE((byte) 1);

        private final byte state;

        CreeperState(byte state) {
            this.state = state;
        }

        private byte getState() {
            return state;
        }

        private static CreeperState fromState(int state) {
            if (state == -1) {
                return IDLE;
            } else if (state == 1) {
                return FUSE;
            }
            throw new IllegalArgumentException("Weird thing happened");
        }
    }
}
