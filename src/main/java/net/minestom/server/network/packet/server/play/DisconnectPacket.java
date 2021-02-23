package net.minestom.server.network.packet.server.play;

import net.kyori.adventure.text.Component;
import net.minestom.server.chat.Adventure;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import net.minestom.server.utils.binary.BinaryWriter;
import org.jetbrains.annotations.NotNull;

public class DisconnectPacket implements ServerPacket {

    public String message; // JSON text

    public DisconnectPacket(@NotNull String message) {
        this.message = message;
    }

    public DisconnectPacket(@NotNull Component message){
        this(Adventure.COMPONENT_SERIALIZER.serialize(message));
    }

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeSizedString(message);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.DISCONNECT;
    }
}
