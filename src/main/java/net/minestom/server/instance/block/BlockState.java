package net.minestom.server.instance.block;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockState {
    private final Block block;
    private final byte metadata;
    private final BlockVariation variation;

    public BlockState(@NotNull Block block, byte metadata, @Nullable BlockVariation variation) {
        this.block = block;
        this.metadata = metadata;
        this.variation = variation;
    }

    @NotNull
    public Block getBlock() {
        return block;
    }

    public byte getMetadata() {
        return metadata;
    }

    @Nullable
    public BlockVariation getVariation() {
        return variation;
    }

    @NotNull
    public static BlockState fromStateId(short blockStateId) {
        Block block = Block.fromStateId(blockStateId);
        byte metadata = Block.toMetadata(blockStateId);
        BlockVariation variation = block.getVariation(metadata);
        return new BlockState(block, metadata, variation);
    }
}
