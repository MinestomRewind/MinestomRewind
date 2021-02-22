package net.minestom.server.entity.type.monster;

import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.type.Monster;
import net.minestom.server.utils.Position;

public class EntityGhast extends EntityCreature implements Monster {

    public EntityGhast(Position spawnPosition) {
        super(EntityType.GHAST, spawnPosition);
        setBoundingBox(4, 4, 4);
    }

    public boolean isAttacking() {
        return metadata.getIndex((byte) 16, (byte) 0) == 1;
    }

    public void setAttacking(boolean attacking) {
        this.metadata.setIndex((byte) 16, Metadata.Byte((byte) (attacking ? 1 : 0)));
    }
}
