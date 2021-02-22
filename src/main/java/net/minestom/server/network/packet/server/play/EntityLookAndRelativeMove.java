package net.minestom.server.network.packet.server.play;

import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import net.minestom.server.utils.Position;
import net.minestom.server.utils.binary.BinaryWriter;
import org.jetbrains.annotations.NotNull;

public class EntityLookAndRelativeMove implements ServerPacket {

    public int entityId;
    public byte deltaX, deltaY, deltaZ;
    public float yaw, pitch;
    public boolean onGround;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(entityId);
        writer.writeByte(deltaX);
        writer.writeByte(deltaY);
        writer.writeByte(deltaZ);
        writer.writeByte((byte) (yaw * 256 / 360));
        writer.writeByte((byte) (pitch * 256 / 360));
        writer.writeBoolean(onGround);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.ENTITY_LOOK_AND_RELATIVE_MOVE;
    }

    public static EntityLookAndRelativeMove getPacket(int entityId,
                                                      @NotNull Position newPosition, @NotNull Position oldPosition,
                                                      boolean onGround) {
        EntityLookAndRelativeMove entityLookAndRelativeMove = new EntityLookAndRelativeMove();
        entityLookAndRelativeMove.entityId = entityId;
        entityLookAndRelativeMove.deltaX = EntityRelativeMovePacket.getRelativeMove(newPosition.getX(), oldPosition.getX());
        entityLookAndRelativeMove.deltaY = EntityRelativeMovePacket.getRelativeMove(newPosition.getY(), oldPosition.getY());
        entityLookAndRelativeMove.deltaZ = EntityRelativeMovePacket.getRelativeMove(newPosition.getZ(), oldPosition.getZ());
        entityLookAndRelativeMove.yaw = newPosition.getYaw();
        entityLookAndRelativeMove.pitch = newPosition.getPitch();
        entityLookAndRelativeMove.onGround = onGround;

        return entityLookAndRelativeMove;
    }
}
