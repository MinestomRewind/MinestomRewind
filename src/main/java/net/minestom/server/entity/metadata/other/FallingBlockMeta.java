package net.minestom.server.entity.metadata.other;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.metadata.EntityMeta;
import net.minestom.server.entity.metadata.ObjectDataProvider;
import net.minestom.server.instance.block.Block;
import org.jetbrains.annotations.NotNull;

public class FallingBlockMeta extends EntityMeta implements ObjectDataProvider {

    private Block block = Block.STONE;

    public FallingBlockMeta(@NotNull Entity entity, @NotNull Metadata metadata) {
        super(entity, metadata);
    }

    @NotNull
    public Block getBlock() {
        return block;
    }

    /**
     * Sets which block to display.
     * This is possible only before spawn packet is sent.
     *
     * @param block which block to display.
     */
    public void setBlock(@NotNull Block block) {
        this.block = block;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public int getObjectData() {
        int id = this.block.getBlockId();
        int metadata = 0; // TODO ?
        return id | (metadata << 0x10);
    }

    @Override
    public boolean requiresVelocityPacketAtSpawn() {
        return false;
    }

}
