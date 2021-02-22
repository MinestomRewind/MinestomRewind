package net.minestom.server.instance.block.states;

import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockAlternative;

/**
 * Completely internal. DO NOT USE. IF YOU ARE A USER AND FACE A PROBLEM WHILE USING THIS CODE, THAT'S ON YOU.
 */
@Deprecated(
        since = "forever",
        forRemoval = false
)
public final class Stonebrick {
    /**
     * Completely internal. DO NOT USE. IF YOU ARE A USER AND FACE A PROBLEM WHILE USING THIS CODE, THAT'S ON YOU.
     */
    @Deprecated(
            since = "forever",
            forRemoval = false
    )
    public static void initStates() {
        Block.STONEBRICK.addBlockAlternative(new BlockAlternative((short) 0));
        Block.STONEBRICK.addBlockAlternative(new BlockAlternative((short) 1));
        Block.STONEBRICK.addBlockAlternative(new BlockAlternative((short) 2));
        Block.STONEBRICK.addBlockAlternative(new BlockAlternative((short) 3));
    }
}
