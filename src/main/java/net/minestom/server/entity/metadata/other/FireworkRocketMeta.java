package net.minestom.server.entity.metadata.other;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.metadata.EntityMeta;
import net.minestom.server.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FireworkRocketMeta extends EntityMeta {

    private Entity shooter;

    public FireworkRocketMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    @NotNull
    public ItemStack getFireworkInfo() {
        return super.metadata.getIndex((byte) 8, ItemStack.getAirItem());
    }

    public void setFireworkInfo(@NotNull ItemStack value) {
        super.metadata.setIndex((byte) 8, Metadata.Slot(value));
    }

}
