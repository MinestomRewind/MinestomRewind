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
public final class Planks {
    /**
     * Completely internal. DO NOT USE. IF YOU ARE A USER AND FACE A PROBLEM WHILE USING THIS CODE, THAT'S ON YOU.
     */
    @Deprecated(
            since = "forever",
            forRemoval = false
    )
    public static void initStates() {
        Block.PLANKS.addBlockAlternative(new BlockAlternative((short) 0));
        Block.PLANKS.addBlockAlternative(new BlockAlternative((short) 1));
        Block.PLANKS.addBlockAlternative(new BlockAlternative((short) 2));
        Block.PLANKS.addBlockAlternative(new BlockAlternative((short) 3));
        Block.PLANKS.addBlockAlternative(new BlockAlternative((short) 4));
        Block.PLANKS.addBlockAlternative(new BlockAlternative((short) 5));
    }
}
