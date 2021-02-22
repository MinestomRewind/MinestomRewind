package net.minestom.server.network.packet.server.play;

import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import net.minestom.server.utils.binary.BinaryWriter;
import org.jetbrains.annotations.NotNull;

public class KeepAlivePacket implements ServerPacket {

    public int id;

    public KeepAlivePacket(int id) {
        this.id = id;
    }

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(id);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.KEEP_ALIVE;
    }
}
