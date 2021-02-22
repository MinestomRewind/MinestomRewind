package net.minestom.codegen.items;

import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.NamespaceID;

import java.util.Objects;

public class ItemContainer implements Comparable<ItemContainer> {
    private int id;
    private NamespaceID name;
    private int stackSize;
    private String blockName;

    public ItemContainer(int id, NamespaceID name, int stackSize, String blockName) {
        this.id = id;
        this.name = name;
        this.stackSize = stackSize;
        this.blockName = blockName;
    }

    public int getId() {
        return id;
    }

    public NamespaceID getName() {
        return name;
    }

    public String getBlockName() {
        return blockName;
    }

    public int getStackSize() {
        return stackSize;
    }

    @Override
    public int compareTo(ItemContainer o) {
        return Integer.compare(id, o.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemContainer that = (ItemContainer) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
