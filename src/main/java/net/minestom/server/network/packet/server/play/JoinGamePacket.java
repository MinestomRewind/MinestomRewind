package net.minestom.server.network.packet.server.play;

import net.minestom.server.entity.GameMode;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import net.minestom.server.utils.binary.BinaryWriter;
import net.minestom.server.world.Difficulty;
import net.minestom.server.world.DimensionType;
import net.minestom.server.world.LevelType;
import org.jetbrains.annotations.NotNull;

public class JoinGamePacket implements ServerPacket {

    public int entityId;
    public GameMode gameMode;
    public DimensionType dimensionType;
    public Difficulty difficulty;
    public int maxPlayers = 0;
    public LevelType levelType = LevelType.DEFAULT;
    public boolean reducedDebugInfo = false;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeInt(entityId);
        writer.writeByte((byte) (gameMode.getId() | (gameMode.isHardcore() ? 0x08 : 0x0)));
        writer.writeByte(dimensionType.getId());
        writer.writeByte(difficulty.getId());
        writer.writeByte((byte) maxPlayers);
        writer.writeSizedString(levelType.getId());
        writer.writeBoolean(reducedDebugInfo);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.JOIN_GAME;
    }

}
