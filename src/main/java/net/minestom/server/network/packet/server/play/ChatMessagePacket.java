package net.minestom.server.network.packet.server.play;

import net.kyori.adventure.text.Component;
import net.minestom.server.chat.Adventure;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import net.minestom.server.utils.binary.BinaryWriter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ChatMessagePacket implements ServerPacket {

    public String jsonMessage;
    public Position position;

    public ChatMessagePacket(String jsonMessage, Position position) {
        this.jsonMessage = jsonMessage;
        this.position = position;
    }

    public ChatMessagePacket(Component component, Position position) {
        this(Adventure.COMPONENT_SERIALIZER.serialize(component), position);
    }

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeSizedString(jsonMessage);
        writer.writeByte((byte) position.ordinal());
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.CHAT_MESSAGE;
    }

    public enum Position {
        CHAT,
        SYSTEM_MESSAGE,
        GAME_INFO
    }
}
