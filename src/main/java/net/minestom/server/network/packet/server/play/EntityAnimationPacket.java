package net.minestom.server.network.packet.server.play;

import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import net.minestom.server.utils.binary.BinaryWriter;
import org.jetbrains.annotations.NotNull;

public class EntityAnimationPacket implements ServerPacket {

    public int entityId;
    public Animation animation;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(entityId);
        writer.writeByte((byte) animation.ordinal());
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.ENTITY_ANIMATION;
    }

    public enum Animation {
        SWING_ARM,
        TAKE_DAMAGE,
        LEAVE_BED,
        EAT_FOOD,
        CRITICAL_EFFECT,
        MAGICAL_CRITICAL_EFFECT
    }
}
