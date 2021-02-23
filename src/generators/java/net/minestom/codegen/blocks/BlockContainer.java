package net.minestom.codegen.blocks;

import net.minestom.server.utils.NamespaceID;

import java.util.List;

public class BlockContainer implements Comparable<BlockContainer> {

    private int ordinal;
    private NamespaceID id;
    private double hardness;
    private double resistance;
    private boolean isSolid;
    private boolean isAir;
    private List<BlockVariation> variations;

    private boolean isMushroom;
    private boolean isLiquid;
    private NamespaceID blockEntity;

    public BlockContainer(int ordinal, NamespaceID id, double hardness, double resistance, NamespaceID blockEntity, List<BlockVariation> variations) {
        this.ordinal = ordinal;
        this.id = id;
        this.hardness = hardness;
        this.resistance = resistance;
        this.blockEntity = blockEntity;
        this.variations = variations;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public List<BlockVariation> getVariations() {
        return variations;
    }

    public NamespaceID getId() {
        return id;
    }

    public boolean isAir() {
        return isAir;
    }

    public boolean isLiquid() {
        return isLiquid;
    }

    public boolean isMushroom() {
        return isMushroom;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public double getHardness() {
        return hardness;
    }

    public double getResistance() {
        return resistance;
    }

    public NamespaceID getBlockEntityName() {
        return blockEntity;
    }

    public BlockContainer setLiquid() {
        isLiquid = true;
        return this;
    }

    public BlockContainer setMushroom() {
        isMushroom = true;
        return this;
    }

    public BlockContainer setSolid() {
        isSolid = true;
        return this;
    }

    public BlockContainer setAir() {
        isAir = true;
        return this;
    }

    @Override
    public String toString() {
        return "blocks.BlockContainer{" +
                "ordinal=" + ordinal +
                ", id=" + id +
                ", hardness=" + hardness +
                ", resistance=" + resistance +
                ", isSolid=" + isSolid +
                ", isAir=" + isAir +
                ", variations=" + variations +
                ", isMushroom=" + isMushroom +
                ", isLiquid=" + isLiquid +
                ", blockEntity=" + blockEntity +
                '}';
    }

    @Override
    public int compareTo(BlockContainer o) {
        return Integer.compare(ordinal, o.ordinal);
    }

    public static class BlockVariation {
        private short metadata;
        private String displayName;

        public BlockVariation(short metadata, String displayName) {
            this.metadata = metadata;
            this.displayName = displayName;
        }

        public short getMetadata() {
            return metadata;
        }

        public String getDisplayName() {
            return displayName;
        }

        @Override
        public String toString() {
            return "BlockVariation{" +
                    "metadata=" + metadata +
                    ", displayName='" + displayName + '\'' +
                    '}';
        }
    }

}
