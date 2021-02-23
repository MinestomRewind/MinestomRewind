package net.minestom.server.network.packet.server.play;

import net.kyori.adventure.text.Component;
import net.minestom.server.chat.Adventure;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import net.minestom.server.utils.binary.BinaryWriter;
import org.jetbrains.annotations.NotNull;

public class PlayerListHeaderAndFooterPacket implements ServerPacket {

    private static final String EMPTY_COMPONENT = "{\"translate\":\"\"}";

    public Component header; // Only text
    public Component footer; // Only text

    @Override
    public void write(@NotNull BinaryWriter writer) {
        if (header == null) {
            writer.writeSizedString(EMPTY_COMPONENT);
        } else {
            writer.writeSizedString(Adventure.COMPONENT_SERIALIZER.serialize(header));
        }

        if (footer == null) {
            writer.writeSizedString(EMPTY_COMPONENT);
        } else {
            writer.writeSizedString(Adventure.COMPONENT_SERIALIZER.serialize(footer));
        }
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.PLAYER_LIST_HEADER_AND_FOOTER;
    }
}
