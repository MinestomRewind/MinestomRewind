package net.minestom.server.network.packet.server.play;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.minestom.server.chat.Adventure;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import net.minestom.server.utils.binary.BinaryWriter;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ChatMessagePacket implements ServerPacket {

    public Component component;
    public Position position;

    public ChatMessagePacket(Component component, Position position) {
        this.component = component;
        this.position = position;
    }

    @Override
    public void write(@NotNull BinaryWriter writer) {
        String jsonMessage;
        if (position == Position.GAME_INFO) {
            // The action bar doesn't support colors in components, but does require it
            jsonMessage = Adventure.COMPONENT_SERIALIZER.serialize(Component.text(LegacyComponentSerializer.legacySection().serialize(component)));
        } else {
            jsonMessage = Adventure.COMPONENT_SERIALIZER.serialize(component);
        }

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
