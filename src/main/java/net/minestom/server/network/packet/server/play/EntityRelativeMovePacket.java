package net.minestom.server.network.packet.server.play;

import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.binary.BinaryWriter;
import org.jetbrains.annotations.NotNull;

public class EntityRelativeMovePacket implements ServerPacket {

    public int entityId;
    public byte deltaX, deltaY, deltaZ;
    public boolean onGround;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(entityId);
        writer.writeByte(deltaX);
        writer.writeByte(deltaY);
        writer.writeByte(deltaZ);
        writer.writeBoolean(onGround);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.ENTITY_RELATIVE_MOVE;
    }

    @NotNull
    public static EntityRelativeMovePacket getPacket(int entityId,
                                                     @NotNull Position newPosition, @NotNull Position oldPosition,
                                                     boolean onGround) {
        EntityRelativeMovePacket entityRelativeMovePacket = new EntityRelativeMovePacket();
        entityRelativeMovePacket.entityId = entityId;
        entityRelativeMovePacket.deltaX = getRelativeMove(newPosition.getX(), oldPosition.getX());
        entityRelativeMovePacket.deltaY = getRelativeMove(newPosition.getY(), oldPosition.getY());
        entityRelativeMovePacket.deltaZ = getRelativeMove(newPosition.getZ(), oldPosition.getZ());
        entityRelativeMovePacket.onGround = onGround;

        return entityRelativeMovePacket;
    }

    public static byte getRelativeMove(double newCoord, double oldCoord) {
        int newFixedPoint = (int) (newCoord * 32.0);
        int oldFixedPoint = (int) (oldCoord * 32.0);

        return (byte) (newFixedPoint - oldFixedPoint);
    }
}
