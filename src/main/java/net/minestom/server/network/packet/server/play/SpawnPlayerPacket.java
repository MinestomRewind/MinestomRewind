package net.minestom.server.network.packet.server.play;

import net.minestom.server.entity.Metadata;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.binary.BinaryWriter;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.UUID;

public class SpawnPlayerPacket implements ServerPacket {

    public int entityId;
    public UUID playerUuid;
    public Position position;
    public short heldItem;
    public Collection<Metadata.Entry<?>> metadataEntries;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(entityId);
        writer.writeUuid(playerUuid);
        // Fixed point numbers
        writer.writeInt((int)(position.getX() * 32));
        writer.writeInt((int)(position.getY() * 32));
        writer.writeInt((int)(position.getZ() * 32));
        writer.writeByte((byte) (position.getYaw() * 256f / 360f));
        writer.writeByte((byte) (position.getPitch() * 256f / 360f));
        writer.writeShort(heldItem);

        // Write all the fields
        for (Metadata.Entry<?> entry : metadataEntries) {
            entry.write(writer);
        }

        writer.writeByte((byte) 0x7F); // End
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.SPAWN_PLAYER;
    }
}
