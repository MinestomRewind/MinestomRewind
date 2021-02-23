package net.minestom.server.network.packet.server.login;

import net.kyori.adventure.text.Component;
import net.minestom.server.chat.Adventure;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import net.minestom.server.utils.binary.BinaryWriter;
import org.jetbrains.annotations.NotNull;

public class LoginDisconnectPacket implements ServerPacket {

    private final String kickMessage; // JSON text

    public LoginDisconnectPacket(@NotNull String kickMessage) {
        this.kickMessage = kickMessage;
    }

    public LoginDisconnectPacket(@NotNull Component jsonKickMessage) {
        this(Adventure.COMPONENT_SERIALIZER.serialize(jsonKickMessage));
    }

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeSizedString(kickMessage);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.LOGIN_DISCONNECT;
    }

}
