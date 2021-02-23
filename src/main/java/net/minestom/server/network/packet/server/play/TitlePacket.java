package net.minestom.server.network.packet.server.play;

import net.kyori.adventure.text.Component;
import net.minestom.server.chat.Adventure;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import net.minestom.server.utils.binary.BinaryWriter;
import org.jetbrains.annotations.NotNull;

public class TitlePacket implements ServerPacket {

    public Action action;

    public Component titleText; // Only text

    public Component subtitleText; // Only text

    public int fadeIn;
    public int stay;
    public int fadeOut;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(action.ordinal());

        switch (action) {
            case SET_TITLE:
                writer.writeSizedString(Adventure.COMPONENT_SERIALIZER.serialize(titleText));
                break;
            case SET_SUBTITLE:
                writer.writeSizedString(Adventure.COMPONENT_SERIALIZER.serialize(subtitleText));
                break;
            case SET_TIMES_AND_DISPLAY:
                writer.writeInt(fadeIn);
                writer.writeInt(stay);
                writer.writeInt(fadeOut);
                break;
            case HIDE:
            case RESET:
                break;
        }
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.TITLE;
    }

    public enum Action {
        SET_TITLE,
        SET_SUBTITLE,
        SET_TIMES_AND_DISPLAY,
        HIDE,
        RESET
    }

}
