package net.minestom.server.network.packet.server.play;

import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.binary.BinaryWriter;
import org.jetbrains.annotations.NotNull;

public class UseBedPacket implements ServerPacket {

    public int entityId;
    public BlockPosition location;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(entityId);
        writer.writeBlockPosition(location);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.USE_BED;
    }
}
