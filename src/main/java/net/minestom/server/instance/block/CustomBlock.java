package net.minestom.server.instance.block;

import net.minestom.server.data.Data;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.BlockModifier;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.time.UpdateOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the handler of a custom block type which can be registered with {@link BlockManager#registerCustomBlock(CustomBlock)}.
 * <p>
 * There should be only one instance of this class for each custom block type,
 * every individual blocks will execute the callbacks present there. Each of which contains the
 * custom block position and the instance concerned.
 * <p>
 * Each block in a chunk contains 2 id, the block state id (only visual) and a custom block id corresponding to
 * {@link CustomBlock#getCustomBlockId()}. A custom block is responsible for the blocks tick and some useful callbacks.
 */
public abstract class CustomBlock {

    private final short defaultBlockStateId;
    private final String identifier;

    /**
     * @param defaultBlockStateId the default block state id
     * @param identifier          the custom block identifier
     */
    public CustomBlock(short defaultBlockStateId, @NotNull String identifier) {
        this.defaultBlockStateId = defaultBlockStateId;
        this.identifier = identifier;
    }

    public CustomBlock(@NotNull Block block, @NotNull String identifier) {
        this(block, identifier, (byte) 0);
    }

    public CustomBlock(@NotNull Block block, @NotNull String identifier, byte metadata) {
        this(block.toStateId(metadata), identifier);
    }

    /**
     * Calling delay depends on {@link #getUpdateOption()} which should be overridden.
     *
     * @param instance      the instance of the block
     * @param blockPosition the position of the block
     * @param data          the data associated with the block
     * @throws UnsupportedOperationException if {@link #getUpdateOption()}
     *                                       is not null but the update method is not overridden
     */
    public void update(@NotNull Instance instance, @NotNull BlockPosition blockPosition, @Nullable Data data) {
        throw new UnsupportedOperationException("Update method not overridden, check #getUpdateOption()");
    }

    /**
     * The update option is used to define the delay between two
     * {@link #update(Instance, BlockPosition, Data)} execution.
     * <p>
     * If this is not null, {@link #update(Instance, BlockPosition, Data)}
     * should be overridden or errors with occurs.
     *
     * @return the update option of the block, null if not any
     */
    @Nullable
    public UpdateOption getUpdateOption() {
        return null;
    }

    /**
     * Called when a custom block has been placed.
     *
     * @param instance      the instance of the block
     * @param blockPosition the position of the block
     * @param data          the data associated with the block
     */
    public abstract void onPlace(@NotNull Instance instance, @NotNull BlockPosition blockPosition, @Nullable Data data);

    /**
     * Called when a custom block has been destroyed or replaced.
     *
     * @param instance      the instance of the block
     * @param blockPosition the position of the block
     * @param data          the data associated with the block
     */
    public abstract void onDestroy(@NotNull Instance instance, @NotNull BlockPosition blockPosition, @Nullable Data data);

    /**
     * Handles interactions with this block. Can also block normal item use (containers should block when opening the
     * menu, this prevents the player from placing a block when opening it for instance).
     *
     * @param player        the player interacting
     * @param blockPosition the position of this block
     * @param data          the data at this position
     * @return true if this block blocks normal item use, false otherwise
     */
    public abstract boolean onInteract(@NotNull Player player, @NotNull BlockPosition blockPosition, @Nullable Data data);

    /**
     * This id can be serialized in chunk file, meaning no duplicate should exist
     * Changing this value halfway should mean potentially breaking the world
     *
     * @return the custom block id
     */
    public abstract short getCustomBlockId();

    /**
     * Gets if this {@link CustomBlock} requires any tick update.
     *
     * @return true if {@link #getUpdateOption()} is not null and the value is positive
     */
    public boolean hasUpdate() {
        final UpdateOption updateOption = getUpdateOption();
        if (updateOption == null)
            return false;

        return updateOption.getValue() > 0;
    }


    /**
     * Defines custom behaviour for entities touching this block.
     *
     * @param instance the instance
     * @param position the position at which the block is
     * @param touching the entity currently touching the block
     */
    public void handleContact(@NotNull Instance instance, @NotNull BlockPosition position, @NotNull Entity touching) {
    }

    /**
     * This is the default block state id when the custom block is set,
     * it is possible to change this value per block using
     * {@link BlockModifier#setSeparateBlocks(int, int, int, short, short)}
     * <p>
     * Meaning that you should not believe that your custom blocks id will always be this one.
     *
     * @return the default visual block id
     */
    public short getDefaultBlockStateId() {
        return defaultBlockStateId;
    }

    /**
     * The custom block identifier, used to retrieve the custom block object with
     * {@link BlockManager#getCustomBlock(String)} and to set custom block in the instance.
     *
     * @return the custom block identifier
     */
    @NotNull
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Initialises data for this block.
     *
     * @param blockPosition the position of the targeted block
     * @param data          data given to 'setBlock', can be null
     * @return Data for this block. Can be null, 'data', or a new object
     */
    @Nullable
    public Data createData(@NotNull Instance instance, @NotNull BlockPosition blockPosition, @Nullable Data data) {
        return data;
    }

    /**
     * Updates this block from a neighbor. By default calls 'update' if directNeighbor is true.
     *
     * @param instance         current instance
     * @param thisPosition     this block's position
     * @param neighborPosition the neighboring block which triggered the update
     * @param directNeighbor   is the neighbor directly connected to this block? (No diagonals)
     */
    public void updateFromNeighbor(@NotNull Instance instance, @NotNull BlockPosition thisPosition,
                                   @NotNull BlockPosition neighborPosition, boolean directNeighbor) {
        if (directNeighbor && hasUpdate()) {
            update(instance, thisPosition, instance.getBlockData(thisPosition));
        }
    }

    /**
     * Called when a scheduled update on this block happens. By default, calls 'update'.
     *
     * @param instance  the instance of the block
     * @param position  the position of the block
     * @param blockData the data of the block
     */
    public void scheduledUpdate(@NotNull Instance instance, @NotNull BlockPosition position, @Nullable Data blockData) {
        update(instance, position, blockData);
    }

    /**
     * Gets the drag of this block.
     * <p>
     * It has to be between 0 and 1.
     *
     * @return the drag of this block
     */
    public float getDrag(@NotNull Instance instance, @NotNull BlockPosition blockPosition) {
        return 0.5f;
    }

    /**
     * Allows custom block to write block entity data to a given NBT compound.
     * Used to send block entity data to the client over the network.
     * Can also be used to save block entity data on disk for compatible chunk savers.
     *
     * @param position  position of the block
     * @param blockData equivalent to <pre>instance.getBlockData(position)</pre>
     * @param nbt       the nbt to write in the {@link net.minestom.server.network.packet.server.play.ChunkDataPacket}
     */
    public void writeBlockEntity(@NotNull BlockPosition position, @Nullable Data blockData, @NotNull NBTCompound nbt) {
    }

    /**
     * Called when an explosion wants to destroy this block.
     *
     * @param instance           the instance
     * @param lootTableArguments arguments used in the loot table loot generation
     * @return 'true' if the explosion should happen on this block, 'false' to cancel the destruction.
     * Returning true does NOT block the explosion rays, ie it does not change the block explosion resistance
     */
    public boolean onExplode(@NotNull Instance instance, @NotNull BlockPosition position, Data lootTableArguments) {
        return true;
    }
}
