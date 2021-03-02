package net.minestom.server.entity.metadata.item;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.metadata.EntityMeta;
import net.minestom.server.entity.metadata.ObjectDataProvider;
import net.minestom.server.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemEntityMeta extends EntityMeta implements ObjectDataProvider {

    public ItemEntityMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    @NotNull
    public ItemStack getItem() {
        return super.metadata.getIndex((byte) 10, ItemStack.getAirItem());
    }

    public void setItem(@NotNull ItemStack item) {
        super.metadata.setIndex((byte) 10, Metadata.Slot(item));
    }

    @Override
    public int getObjectData() {
        return 1;
    }

    @Override
    public boolean requiresVelocityPacketAtSpawn() {
        return true;
    }

}