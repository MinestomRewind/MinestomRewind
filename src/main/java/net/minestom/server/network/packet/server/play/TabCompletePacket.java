package net.minestom.server.network.packet.server.play;

import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import net.minestom.server.utils.binary.BinaryWriter;
import org.jetbrains.annotations.NotNull;

public class TabCompletePacket implements ServerPacket {

    public int length;
    public String[] matches;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(length);

        writer.writeVarInt(matches.length);
        for (String match : matches) {
            writer.writeSizedString(match);
        }
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.TAB_COMPLETE;
    }
}
