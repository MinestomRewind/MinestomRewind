package net.minestom.server.network.packet.server.play;

import net.minestom.server.entity.GameMode;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import net.minestom.server.utils.binary.BinaryWriter;
import net.minestom.server.world.Difficulty;
import net.minestom.server.world.DimensionType;
import net.minestom.server.world.LevelType;
import org.jetbrains.annotations.NotNull;

public class RespawnPacket implements ServerPacket {

    public DimensionType dimensionType;
    public Difficulty difficulty;
    public GameMode gameMode;
    public LevelType levelType;

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeInt(dimensionType.getId());
        writer.writeByte(difficulty.getId());
        writer.writeByte(gameMode.getId());
        writer.writeSizedString(levelType.getId());
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.RESPAWN;
    }
}
