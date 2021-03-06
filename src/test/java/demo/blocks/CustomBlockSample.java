package demo.blocks;

import net.minestom.server.data.Data;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.CustomBlock;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.time.TimeUnit;
import net.minestom.server.utils.time.UpdateOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class CustomBlockSample extends CustomBlock {

    private static final UpdateOption UPDATE_OPTION = new UpdateOption(3, TimeUnit.TICK);

    public CustomBlockSample() {
        super(Block.GOLD_BLOCK, "custom_block");
    }

    @Override
    public void onPlace(@NotNull Instance instance, @NotNull BlockPosition blockPosition, Data data) {

    }

    @Override
    public void onDestroy(@NotNull Instance instance, @NotNull BlockPosition blockPosition, Data data) {
        BlockPosition above = blockPosition.clone().add(0, 1, 0);
        CustomBlock blockAbove = instance.getCustomBlock(above);
        if (blockAbove == this) {
            instance.setBlock(above, Block.AIR);
            instance.setBlock(blockPosition, Block.AIR); // this should NOT create a stack overflow simply because we are trying to remove this same block
        }

        System.out.println("position "+blockPosition);
        instance.setBlock(blockPosition, Block.DIAMOND_BLOCK);
    }

    @Override
    public void update(@NotNull Instance instance, @NotNull BlockPosition blockPosition, @Nullable Data data) {
        final short blockId = instance.getBlockStateId(blockPosition);
        //instance.refreshBlockStateId(blockPosition, (short) (blockId+1));
    }

    @Nullable
    @Override
    public UpdateOption getUpdateOption() {
        return UPDATE_OPTION;
    }

    @Override
    public void updateFromNeighbor(@NotNull Instance instance, @NotNull BlockPosition thisPosition, @NotNull BlockPosition neighborPosition, boolean directNeighbor) {

    }

    @Override
    public boolean onInteract(@NotNull Player player, @NotNull BlockPosition blockPosition, Data data) {
        return false;
    }

    @Override
    public short getCustomBlockId() {
        return 2;
    }
}