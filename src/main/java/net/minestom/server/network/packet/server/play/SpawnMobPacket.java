package net.minestom.server.network.packet.server.play;

import net.minestom.server.entity.Metadata;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.binary.BinaryWriter;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.UUID;

public class SpawnMobPacket implements ServerPacket {

    public int entityId;
    public byte entityType;
    public Position position;
    public float headPitch;
    public short velocityX, velocityY, velocityZ;
    public Collection<Metadata.Entry<?>> metadataEntries;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(entityId);
        writer.writeByte(entityType);

        writer.writeInt((int) (position.getX() * 32.0));
        writer.writeInt((int) (position.getY() * 32.0));
        writer.writeInt((int) (position.getZ() * 32.0));

        writer.writeByte((byte) (position.getYaw() * 256 / 360));
        writer.writeByte((byte) (position.getPitch() * 256 / 360));
        writer.writeByte((byte) (headPitch * 256 / 360));

        writer.writeShort(velocityX);
        writer.writeShort(velocityY);
        writer.writeShort(velocityZ);

        // Write all the fields
        for (Metadata.Entry<?> entry : metadataEntries) {
            entry.write(writer);
        }

        writer.writeByte((byte) 0x7F); // End
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.SPAWN_MOB;
    }
}
