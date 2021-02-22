package net.minestom.server.network.packet.server.play;

import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import net.minestom.server.utils.binary.BinaryWriter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class OpenWindowPacket implements ServerPacket {

    public byte windowId;
    public String windowType;
    public String title;
    public byte numberOfSlots;
    public int entityId;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeByte(windowId);
        writer.writeSizedString(windowType);
        writer.writeSizedString("{\"text\": \"" + title + " \"}");
        writer.writeByte(numberOfSlots);

        if (Objects.equals(windowType, "EntityHorse")) {
            writer.writeVarInt(entityId);
        }
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.OPEN_WINDOW;
    }
}
