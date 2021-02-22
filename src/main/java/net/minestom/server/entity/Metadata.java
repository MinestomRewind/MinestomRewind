package net.minestom.server.entity;

import net.minestom.server.item.ItemStack;
import net.minestom.server.network.packet.server.play.EntityMetaDataPacket;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Vector;
import net.minestom.server.utils.binary.BinaryWriter;
import net.minestom.server.utils.binary.Writeable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class Metadata {

    // METADATA TYPES

    public static Value<Byte> Byte(byte value) {
        return new Value<>(TYPE_BYTE, value, writer -> writer.writeByte(value));
    }

    public static Value<Short> Short(short value) {
        return new Value<>(TYPE_SHORT, value, writer -> writer.writeShort(value));
    }

    public static Value<Integer> Int(int value) {
        return new Value<>(TYPE_INT, value, writer -> writer.writeInt(value));
    }

    public static Value<Float> Float(float value) {
        return new Value<>(TYPE_FLOAT, value, writer -> writer.writeFloat(value));
    }

    public static Value<String> String(@NotNull String value) {
        return new Value<>(TYPE_STRING, value, writer -> writer.writeSizedString(value));
    }

    public static Value<ItemStack> Slot(@NotNull ItemStack value) {
        return new Value<>(TYPE_SLOT, value, writer -> writer.writeItemStack(value));
    }

    public static Value<BlockPosition> Position(@NotNull BlockPosition value) {
        return new Value<>(TYPE_POSITION, value, writer -> {
            writer.writeInt(value.getX());
            writer.writeInt(value.getY());
            writer.writeInt(value.getZ());
        });
    }

    public static Value<Vector> Vector(@NotNull Vector value) {
        return new Value<>(TYPE_VECTOR, value, writer -> {
            writer.writeFloat((float) value.getX());
            writer.writeFloat((float) value.getY());
            writer.writeFloat((float) value.getZ());
        });
    }

    public static final byte TYPE_BYTE = 0;
    public static final byte TYPE_SHORT = 1;
    public static final byte TYPE_INT = 2;
    public static final byte TYPE_FLOAT = 3;
    public static final byte TYPE_STRING = 4;
    public static final byte TYPE_SLOT = 5;
    public static final byte TYPE_POSITION = 6;
    public static final byte TYPE_VECTOR = 7;

    private final Entity entity;

    private Map<Byte, Entry<?>> metadataMap = new ConcurrentHashMap<>();

    public Metadata(@Nullable Entity entity) {
        this.entity = entity;
    }

    public <T> T getIndex(byte index, @Nullable T defaultValue) {
        Entry<?> value = metadataMap.get(index);
        return value != null ? (T) value.getMetaValue().value : defaultValue;
    }

    public void setIndex(byte index, @NotNull Value<?> value) {
        final Entry<?> entry = new Entry<>(index, value);
        this.metadataMap.put(index, entry);

        // Send metadata packet to update viewers and self
        if (entity != null && entity.isActive()) {
            EntityMetaDataPacket metaDataPacket = new EntityMetaDataPacket();
            metaDataPacket.entityId = entity.getEntityId();
            metaDataPacket.entries = Collections.singleton(entry);

            this.entity.sendPacketToViewersAndSelf(metaDataPacket);
        }
    }

    @NotNull
    public Collection<Entry<?>> getEntries() {
        return metadataMap.values();
    }

    public static class Entry<T> implements Writeable {

        protected final byte index;
        protected final Value<T> value;

        private Entry(byte index, @NotNull Value<T> value) {
            this.index = index;
            this.value = value;
        }

        @Override
        public void write(@NotNull BinaryWriter writer) {
            writer.writeByte((byte) (((this.value.type << 5) | (index & 0x1F)) & 0xFF));
            this.value.valueWriter.accept(writer);
        }

        public byte getIndex() {
            return index;
        }

        @NotNull
        public Value<T> getMetaValue() {
            return value;
        }
    }

    public static class Value<T> {

        protected final int type;
        protected final T value;
        protected final Consumer<BinaryWriter> valueWriter;

        private Value(int type, T value, @NotNull Consumer<BinaryWriter> valueWriter) {
            this.type = type;
            this.value = value;
            this.valueWriter = valueWriter;
        }

        public int getType() {
            return type;
        }

        public T getValue() {
            return value;
        }
    }

}
