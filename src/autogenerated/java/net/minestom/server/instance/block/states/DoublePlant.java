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
public final class DoublePlant {
    /**
     * Completely internal. DO NOT USE. IF YOU ARE A USER AND FACE A PROBLEM WHILE USING THIS CODE, THAT'S ON YOU.
     */
    @Deprecated(
            since = "forever",
            forRemoval = false
    )
    public static void initStates() {
        Block.DOUBLE_PLANT.addBlockAlternative(new BlockAlternative((short) 0));
        Block.DOUBLE_PLANT.addBlockAlternative(new BlockAlternative((short) 1));
        Block.DOUBLE_PLANT.addBlockAlternative(new BlockAlternative((short) 2));
        Block.DOUBLE_PLANT.addBlockAlternative(new BlockAlternative((short) 3));
        Block.DOUBLE_PLANT.addBlockAlternative(new BlockAlternative((short) 4));
        Block.DOUBLE_PLANT.addBlockAlternative(new BlockAlternative((short) 5));
        Block.DOUBLE_PLANT.addBlockAlternative(new BlockAlternative((short) 8));
    }
}
