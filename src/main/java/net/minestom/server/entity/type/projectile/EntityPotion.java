package net.minestom.server.entity.type.projectile;

import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.ObjectEntity;
import net.minestom.server.entity.type.Projectile;
import net.minestom.server.item.ItemStack;
import net.minestom.server.utils.Position;
import org.jetbrains.annotations.NotNull;

public class EntityPotion extends ObjectEntity implements Projectile {

    public EntityPotion(Position spawnPosition, @NotNull ItemStack potion) {
        super(EntityType.THROWN_POTION, spawnPosition);
        setBoundingBox(0.25f, 0.25f, 0.25f);
        // TODO(koesie10): Use potion
    }

    @Override
    public int getObjectData() {
        return 0;
    }
}
