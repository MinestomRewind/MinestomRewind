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
public final class Stone {
    /**
     * Completely internal. DO NOT USE. IF YOU ARE A USER AND FACE A PROBLEM WHILE USING THIS CODE, THAT'S ON YOU.
     */
    @Deprecated(
            since = "forever",
            forRemoval = false
    )
    public static void initStates() {
        Block.STONE.addBlockAlternative(new BlockAlternative((short) 0));
        Block.STONE.addBlockAlternative(new BlockAlternative((short) 1));
        Block.STONE.addBlockAlternative(new BlockAlternative((short) 2));
        Block.STONE.addBlockAlternative(new BlockAlternative((short) 3));
        Block.STONE.addBlockAlternative(new BlockAlternative((short) 4));
        Block.STONE.addBlockAlternative(new BlockAlternative((short) 5));
        Block.STONE.addBlockAlternative(new BlockAlternative((short) 6));
    }
}
