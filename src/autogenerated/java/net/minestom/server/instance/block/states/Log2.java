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
public final class Log2 {
    /**
     * Completely internal. DO NOT USE. IF YOU ARE A USER AND FACE A PROBLEM WHILE USING THIS CODE, THAT'S ON YOU.
     */
    @Deprecated(
            since = "forever",
            forRemoval = false
    )
    public static void initStates() {
        Block.LOG2.addBlockAlternative(new BlockAlternative((short) 0));
        Block.LOG2.addBlockAlternative(new BlockAlternative((short) 1));
        Block.LOG2.addBlockAlternative(new BlockAlternative((short) 4));
        Block.LOG2.addBlockAlternative(new BlockAlternative((short) 5));
        Block.LOG2.addBlockAlternative(new BlockAlternative((short) 8));
        Block.LOG2.addBlockAlternative(new BlockAlternative((short) 9));
        Block.LOG2.addBlockAlternative(new BlockAlternative((short) 12));
        Block.LOG2.addBlockAlternative(new BlockAlternative((short) 13));
    }
}
