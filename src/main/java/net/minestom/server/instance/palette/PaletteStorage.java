package net.minestom.server.instance.palette;

import it.unimi.dsi.fastutil.shorts.Short2ShortLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ShortOpenHashMap;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.Chunk;
import net.minestom.server.utils.MathUtils;
import net.minestom.server.utils.chunk.ChunkUtils;
import net.minestom.server.utils.clone.PublicCloneable;
import net.minestom.server.utils.validate.Check;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.minestom.server.instance.Chunk.CHUNK_SECTION_COUNT;
import static net.minestom.server.instance.Chunk.CHUNK_SECTION_SIZE;

/**
 * Used to efficiently store blocks with an optional palette.
 * <p>
 * The format used is the one described in the {@link net.minestom.server.network.packet.server.play.ChunkDataPacket},
 * the reason is that it allows us to write the packet much faster.
 */
public class PaletteStorage implements PublicCloneable<PaletteStorage> {

    /**
     * The number of blocks that should be in one chunk section.
     */
    private final static int BLOCK_COUNT = CHUNK_SECTION_SIZE * CHUNK_SECTION_SIZE * CHUNK_SECTION_SIZE;

    private short[][] sectionBlocks;

    /**
     * Creates a new palette storage.
     */
    public PaletteStorage() {
        init();
    }

    private void init() {
        this.sectionBlocks = new short[CHUNK_SECTION_COUNT][0];
    }

    public void setBlockAt(int x, int y, int z, short blockId) {
        PaletteStorage.setBlockAt(this, x, y, z, blockId);
    }

    public short getBlockAt(int x, int y, int z) {
        return PaletteStorage.getBlockAt(this, x, y, z);
    }

    /**
     * Gets the sections of this object,
     * the first array representing the chunk section and the second the block position from {@link #getSectionIndex(int, int, int)}.
     *
     * @return the section blocks
     */
    public short[][] getSectionBlocks() {
        return sectionBlocks;
    }

    /**
     * Loops through all the sections and blocks to find unused array (empty chunk section)
     * <p>
     * Useful after clearing one or multiple sections of a chunk. Can be unnecessarily expensive if the chunk
     * is composed of almost-empty sections since the loop will not stop until a non-air block is discovered.
     */
    public synchronized void clean() {
        for (int i = 0; i < sectionBlocks.length; i++) {
            short[] section = sectionBlocks[i];

            if (section.length != 0) {
                boolean canClear = true;
                for (long blockGroup : section) {
                    if (blockGroup != 0) {
                        canClear = false;
                        break;
                    }
                }
                if (canClear) {
                    sectionBlocks[i] = new short[0];
                }

            }

        }
    }

    /**
     * Clears all the data in the palette and data array.
     */
    public void clear() {
        init();
    }

    /**
     * @deprecated use {@link #clone()}
     */
    @Deprecated
    @NotNull
    public PaletteStorage copy() {
        return clone();
    }

    @NotNull
    @Override
    public PaletteStorage clone() {
        try {
            PaletteStorage paletteStorage = (PaletteStorage) super.clone();
            paletteStorage.sectionBlocks = sectionBlocks.clone();

            return paletteStorage;
        } catch (CloneNotSupportedException e) {
            MinecraftServer.getExceptionManager().handleException(e);
            throw new IllegalStateException("Weird thing happened");
        }
    }

    private static void setBlockAt(@NotNull PaletteStorage paletteStorage, int x, int y, int z, short blockId) {
        if (!MathUtils.isBetween(y, 0, Chunk.CHUNK_SIZE_Y - 1)) {
            return;
        }

        final int section = ChunkUtils.getSectionAt(y);

        if (paletteStorage.sectionBlocks[section].length == 0) {
            if (blockId == 0) {
                // Section is empty and method is trying to place an air block, stop unnecessary computation
                return;
            }

            // Initialize the section
            paletteStorage.sectionBlocks[section] = new short[BLOCK_COUNT];
        }

        // Convert world coordinates to chunk coordinates
        x = toChunkCoordinate(x);
        z = toChunkCoordinate(z);

        final int sectionIndex = getSectionIndex(x, y, z);

        paletteStorage.sectionBlocks[section][sectionIndex] = blockId;
    }

    private static short getBlockAt(@NotNull PaletteStorage paletteStorage, int x, int y, int z) {
        if (y < 0 || y >= Chunk.CHUNK_SIZE_Y) {
            return 0;
        }

        final int section = ChunkUtils.getSectionAt(y);
        final short[] blocks;

        // Retrieve the longs and check if the section is empty
        {
            blocks = paletteStorage.sectionBlocks[section];

            if (blocks.length == 0) {
                // Section is not loaded, can only be air
                return 0;
            }
        }

        x = toChunkCoordinate(x);
        z = toChunkCoordinate(z);

        final int sectionIndex = getSectionIndex(x, y, z);

        // Change to palette value and return
        return paletteStorage.sectionBlocks[section][sectionIndex];
    }

    /**
     * Converts a world coordinate to a chunk one.
     *
     * @param xz the world coordinate
     * @return the chunk coordinate of {@code xz}
     */
    private static int toChunkCoordinate(int xz) {
        xz %= 16;
        if (xz < 0) {
            xz += CHUNK_SECTION_SIZE;
        }

        return xz;
    }

    /**
     * Gets the index of the block on the section array based on the block position.
     *
     * @param x the chunk X
     * @param y the chunk Y
     * @param z the chunk Z
     * @return the section index of the position
     */
    public static int getSectionIndex(int x, int y, int z) {
        y %= CHUNK_SECTION_SIZE;
        return y << 8 | z << 4 | x;
    }
}
